package android.support.v4.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.SocketImpl;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class ServerSockets extends ServerSocket {
	public ServerSockets() throws IOException {
		super();
	}

	public ServerSockets(final int serverport) throws IOException {
		super(serverport);
	}

	@Override
	public Sockets accept() throws IOException {
		if (isClosed()) {
			throw new SocketException("Socket is closed");
		}
		if (!isBound()) {
			throw new SocketException("Socket is not bound yet");
		}
		final Sockets socket = new Sockets((SocketImpl) null);
		implAccept(socket);
		return socket;
	}

}