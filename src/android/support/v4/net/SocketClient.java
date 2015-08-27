package android.support.v4.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import net.minidev.json.JSONValue;
import android.support.v4.lang.Strings;
import android.support.v4.util.Dates;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class SocketClient {
	private transient String serverIp;

	public void setServerIp(final String serverIp) {
		this.serverIp = serverIp;
	}

	private transient JSONObject greetings;

	public void setGreetings(final JSONObject greetings) {
		this.greetings = greetings;
	}

	public interface ClientCallback {
		void onConnected(Sockets socket);

		void onDisconnected();

		void onRead(String message);
	}

	private transient ClientCallback callback;

	public void setCallback(final ClientCallback callback) {
		this.callback = callback;
	}

	private transient ExecutorService threadPool;

	public void setThreadPool(final ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	private transient boolean isConnected;

	private void setIsConnected(final boolean isConnected) {
		this.isConnected = isConnected;
	}

	private transient Sockets socket;

	public void connectToServer() {
		if (null != connectToServerFuture) {
			connectToServerFuture.cancel(true);
			connectToServerFuture = null;
		}

		connectToServerFuture = threadPool
				.submit(new ConnectToServerRunnable());
	}

	private transient Future<?> connectToServerFuture;

	private class ConnectToServerRunnable implements Runnable {
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
				socket = new Sockets(InetAddress.getByName(serverIp),
						Sockets.SERVERPORT);
				socket.setReceiveBufferSize(Sockets.BUFFERSIZE * 1024);
				socket.setSendBufferSize(Sockets.BUFFERSIZE / 2 * 1024);
				// socket[i].setSoLinger(true, 0);
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
			} catch (final RejectedExecutionException exception) {
				onDisconnect();
			} catch (final Exception exception) {
				Strings.exceptionToJSONObject(exception);
				onDisconnect();
			}
		}
	}

	public void sendMessage(final String message) throws IOException {
		if (!message.equals(Sockets.KEEPALIVE)) {
			System.out.println("[" + Dates.now() + "]" + "_SocketClientSend-->"
					+ message + "_"
					// + socket.getRemoteSocketAddress() + "_"
					+ socket.getProperties());
		}
		socket.send(message);
		setIsConnected(false);
	}

	public static final int PRIORITY_LOW = 0;
	public static final int PRIORITY_HIG = 1;

	private transient String lastMsgCode = Strings.EMPTY;
	private transient final ArrayList<JSONObject> queryListLOW = new ArrayList<JSONObject>();
	private transient final ArrayList<JSONObject> queryListHIG = new ArrayList<JSONObject>();

	public void addMessage(final JSONObject jObject, final int priority) {
		if (!Strings.isNull(jObject.get(Sockets.ACTION))) {
			for (int i = 0; i < queryListHIG.size(); i++) {
				if (jObject.get(Sockets.ACTION).equals(
						queryListHIG.get(i).get(Sockets.ACTION))) {
					queryListHIG.remove(i);
				}
			}
			for (int i = 0; i < queryListLOW.size(); i++) {
				if (jObject.get(Sockets.ACTION).equals(
						queryListLOW.get(i).get(Sockets.ACTION))) {
					queryListLOW.remove(i);
				}
			}

			jObject.put(Sockets.MSG_CODE, Dates.format("HHmmssSSS", new Date()));
			if (PRIORITY_HIG == priority) {
				queryListHIG.add(jObject);
			} else {
				queryListLOW.add(jObject);
			}
		}
	}

	private void removeMessage(final String message) {
		try {
			final JSONObject jObject = (JSONObject) JSONValue.parse(message);
			if (!Strings.isNull(jObject.get(Sockets.MSG_CODE))) {
				for (int i = 0; i < queryListHIG.size(); i++) {
					if (jObject.get(Sockets.MSG_CODE).equals(
							queryListHIG.get(i).get(Sockets.MSG_CODE))) {
						queryListHIG.remove(i);
					}
				}
				for (int i = 0; i < queryListLOW.size(); i++) {
					if (jObject.get(Sockets.MSG_CODE).equals(
							queryListLOW.get(i).get(Sockets.MSG_CODE))) {
						queryListLOW.remove(i);
					}
				}
			}
		} catch (final ClassCastException exception) {
		} catch (final Exception exception) {
			Strings.exceptionToJSONObject(exception);
		}
	}

	private transient Future<?> proposeFuture;

	private class Propose implements Runnable {
		private transient int queryTryCount;

		@Override
		public void run() {
			try {
				while (socket.isConnected()) {
					if (isConnected) {
						if (!queryListHIG.isEmpty()) {
							if (lastMsgCode.equals(queryListHIG.get(0).get(
									Sockets.MSG_CODE))) {
								queryTryCount++;
							} else {
								lastMsgCode = Strings.valueOf(queryListHIG.get(
										0).get(Sockets.MSG_CODE));
								queryTryCount = 0;
							}

							if (queryTryCount >= Sockets.QUEUEFAILTRY) {
								queryListHIG.remove(0);
								queryTryCount = 0;
							} else {
								sendMessage(queryListHIG.get(0).toJSONString(
										JSONStyle.MAX_COMPRESS));
							}
						} else if (!queryListLOW.isEmpty()) {
							if (lastMsgCode.equals(Strings.valueOf(queryListLOW
									.get(0).get(Sockets.MSG_CODE)))) {
								queryTryCount++;
							} else {
								lastMsgCode = Strings.valueOf(queryListLOW.get(
										0).get(Sockets.MSG_CODE));
								queryTryCount = 0;
							}

							if (queryTryCount >= Sockets.QUEUEFAILTRY) {
								queryListLOW.remove(0);
								queryTryCount = 0;
							} else {
								sendMessage(queryListLOW.get(0).toJSONString(
										JSONStyle.MAX_COMPRESS));
							}
						}
					}
					Thread.sleep(Sockets.PROPOSESPEED);
				}
				Thread.currentThread().interrupt();
			} catch (final InterruptedException exception) {
			} catch (final IOException exception) {
				onDisconnect();
			} catch (final Exception exception) {
				Strings.exceptionToJSONObject(exception);
			}
		}
	}

	public void readMessage(final String message) {
		removeMessage(message);
		callback.onRead(message);
	}

	private transient Future<?> receiveFuture;

	private class Receive implements Runnable {
		private transient BufferedReader reader;
		private transient InputStream inputStream;

		@Override
		public void run() {
			try {
				inputStream = new BufferedInputStream(socket.getInputStream());
				reader = new BufferedReader(new InputStreamReader(inputStream,
						"UTF-8"));
				while (socket.isConnected()) {
					String message = reader.readLine();
					if (null != message && message.length() != 0) {
						if (message.equals(Sockets.KEEPALIVE_REACTION)) {
							keepAliveTimeoutCount = 0;
						} else {
							if (message.length() >= Sockets.ENCRYPTSIZELIMIT) {
								message = Strings.decrypt(message,
										socket.getEncryptionKey());
							}
							readMessage(message);
						}

						System.out.println("[" + Dates.now() + "]"
								+ "_SocketClientRead-->" + message + "_"
								// + socket.getRemoteSocketAddress() +
								// "_"
								+ socket.getProperties());

						setIsConnected(true);
					}
					Thread.sleep(5);
				}
			} catch (final InterruptedException exception) {
			} catch (final IOException exception) {
				onDisconnect();
			} catch (final Exception exception) {
				Strings.exceptionToJSONObject(exception);
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

	private class KeepAlive implements Runnable {
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
				try {
					sendMessage(Sockets.KEEPALIVE);
				} catch (final IOException exception) {
					onDisconnect();
				}
				try {
					Thread.sleep(Sockets.KEEPALIVESPEED);
				} catch (final InterruptedException exception) {
				}
			}
		}
	}

	public void onDisconnect() {
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
			callback = null;
		}
	}
}
