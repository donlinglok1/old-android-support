package android.support.v4.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import net.minidev.json.JSONObject;
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
public class SocketServerHandler {
	public interface SocketServerHandlerCallBack {
		void onSend(Sockets socket, String msg);

		void onBroadcast(Sockets socket, JSONObject action);

		void onCallBack(Sockets socket, JSONObject action);

		void onDisconnected(Sockets socket);
	}

	private transient SocketServerHandlerCallBack callback;

	public void setCallback(final SocketServerHandlerCallBack callback) {
		this.callback = callback;
	}

	private transient ExecutorService threadPool;

	public void setThreadPool(final ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	private transient Sockets socket;

	public void connectToClient(final Sockets socket) {
		this.socket = socket;
		try {
			receiveFuture.cancel(true);
		} catch (final Exception exception) {
			// Strings.exceptionToJSONObject(exception);
		}
		receiveFuture = threadPool.submit(new Receive(socket));

		try {
			keepAliveFuture.cancel(true);
		} catch (final Exception exception) {
			// Strings.exceptionToJSONObject(exception);
		}
		keepAliveFuture = threadPool.submit(new KeepAlive(socket));
	}

	public void readMessage(final String message) {
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
					if (message != null && message.length() != 0) {
						if (message.equals(Sockets.KEEPALIVE)) {
							keepAliveTimeoutCount = 0;
							callback.onSend(socket, Sockets.KEEPALIVE_REACTION);
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

							System.out.println("[" + Dates.now() + "]"
									+ "_SocketServerRead-->" + message + "_"
									// + socket.getRemoteSocketAddress() +
									// "_"
									+ socket.getProperties());
						}
					}
					Thread.sleep(5);
				}
			} catch (final InterruptedException exception) {
				// } catch (final SocketTimeoutException exception) {
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

	private transient int keepAliveFailTry = 5;

	public void setKeepAliveFailTry(final int keepAliveFailTry) {
		this.keepAliveFailTry = keepAliveFailTry;
	}

	private transient int keepAliveTimeoutCount;

	protected transient Future<?> keepAliveFuture;

	private class KeepAlive implements Runnable {
		// private transient final Sockets socket;

		public KeepAlive(final Sockets socket) {
			// this.socket = socket;
		}

		@Override
		public void run() {
			while (true) {
				if (keepAliveTimeoutCount > keepAliveFailTry) {
					keepAliveTimeoutCount = 0;
					onDisconnect();
					break;
				} else {
					keepAliveTimeoutCount++;
				}
				try {
					Thread.sleep(1000 * 4);
				} catch (final InterruptedException exception) {
				} catch (final Exception exception) {
					Strings.exceptionToJSONObject(exception);
				}
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
			receiveFuture.cancel(true);
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
}