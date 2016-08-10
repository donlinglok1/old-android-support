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
    public interface SocketServerCallback {
	void onConnected(Sockets socket);
    }

    private transient SocketServerCallback callback;

    public void setCallback(final SocketServerCallback callback) {
	this.callback = callback;
    }

    public void waitForClient() {
	ServerSockets serverSocket = null;
	try {
	    serverSocket = new ServerSockets(Sockets.SERVERPORT);

	    while (null != serverSocket) {
		final Sockets socket = serverSocket.accept();
		socket.setReceiveBufferSize(Sockets.BUFFERSIZE / 2 * 1024);
		socket.setSendBufferSize(Sockets.BUFFERSIZE * 1024);
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
	    if (null != serverSocket) {
		try {
		    serverSocket.close();
		} catch (final IOException e) {
		}
	    }
	}
    }
}
