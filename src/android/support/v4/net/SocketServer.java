package android.support.v4.net;

import java.io.IOException;

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

	public final void setCallback(final ServerCallback callback) {
		this.callback = callback;
	}

	public final void waitForClient() {
		try {
			final ServerSockets serverSocket = new ServerSockets(
					Sockets.SERVERPORT);

			while (null != serverSocket) {
				final Sockets socket = serverSocket.accept();
				socket.setReceiveBufferSize(Sockets.BUFFERSIZE / 2 * 1024);
				socket.setSendBufferSize(Sockets.BUFFERSIZE * 1024);
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
		} catch (final IOException exception) {
		}
	}
}
