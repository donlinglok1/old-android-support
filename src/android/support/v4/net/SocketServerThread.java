package android.support.v4.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Timer;
import java.util.TimerTask;

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
public class SocketServerThread extends Thread {

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
	public void setencryptSizeLimit(final int encryptSizeLimit) {
		this.encryptSizeLimit = encryptSizeLimit;
	}

	private transient ServerThreadCallBack serverThreadCallBack;

	public void setServerThreadCallBack(final ServerThreadCallBack callback) {
		serverThreadCallBack = callback;
	}

	public interface ServerThreadCallBack {
		void onCallBack(Sockets socket, JSONObject action);
	}

	private transient Sockets socket;

	public void setSocket(final Sockets socket) {
		this.socket = socket;
	}

	public void onStartCommand() {
	}

	private transient Timer timeoutTimer;

	public void setTimeoutTimer() {
		final int timeInteravl = 1000 * 3;
		timeoutTimer = new Timer();
		timeoutTimer.scheduleAtFixedRate(KeepAlive, 0, timeInteravl);
	}

	private transient int keepAliveTimeoutCount;

	private final transient TimerTask KeepAlive = new TimerTask() {
		@Override
		public void run() {
			try {
				if (keepAliveTimeoutCount > keepAliveFailTry) {
					keepAliveTimeoutCount = 0;
					try {
						final JSONObject action = new JSONObject();
						action.put(Sockets.ACTION, "kick");
						serverThreadCallBack.onCallBack(socket, action);
						socket.close();
						timeoutTimer.cancel();
					} catch (final Exception exception) {
					}
					Thread.currentThread().interrupt();
				} else {
					keepAliveTimeoutCount++;
				}
			} catch (final Exception exception) {
				exception.printStackTrace();
			}
		}
	};

	public void startListen() {
		boolean connected = true;// TODO

		BufferedReader reader = null;
		InputStream inputStream = null;

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
								System.out.println("[" + Dates.now() + "]"
										+ "_Read_" + message + "_"
										+ message.length() + "_"
										+ socket.getRemoteSocketAddress() + "_"
										+ socket.getTag());
							}
						} catch (final Exception exception) {
							exception.printStackTrace();
						}

						if (message.equals(Sockets.KEEPALIVE)) {
							keepAliveTimeoutCount = 0;
							final JSONObject action = new JSONObject();
							action.put(Sockets.ACTION, "send");
							action.put(Sockets.RETURN,
									Sockets.KEEPALIVE_REACTION);
							serverThreadCallBack.onCallBack(socket, action);
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
			}
			try {
				reader.close();
			} catch (final Exception exception) {
			}
			try {
				final JSONObject action = new JSONObject();
				action.put(Sockets.ACTION, "kick");
				serverThreadCallBack.onCallBack(socket, action);
				socket.close();
				timeoutTimer.cancel();
			} catch (final Exception exception) {
			}
			Thread.currentThread().interrupt();
		}
	}

	public void readMessage(final String message) {
	}

}