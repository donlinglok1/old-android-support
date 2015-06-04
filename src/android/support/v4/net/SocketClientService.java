package android.support.v4.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import net.minidev.json.JSONValue;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
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
public class SocketClientService extends Service {
	private transient Sockets socket[] = new Sockets[1];
	private transient ExecutorService threadPool;

	/**
	 * Must Set
	 */
	public void setThreadPool(final ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	private transient String serverIpAddress;

	/**
	 * Must Set
	 */
	public void setServerIp(final String serverIpAddress) {
		this.serverIpAddress = serverIpAddress;
	}

	private transient int serverPort = 40;

	/**
	 * Default Value = 40;
	 */
	public void setServerPort(final int serverPort) {
		this.serverPort = serverPort;
	}

	private transient String encryptionKey = Strings.fString(Strings.UPPK,
			Strings.LOWE, Strings.LOWN, Strings.LOWN, Strings.LOWN,
			Strings.LOWE, Strings.LOWT, Strings.LOWH);

	/**
	 * Must Set
	 */
	public void setEncryptionKey(final String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	private transient int keepAliveFailTry = 2;

	/**
	 * Default Value = 3;
	 */
	public void setKeepAliveFailTry(final int keepAliveFailTry) {
		this.keepAliveFailTry = keepAliveFailTry;
	}

	private transient int encryptSizeLimit = 10;

	/**
	 * Default Value = 10;
	 */
	public void setEncryptSizeLimit(final int encryptSizeLimit) {
		this.encryptSizeLimit = encryptSizeLimit;
	}

	private transient JSONObject greetings;

	/**
	 * This message send Immediately after connected.
	 */
	public void setGreetings(final JSONObject greetings) {
		this.greetings = greetings;
	}

	private transient boolean isConnect;

	public void setIsConnect(final boolean isConnect) {
		this.isConnect = isConnect;
	}

	public boolean isitConnect() {
		return isConnect;
	}

	private transient Future<?> connectFuture;

	private transient ConnectToServerCallback callback;

	public void setCallback(final ConnectToServerCallback callback) {
		this.callback = callback;
	}

	public interface ConnectToServerCallback {
		void onConnected(Sockets result);

		void onTimeout(Sockets result);
	}

	public void startConnection() {
		connectFuture = threadPool.submit(new ConnectToServer());
	}

	/*
	 * DONNOT OVERRIDE IT.
	 */
	private class ConnectToServer implements Runnable {
		@Override
		public void run() {
			while (!isitConnect()) {
				try {
					Thread.sleep(1000 / 2);
					for (int i = 0; i < socket.length; i++) {
						socket[i] = new Sockets(
								InetAddress.getByName(serverIpAddress),
								serverPort);
						socket[i].setReceiveBufferSize(16 * 1024);
						socket[i].setSendBufferSize(16 * 1024);
						socket[i].setSoLinger(true, 0);
						socket[i].setTcpNoDelay(true);
						socket[i].setKeepAlive(true);
						socket[i].setOOBInline(true);
						socket[i].setTrafficClass(0x04 | 0x10);
						socket[i].setPerformancePreferences(1, 3, 2);
						final JSONObject jsonObject = new JSONObject();
						jsonObject.put(Sockets.ID, Strings.valueOf(i));
						socket[i].setTag(jsonObject);

						if (null != proposeFuture) {
							proposeFuture.cancel(true);
						}
						proposeFuture = threadPool
								.submit(new Propose(socket[i]));

						if (null != receiveFuture) {
							receiveFuture.cancel(true);
						}
						receiveFuture = threadPool
								.submit(new Receive(socket[i]));

						if (null != keepAliveFuture) {
							keepAliveFuture.cancel(true);
						}
						keepAliveFuture = threadPool.submit(new KeepAlive(
								socket[i]));

						if (null != greetings) {
							sendMessage(
									socket[i],
									greetings
											.toJSONString(JSONStyle.MAX_COMPRESS));
						}
						setIsConnect(true);

						if (null != callback) {
							callback.onConnected(socket[i]);
						}
					}
					break;
				} catch (final Exception exception) {
					exception.printStackTrace();
				}
			}
		}
	}

	private transient int keepAliveTimeoutCount;

	protected transient Future<?> keepAliveFuture;

	private class KeepAlive implements Runnable {
		private transient boolean connected = true;// TODO
		private final transient Sockets socket;

		public KeepAlive(final Sockets socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			while (connected) {
				try {
					sendMessage(socket, Sockets.KEEPALIVE);
					setIsConnect(false);
					if (keepAliveTimeoutCount > keepAliveFailTry) {
						keepAliveTimeoutCount = 0;
						connected = false;
						if (null != callback) {
							callback.onTimeout(socket);
						}
						break;
					} else {
						Thread.sleep(1000 * 3);
						keepAliveTimeoutCount++;
					}
				} catch (final InterruptedException exception) {
					exception.printStackTrace();
					connected = false;
				}
			}
		}
	}

	protected transient Future<?> receiveFuture;

	private class Receive implements Runnable {
		private transient boolean connected = true;// TODO
		private final transient Sockets socket;

		private transient BufferedReader reader;
		private transient InputStream inputStream;

		public Receive(final Sockets socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				inputStream = new BufferedInputStream(socket.getInputStream());
				reader = new BufferedReader(new InputStreamReader(inputStream,
						"UTF-8"));
				while (connected) {
					Thread.sleep(100);
					try {
						String message = reader.readLine();
						if (message != null && message.length() != 0) {
							try {
								if (message.length() >= encryptSizeLimit) {
									message = Strings.decrypt(message,
											encryptionKey);
									// System.out.println("[" + Dates.now() +
									// "]"
									// + "_Read-->" + message + "_"
									// + message.length() + "_"
									// + socket.getRemoteSocketAddress()
									// + "_" + socket.getTag());
								}
							} catch (final Exception exception) {
								exception.printStackTrace();
							}

							if (message.equals(Sockets.KEEPALIVE_REACTION)) {
								keepAliveTimeoutCount = 0;
								setIsConnect(true);
							} else {
								readMessage(message);
							}
						}
					} catch (final SocketTimeoutException e) {
						connected = false;
						break;
					} catch (final SocketException e) {
						connected = false;
						break;
					} catch (final IOException e) {
						connected = false;
						break;
					} catch (final Exception exception) {
						exception.printStackTrace();
						connected = false;
						break;
					}
				}
			} catch (final InterruptedException e) {
			} catch (final SocketException e) {
			} catch (final Exception exception) {
				exception.printStackTrace();
			} finally {
				try {
					inputStream.close();
				} catch (final Exception exception) {
					exception.printStackTrace();
				}
				try {
					reader.close();
				} catch (final Exception exception) {
					exception.printStackTrace();
				}
				try {
					socket.close();
				} catch (final Exception exception) {
					exception.printStackTrace();
				}
			}
			Thread.currentThread().interrupt();
		}
	}

	private transient String lastMsgCode = Strings.EMPTY;
	private transient int queueFailTry = 5;

	/**
	 * Default Value = 5;
	 */
	public void setQueueFailTry(final int setQueueFailTry) {
		queueFailTry = setQueueFailTry;
	}

	protected transient Future<?> proposeFuture;

	private class Propose implements Runnable {
		private transient int queryTryCount;
		private final transient Sockets socket;

		public Propose(final Sockets socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			boolean connected = true;
			while (connected) {
				try {
					if (isitConnect()) {
						if (!queryListHIG.isEmpty()) {
							if (lastMsgCode.equals(queryListHIG.get(0).get(
									Sockets.MSG_CODE))) {
								queryTryCount++;
							} else {
								lastMsgCode = Strings.valueOf(queryListHIG.get(
										0).get(Sockets.MSG_CODE));
								queryTryCount = 0;
							}

							if (queryTryCount >= queueFailTry) {
								queryListHIG.remove(0);
								queryTryCount = 0;
							} else {
								sendMessage(socket, queryListHIG.get(0)
										.toJSONString(JSONStyle.MAX_COMPRESS));
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

							if (queryTryCount >= queueFailTry) {
								queryListLOW.remove(0);
								queryTryCount = 0;
							} else {
								sendMessage(socket, queryListLOW.get(0)
										.toJSONString(JSONStyle.MAX_COMPRESS));
							}
						}
					}
					Thread.sleep(100 * 1);
				} catch (final Exception exception) {
					exception.printStackTrace();
					connected = false;
				}
			}
		}
	}

	public final static int PRIORITY_LOW = 0;
	public final static int PRIORITY_HIG = 1;

	private final transient ArrayList<JSONObject> queryListLOW = new ArrayList<JSONObject>();
	private final transient ArrayList<JSONObject> queryListHIG = new ArrayList<JSONObject>();

	/**
	 * Add message to the pending send queue
	 */
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

	public void removeMessage(final String message) {
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
	}

	public void sendMessage(final Sockets socket, final String message) {
		try {
			if (null != socket && socket.isConnected()
					&& !socket.isOutputShutdown()) {
				final OutputStream out = new BufferedOutputStream(
						socket.getOutputStream());
				final PrintWriter writer = new PrintWriter(
						new OutputStreamWriter(out, "UTF-8"));

				if (message.length() >= encryptSizeLimit) {
					try {
						writer.println(Strings.encrypt(message, encryptionKey));
						// System.out.println("[" + Dates.now() + "]" +
						// "_Send-->"
						// + message + "_" + message.length() + "_"
						// + socket.getRemoteSocketAddress() + "_"
						// + socket.getTag());
					} catch (final Exception exception) {
						exception.printStackTrace();
					}
				} else {
					writer.println(message);
				}
				writer.flush();
			}
		} catch (final Exception exception) {
			exception.printStackTrace();
		}
		setIsConnect(false);
	}

	/**
	 * Must Set
	 */
	public void readMessage(final String message) {
		removeMessage(message);
	}

	@Override
	public void onDestroy() {
		if (null != connectFuture) {
			connectFuture.cancel(true);
		}
		if (null != receiveFuture) {
			receiveFuture.cancel(true);
		}
		if (null != proposeFuture) {
			proposeFuture.cancel(true);
		}
		if (null != keepAliveFuture) {
			keepAliveFuture.cancel(true);
		}
		for (final Sockets element : socket) {
			try {
				element.close();
			} catch (final Exception exception) {
				exception.printStackTrace();
			}
		}
		super.onDestroy();
	}

	/*
	 * Auto run after onCreate();
	 *
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(final Intent intent, final int flags,
			final int startId) {
		return super.onStartCommand(intent, Service.START_STICKY, startId);
	}

	private final transient IBinder myBinder = new LocalBinder();

	/**
	 * Must Set
	 */
	public class LocalBinder extends Binder {
		LocalBinder() {
			super();
		}

		public SocketClientService getService() {
			return SocketClientService.this;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(final Intent intent) {
		return myBinder;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see android.app.Service#onUnbind(android.content.Intent)
	 */
	@Override
	public boolean onUnbind(final Intent intent) {
		return false;
	}
}