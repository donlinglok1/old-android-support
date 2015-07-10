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
	public interface ServerCallback {
		void onConnected(Sockets socket);
	}

	private transient ServerCallback callback;

	public void setCallback(final ServerCallback callback) {
		this.callback = callback;
	}

	private transient int serverPort = 40;

	public void setServerPort(final int serverPort) {
		this.serverPort = serverPort;
	}

	public void waitForClient() {
		try {
			final ServerSockets serverSocket = new ServerSockets(serverPort);

			while (null != serverSocket) {
				final Sockets socket = serverSocket.accept();
				socket.setReceiveBufferSize(8 * 1024);
				socket.setSendBufferSize(8 * 1024);
				// socket.setSoLinger(true, 0);
				socket.setTcpNoDelay(true);
				socket.setKeepAlive(true);
				socket.setOOBInline(false);
				socket.setTrafficClass(0x04 | 0x10);
				socket.setPerformancePreferences(1, 3, 2);
				socket.setSoTimeout(30 * 1000);

				if (null != callback) {
					callback.onConnected(socket);
				}
			}
		} catch (final Exception exception) {
			Strings.exceptionToJSONObject(exception);
		}
	}

	public void sendMessages(final Sockets socket, final String message)
			throws IOException {
		if (null != socket && !socket.isOutputShutdown() && !socket.isClosed()) {
			final OutputStream out = new BufferedOutputStream(
					socket.getOutputStream());
			final PrintWriter writer = new PrintWriter(new OutputStreamWriter(
					out, "UTF-8"));

			String outMessage = message;
			if (outMessage.length() >= socket.getEncryptionSizeLimit()) {
				System.out.println("[" + Dates.now() + "]"
						+ "_SocketServerSend_" + outMessage + "_"
						// +socket.getRemoteSocketAddress() + "_"
						+ socket.getProperties());
				try {
					outMessage = Strings.encrypt(outMessage,
							socket.getEncryptionKey());
				} catch (final Exception exception) {
					Strings.exceptionToJSONObject(exception);
				}
			}

			writer.println(outMessage);
			writer.flush();
		}
	}

}
