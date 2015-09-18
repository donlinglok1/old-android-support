package android.support.v4.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	public interface ServerHandlerCallBack {
		void onCallBack(SocketServerHandler handler, JSONObject action);

		void onDisconnected(SocketServerHandler handler);
	}

	public transient ServerHandlerCallBack callBack;

	public void setCallback(final ServerHandlerCallBack callBack) {
		this.callBack = callBack;
	}

	private transient ExecutorService threadPool;

	public final void setThreadPool(final ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	public transient Sockets socket;

	public final void connectToClient(final Sockets socket) {
		this.socket = socket;
		if (null != receiveFuture) {
			receiveFuture.cancel(true);
			receiveFuture = null;
		}
		receiveFuture = threadPool.submit(new Receive());

		if (null != keepAliveFuture) {
			keepAliveFuture.cancel(true);
			keepAliveFuture = null;
		}
		keepAliveFuture = threadPool.submit(new KeepAlive());
	}

	public final void sendMessage(final String message) {
		if (null != socket) {
			try {
				socket.send(message);
				if (!message.equals(Sockets.KEEPALIVE_REACTION)) {
					System.out.println("[" + Dates.now() + "]"
							+ "_SocketServerSend-->" + message + "_"
							// +socket.getRemoteSocketAddress() + "_"
							+ socket.getProperties());
				}
			} catch (final IOException exception) {
				Strings.exceptionToJSONObject(exception);
			}
		}
	}

	public void readMessage(final String message) {
		if (null != socket) {
			System.out.println("[" + Dates.now() + "]" + "_SocketServerRead-->"
					+ message + "_"
					// + socket.getRemoteSocketAddress() +
					// "_"
					+ socket.getProperties());
		}
	}

	public transient Future<?> receiveFuture;

	private class Receive implements Runnable {
		private transient BufferedReader reader;
		private transient InputStream inputStream;

		@Override
		public final void run() {
			try {
				inputStream = new BufferedInputStream(socket.getInputStream());
				reader = new BufferedReader(new InputStreamReader(inputStream,
						"UTF-8"));
				while (socket.isConnected()) {
					String message = reader.readLine();
					if (message != null && message.length() != 0) {
						if (message.equals(Sockets.KEEPALIVE)) {
							keepAliveTimeoutCount = 0;
							sendMessage(Sockets.KEEPALIVE_REACTION);
						} else {
							if (message.length() >= Sockets.ENCRYPTSIZELIMIT) {
								message = Strings.decrypt(message,
										socket.getEncryptionKey());
							}
							readMessage(message);
						}
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

	public transient Future<?> keepAliveFuture;

	private class KeepAlive implements Runnable {
		@Override
		public final void run() {
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
					Thread.sleep(Sockets.KEEPALIVESPEED);
				} catch (final InterruptedException exception) {
				}
			}
		}
	}

	public final void onDisconnect() {
		if (null != keepAliveFuture) {
			keepAliveFuture.cancel(true);
			keepAliveFuture = null;
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
		if (null != callBack) {
			callBack.onDisconnected(SocketServerHandler.this);
			callBack = null;
		}
	}
}