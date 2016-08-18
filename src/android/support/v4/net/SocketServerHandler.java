package android.support.v4.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.n.NDate;
import android.support.v4.lang.Base64s;
import android.support.v4.util.Tools;
import net.minidev.json.JSONObject;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class SocketServerHandler {
    public interface SocketServerHandlerCallback {
	void onCallback(SocketServerHandler handler, JSONObject action);

	void onDisconnected(SocketServerHandler handler);
    }

    public transient SocketServerHandlerCallback callback;

    public void setCallback(final SocketServerHandlerCallback callback) {
	this.callback = callback;
    }

    public transient Sockets socket;
    private transient ExecutorService threadPool;

    public void connectToClient(final Sockets socket) {
	this.socket = socket;

	threadPool = Executors.newFixedThreadPool(2);
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

    public void sendMessage(final String message) {
	if (null != socket) {
	    try {
		socket.send(message);
		if (!Sockets.KEEPALIVE_REACTION.equals(message)) {
		    System.out.println("[" + NDate.getDate() + "]" + "_SocketServerSend-->" + message + "_"
			    + socket.getProperties());
		}
	    } catch (final IOException exception) {
		Tools.exceptionToJSONObject(exception);
	    }
	}
    }

    public void readMessage(final String message) {
	if (null != socket && !Sockets.KEEPALIVE.equals(message)) {
	    System.out.println(
		    "[" + NDate.getDate() + "]" + "_SocketServerRead-->" + message + "_" + socket.getProperties());
	}
    }

    public transient Future<?> receiveFuture;

    private class Receive implements Runnable {
	private transient BufferedReader reader;
	private transient InputStream inputStream;

	@Override
	public void run() {
	    try {
		inputStream = new BufferedInputStream(socket.getInputStream());
		reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while (socket.isConnected()) {
		    String message = reader.readLine();
		    if (message != null && message.length() != 0) {
			if (Sockets.KEEPALIVE.equals(message)) {
			    keepAliveTimeoutCount = 0;
			    sendMessage(Sockets.KEEPALIVE_REACTION);
			} else {
			    if (message.length() >= Sockets.ENCRYPTSIZELIMIT) {
				message = Base64s.decrypt(message, socket.getEncryptionKey());
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
		Tools.exceptionToJSONObject(exception);
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
	public void run() {
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

    public void onDisconnect() {
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
	if (null != callback) {
	    callback.onDisconnected(SocketServerHandler.this);
	}
    }
}