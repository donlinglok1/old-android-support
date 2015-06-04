package android.support.v4.net;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

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
public class SocketServer {
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

	private transient int encryptSizeLimit = 10;

	/**
	 * Default Value = 10;
	 */
	public void setEncryptSizeLimit(final int encryptSizeLimit) {
		this.encryptSizeLimit = encryptSizeLimit;
	}

	private transient ServerCallback callback;

	/**
	 * Default Value = 10;
	 */
	public void setServerCallback(final ServerCallback callback) {
		this.callback = callback;
	}

	public interface ServerCallback {
		void connected(Sockets result);
	}

	public void onStartCommand() {
	}

	public void waitForAccept() {
		ServerSockets serverSocket = null;
		try {
			serverSocket = new ServerSockets(serverPort);
		} catch (final IOException exception) {
			exception.printStackTrace();
		}

		while (true) {
			try {
				Thread.sleep(100);
				final Sockets socket = serverSocket.accept();
				socket.setReceiveBufferSize(16 * 1024);
				socket.setSendBufferSize(16 * 1024);
				socket.setSoLinger(true, 0);
				socket.setTcpNoDelay(true);
				socket.setKeepAlive(true);
				socket.setOOBInline(true);
				socket.setTrafficClass(0x04 | 0x10);
				socket.setPerformancePreferences(1, 3, 2);
				socket.setSoTimeout(30 * 1000);

				callback.connected(socket);
			} catch (final Exception exception) {
				exception.printStackTrace();
				try {
					serverSocket.close();
				} catch (final IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void sendMessages(final Sockets socket, final String message) {
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
						System.out.println("[" + Dates.now() + "]" + "_Send_"
								+ message + "_" + message.length() + "_"
								+ socket.getRemoteSocketAddress() + "_"
								+ socket.getTag());
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
	}

}
