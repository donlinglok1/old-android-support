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
	public interface ClientCallback {
		void onConnected(Sockets socket);

		void onDisconnected(Sockets socket);
	}

	private transient ClientCallback callback;

	public void setCallback(final ClientCallback callback) {
		this.callback = callback;
	}

	private transient int serverPort = 40;

	public void setServerPort(final int serverPort) {
		this.serverPort = serverPort;
	}

	private transient String serverIp;

	public void setServerIp(final String serverIp) {
		this.serverIp = serverIp;
	}

	public transient boolean isConnected;

	public void setIsConnected(final boolean isConnected) {
		this.isConnected = isConnected;
	}

	private transient ExecutorService threadPool;

	public void setThreadPool(final ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	private transient Sockets socket;

	public void connectToServer() {
		try {
			connectToServerFuture.cancel(true);
		} catch (final Exception exception) {
			// Strings.exceptionToJSONObject(exception);
		}
		connectToServerFuture = threadPool
				.submit(new ConnectToServerRunnable());
	}

	private transient JSONObject greetings;

	public void setGreetings(final JSONObject greetings) {
		this.greetings = greetings;
	}

	private transient Future<?> connectToServerFuture;

	private class ConnectToServerRunnable implements Runnable {
		@Override
		public void run() {
			try {
				socket = new Sockets(InetAddress.getByName(serverIp),
						serverPort);
				socket.setReceiveBufferSize(16 * 1024);
				socket.setSendBufferSize(16 * 1024);
				// socket[i].setSoLinger(true, 0);
				socket.setTcpNoDelay(true);
				socket.setKeepAlive(true);
				socket.setOOBInline(false);
				socket.setTrafficClass(0x04 | 0x10);
				socket.setPerformancePreferences(1, 3, 2);
				socket.setSoTimeout(30 * 1000);

				try {
					receiveFuture.cancel(true);
				} catch (final Exception exception) {
					// Strings.exceptionToJSONObject(exception);
				}
				receiveFuture = threadPool.submit(new Receive(socket));

				try {
					proposeFuture.cancel(true);
				} catch (final Exception exception) {
					// Strings.exceptionToJSONObject(exception);
				}
				proposeFuture = threadPool.submit(new Propose(socket));

				try {
					keepAliveFuture.cancel(true);
				} catch (final Exception exception) {
					// Strings.exceptionToJSONObject(exception);
				}
				keepAliveFuture = threadPool.submit(new KeepAlive(socket));

				if (null != greetings) {
					sendMessage(socket,
							greetings.toJSONString(JSONStyle.MAX_COMPRESS));
				}

				if (null != callback) {
					callback.onConnected(socket);
				}
			} catch (final RejectedExecutionException exception) {
				Strings.exceptionToJSONObject(exception);
				onDisconnect();
			} catch (final Exception exception) {
				Strings.exceptionToJSONObject(exception);
				run();
			}
		}
	}

	public void sendMessage(final Sockets socket, String message)
			throws IOException {
		if (null != socket && !socket.isOutputShutdown() && !socket.isClosed()) {
			final OutputStream out = new BufferedOutputStream(
					socket.getOutputStream());
			final PrintWriter writer = new PrintWriter(new OutputStreamWriter(
					out, "UTF-8"));

			if (message.length() >= socket.getEncryptionSizeLimit()) {
				// System.out.println("[" + Dates.now() + "]"
				// + "_SocketClientSend-->" + message + "_"
				// // + socket.getRemoteSocketAddress() + "_"
				// + socket.getProperties());

				try {
					message = Strings.encrypt(message,
							socket.getEncryptionKey());
				} catch (final Exception exception) {
					Strings.exceptionToJSONObject(exception);
				}
			}

			writer.println(message);
			writer.flush();
			setIsConnected(false);
		}
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

	public void removeMessage(final String message) {
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
		} catch (final Exception exception) {
			// Strings.exceptionToJSONObject(exception);
		}
	}

	private transient int queueFailTry = 5;

	public void setQueueFailTry(final int queueFailTry) {
		this.queueFailTry = queueFailTry;
	}

	protected transient Future<?> proposeFuture;

	private class Propose implements Runnable {
		private transient int queryTryCount;

		private transient final Sockets socket;

		public Propose(final Sockets socket) {
			this.socket = socket;
		}

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
					Thread.sleep(1000 / 5);
				}
				Thread.currentThread().interrupt();
			} catch (final InterruptedException exception) {
			} catch (final SocketException exception) {
				onDisconnect();
			} catch (final Exception exception) {
				Strings.exceptionToJSONObject(exception);
			}
		}
	}

	public void readMessage(final String message) {
		removeMessage(message);
	}

	protected transient Future<?> receiveFuture;

	private class Receive implements Runnable {
		private transient BufferedReader reader;
		private transient InputStream inputStream;

		private transient final Sockets socket;

		public Receive(final Sockets socket) {
			this.socket = socket;
		}

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
							if (message.length() >= socket
									.getEncryptionSizeLimit()) {
								try {
									message = Strings.decrypt(message,
											socket.getEncryptionKey());
								} catch (final Exception exception) {
									Strings.exceptionToJSONObject(exception);
								}
							}
							readMessage(message);

							// System.out.println("[" + Dates.now() + "]"
							// + "_SocketClientRead-->" + message + "_"
							// // + socket.getRemoteSocketAddress() +
							// // "_"
							// + socket.getProperties());
						}

						setIsConnected(true);
					}
					Thread.sleep(50);
				}
			} catch (final InterruptedException exception) {
			} catch (final SocketException exception) {
				onDisconnect();
			} catch (final Exception exception) {
				Strings.exceptionToJSONObject(exception);
			} finally {
				try {
					inputStream.close();
				} catch (final Exception exception) {
					// Strings.exceptionToJSONObject(exception);
				}
				try {
					reader.close();
				} catch (final Exception exception) {
					// Strings.exceptionToJSONObject(exception);
				}
			}
		}
	}

	private transient int keepAliveFailTry = 2;

	public void setKeepAliveFailTry(final int keepAliveFailTry) {
		this.keepAliveFailTry = keepAliveFailTry;
	}

	private transient int keepAliveTimeoutCount;

	protected transient Future<?> keepAliveFuture;

	private class KeepAlive implements Runnable {
		private transient final Sockets socket;

		public KeepAlive(final Sockets socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				while (true) {
					if (keepAliveTimeoutCount >= keepAliveFailTry) {
						keepAliveTimeoutCount = 0;
						onDisconnect();
						break;
					} else {
						keepAliveTimeoutCount++;
					}
					sendMessage(socket, Sockets.KEEPALIVE);
					Thread.sleep(1000 * 4);
				}
			} catch (final InterruptedException exception) {
			} catch (final SocketException exception) {
				onDisconnect();
			} catch (final Exception exception) {
				Strings.exceptionToJSONObject(exception);
			}
		}
	}

	public void onDisconnect() {
		try {
			keepAliveFuture.cancel(true);
		} catch (final Exception exception) {
			// Strings.exceptionToJSONObject(exception);
		}
		try {
			proposeFuture.cancel(true);
		} catch (final Exception exception) {
			// Strings.exceptionToJSONObject(exception);
		}
		try {
			receiveFuture.cancel(true);
		} catch (final Exception exception) {
			// Strings.exceptionToJSONObject(exception);
		}
		try {
			connectToServerFuture.cancel(true);
		} catch (final Exception exception) {
			// Strings.exceptionToJSONObject(exception);
		}
		try {
			socket.close();
		} catch (final Exception exception) {
			// Strings.exceptionToJSONObject(exception);
		}
		try {
			threadPool.shutdownNow();
		} catch (final Exception exception) {
			// Strings.exceptionToJSONObject(exception);
		}
		if (null != callback) {
			callback.onDisconnected(socket);
		}
	}

	public void onDestroy() {
		onDisconnect();
	}
}
