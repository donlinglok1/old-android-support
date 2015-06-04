package android.support.v4.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketImpl;

import net.minidev.json.JSONObject;
import android.support.v4.lang.Strings;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class Sockets extends Socket {
	public Sockets(final InetAddress serverAddr, final int serverport)
			throws IOException {
		super(serverAddr, serverport);
	}

	public Sockets(final SocketImpl socketImpl) throws SocketException {
		super(socketImpl);
	}

	public Sockets() throws SocketException {
		super();
	}

	private transient JSONObject tag = new JSONObject();

	public void setTag(final JSONObject Tag) {
		tag = Tag;
	}

	public JSONObject getTag() {
		return tag;
	}

	public final static String KEEPALIVE = Strings.LOWK;
	public final static String KEEPALIVE_REACTION = Strings.LOWT;
	public final static String MSG_CODE = Strings.fString(Strings.LOWC,
			Strings.LOWO, Strings.LOWD, Strings.LOWE);
	public final static String ACTION = Strings.fString(Strings.UPPA,
			Strings.UPPC, Strings.UPPT, Strings.UPPI, Strings.UPPO,
			Strings.UPPN);
	public final static String RETURN = Strings.fString(Strings.UPPR,
			Strings.UPPE, Strings.UPPT, Strings.UPPU, Strings.UPPR,
			Strings.UPPN);
	public final static String DUPLICATE_LOGIN = Strings.fString(Strings.UPPD,
			Strings.UPPU, Strings.UPPP, Strings.UPPL, Strings.UPPO,
			Strings.UPPG);
	public final static String DISCONNECT = Strings.fString(Strings.UPPD,
			Strings.UPPI, Strings.UPPS, Strings.UPPC, Strings.UPPO,
			Strings.UPPN);
	public final static String ID = Strings.fString(Strings.LOWI, Strings.LOWD);

}