package android.support.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import android.n.It;
import android.n.NDate;
import android.n.NString;
import android.support.lang.Crypt;
import android.support.lang.Strings;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import net.minidev.json.JSONValue;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class SocketClient {
	public transient boolean isLog;

	private transient String serverIp;

	public final void setServerIp(final String serverIp) {
		this.serverIp = serverIp;
	}

	private transient JSONObject greetings;

	public final void setGreetings(final JSONObject greetings) {
		this.greetings = greetings;
	}

	public static abstract interface SocketClientCallback {
		public abstract void onConnected(Sockets socket);

		public abstract void onDisconnected();

		public abstract void onRead(String message);
	}

	private transient SocketClientCallback callback;

	public final void setCallback(final SocketClientCallback callback) {
		this.callback = callback;
	}

	private transient boolean isConnected;

	private final void setIsConnected(final boolean isConnected) {
		this.isConnected = isConnected;
	}

	private transient Sockets socket;
	private transient ExecutorService threadPool;

	public final void connectToServer() {
		threadPool = Executors.newFixedThreadPool(4);
		if (null != connectToServerFuture) {
			connectToServerFuture.cancel(true);
			connectToServerFuture = null;
		}
		connectToServerFuture = threadPool.submit(new ConnectToServerRunnable());
	}

	private transient Future<?> connectToServerFuture;

	private final class ConnectToServerRunnable implements Runnable {
		@Override
		public void run() {
			try {
				if (null != socket) {
					try {
						socket.close();
					} catch (final IOException exception) {
					}
					socket = null;
				}
				socket = new Sockets(InetAddress.getByName(serverIp), Sockets.SERVERPORT);
				socket.setReceiveBufferSize(Sockets.BUFFERSIZE * 1024);
				socket.setSendBufferSize(Sockets.BUFFERSIZE / 2 * 1024);
				socket.setTcpNoDelay(true);
				socket.setKeepAlive(true);
				socket.setOOBInline(false);
				socket.setTrafficClass(0x04 | 0x10);
				socket.setPerformancePreferences(1, 3, 2);
				socket.setSoTimeout(30 * 1000);

				if (null != receiveFuture) {
					receiveFuture.cancel(true);
					receiveFuture = null;
				}
				receiveFuture = threadPool.submit(new Receive());

				if (null != proposeFuture) {
					proposeFuture.cancel(true);
					proposeFuture = null;
				}
				proposeFuture = threadPool.submit(new Propose());

				if (null != keepAliveFuture) {
					keepAliveFuture.cancel(true);
					keepAliveFuture = null;
				}
				keepAliveFuture = threadPool.submit(new KeepAlive());

				if (null != greetings) {
					sendMessage(greetings.toJSONString(JSONStyle.MAX_COMPRESS));
				}

				if (null != callback) {
					callback.onConnected(socket);
				}
			} catch (final ConnectException exception) {
				onDisconnect();
			} catch (final RejectedExecutionException exception) {
				onDisconnect();
			} catch (final Exception exception) {
				exception.printStackTrace();
				onDisconnect();
			}
		}
	}

	public final void sendMessage(final String message) {
		if (null != socket) {
			try {
				socket.send(message);
				if (isLog && !Sockets.KEEPALIVE.equals(message)) {
					System.out.println("[" + NDate.getDate() + "]" + "_SocketClientSend-->" + message + "_"
							+ socket.getProperties());
				}
			} catch (final IOException exception) {
				exception.printStackTrace();
				onDisconnect();
			}
		}
		setIsConnected(false);
	}

	public static final int PRIORITY_LOW = 0;
	public static final int PRIORITY_HIG = 1;

	private transient String lastMsgCode = Strings.EMPTY;
	private final transient ArrayList<JSONObject> queryListLOW = new ArrayList<JSONObject>();
	private final transient ArrayList<JSONObject> queryListHIG = new ArrayList<JSONObject>();

	public final void addMessage(final JSONObject jObject, final int priority) {
		if (!It.isNull(jObject.get(Sockets.ACTION))) {
			for (int i = 0; i < queryListHIG.size(); i++) {
				if (jObject.get(Sockets.ACTION).equals(queryListHIG.get(i).get(Sockets.ACTION))) {
					queryListHIG.remove(i);
				}
			}
			for (int i = 0; i < queryListLOW.size(); i++) {
				if (jObject.get(Sockets.ACTION).equals(queryListLOW.get(i).get(Sockets.ACTION))) {
					queryListLOW.remove(i);
				}
			}

			jObject.put(Sockets.MSG_CODE, new SimpleDateFormat("HHmmssSSS"));
			if (PRIORITY_HIG == priority) {
				queryListHIG.add(jObject);
			} else {
				queryListLOW.add(jObject);
			}
		}
	}

	private final void removeMessage(final String message) {
		try {
			final JSONObject jObject = (JSONObject) JSONValue.parse(message);
			if (!It.isNull(jObject.get(Sockets.MSG_CODE))) {
				for (int i = 0; i < queryListHIG.size(); i++) {
					if (jObject.get(Sockets.MSG_CODE).equals(queryListHIG.get(i).get(Sockets.MSG_CODE))) {
						queryListHIG.remove(i);
					}
				}
				for (int i = 0; i < queryListLOW.size(); i++) {
					if (jObject.get(Sockets.MSG_CODE).equals(queryListLOW.get(i).get(Sockets.MSG_CODE))) {
						queryListLOW.remove(i);
					}
				}
			}
		} catch (final ClassCastException exception) {
		} catch (final Exception exception) {
			exception.printStackTrace();
		}
	}

	private transient Future<?> proposeFuture;

	private final class Propose implements Runnable {
		private transient int queryTryCount;

		@Override
		public void run() {
			try {
				while (socket.isConnected()) {
					if (isConnected) {
						if (!queryListHIG.isEmpty()) {
							if (lastMsgCode.equals(queryListHIG.get(0).get(Sockets.MSG_CODE))) {
								queryTryCount++;
							} else {
								lastMsgCode = NString.parse(queryListHIG.get(0).get(Sockets.MSG_CODE));
								queryTryCount = 0;
							}

							if (queryTryCount >= Sockets.QUEUEFAILTRY) {
								queryListHIG.remove(0);
								queryTryCount = 0;
							} else {
								sendMessage(queryListHIG.get(0).toJSONString(JSONStyle.MAX_COMPRESS));
							}
						} else if (!queryListLOW.isEmpty()) {
							if (lastMsgCode.equals(NString.parse(queryListLOW.get(0).get(Sockets.MSG_CODE)))) {
								queryTryCount++;
							} else {
								lastMsgCode = NString.parse(queryListLOW.get(0).get(Sockets.MSG_CODE));
								queryTryCount = 0;
							}

							if (queryTryCount >= Sockets.QUEUEFAILTRY) {
								queryListLOW.remove(0);
								queryTryCount = 0;
							} else {
								sendMessage(queryListLOW.get(0).toJSONString(JSONStyle.MAX_COMPRESS));
							}
						}
					}
					Thread.sleep(Sockets.PROPOSESPEED);
				}
				Thread.currentThread().interrupt();
			} catch (final InterruptedException exception) {
			} catch (final Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	public final void readMessage(final String message) {
		if (isLog && !Sockets.KEEPALIVE_REACTION.equals(message)) {
			System.out.println(
					"[" + NDate.getDate() + "]" + "_SocketClientRead-->" + message + "_" + socket.getProperties());
		}
		removeMessage(message);
		if (null != callback) {
			callback.onRead(message);
		}
	}

	private transient Future<?> receiveFuture;

	private final class Receive implements Runnable {
		private transient BufferedReader reader;
		private transient InputStream inputStream;

		@Override
		public void run() {
			try {
				inputStream = new BufferedInputStream(socket.getInputStream());
				reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				while (socket.isConnected()) {
					String message = reader.readLine();
					if (null != message && message.length() != 0) {
						if (Sockets.KEEPALIVE_REACTION.equals(message)) {
							keepAliveTimeoutCount = 0;
						} else {
							if (message.length() >= Sockets.ENCRYPTSIZELIMIT) {
								message = Crypt.decrypt(message, socket.getEncryptionKey());
							}
							readMessage(message);
						}
						setIsConnected(true);
					}
					Thread.sleep(5);
				}
			} catch (final InterruptedException exception) {
			} catch (final IOException exception) {
				onDisconnect();
			} catch (final Exception exception) {
				exception.printStackTrace();
			} finally {
				try {
					inputStream.close();
				} catch (final IOException exception) {
				}
				try {
					reader.close();
				} catch (final IOException exception) {
				}
			}
		}
	}

	private transient int keepAliveTimeoutCount;

	private transient Future<?> keepAliveFuture;

	private final class KeepAlive implements Runnable {
		@Override
		public void run() {
			keepAliveTimeoutCount = 0;

			while (true) {
				if (keepAliveTimeoutCount > Sockets.KEEPALIVEFAILTRY) {
					keepAliveTimeoutCount = 0;
					onDisconnect();
					break;
				} else {
					keepAliveTimeoutCount++;
				}
				sendMessage(Sockets.KEEPALIVE);

				try {
					Thread.sleep(Sockets.KEEPALIVESPEED);
				} catch (final InterruptedException exception) {
				}
			}
		}
	}

	public final void onDisconnect() {
		if (null != connectToServerFuture) {
			connectToServerFuture.cancel(true);
			connectToServerFuture = null;
		}
		if (null != keepAliveFuture) {
			keepAliveFuture.cancel(true);
			keepAliveFuture = null;
		}
		if (null != proposeFuture) {
			proposeFuture.cancel(true);
			proposeFuture = null;
		}
		if (null != receiveFuture) {
			receiveFuture.cancel(true);
			receiveFuture = null;
		}
		if (null != threadPool) {
			threadPool.shutdownNow();
			threadPool = null;
		}
		if (null != socket) {
			try {
				socket.close();
			} catch (final IOException exception) {
			}
			socket = null;
		}
		if (null != callback) {
			callback.onDisconnected();
		}
	}
}
