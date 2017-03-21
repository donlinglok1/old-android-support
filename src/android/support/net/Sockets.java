package android.support.net;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketImpl;

import android.n.NString;
import android.support.lang.Crypt;
import android.support.lang.Strings;
import net.minidev.json.JSONObject;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class Sockets extends Socket {
	public Sockets(final InetAddress serverAddr, final int serverport) throws IOException {
		super(serverAddr, serverport);
	}

	public Sockets(final SocketImpl socketImpl) throws SocketException {
		super(socketImpl);
	}

	public Sockets() throws SocketException {
		super();
	}

	private transient String idString;

	public final void setIdString(final String idString) {
		this.idString = idString;
	}

	public final String getIdString() {
		return idString;
	}

	private transient JSONObject propertiesObject;

	public final void setProperties(final JSONObject propertiesObject) {
		this.propertiesObject = propertiesObject;
	}

	public final JSONObject getProperties() {
		return propertiesObject;
	}

	private transient String encryptionKey = NString.add(Strings.UPPK, Strings.LOWE, Strings.LOWN, Strings.LOWN,
			Strings.LOWN, Strings.LOWE, Strings.LOWT, Strings.LOWH);

	public final void setEncryptionKey(final String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	public final String getEncryptionKey() {
		return encryptionKey;
	}

	public static final int BUFFERSIZE = 32;

	public static final int QUEUEFAILTRY = 3;
	public static final int PROPOSESPEED = 1000 / 10;

	public static final int KEEPALIVEFAILTRY = 2;
	public static final int KEEPALIVESPEED = 1000 * 6;

	public static final int SERVERPORT = 40;

	public static final int ENCRYPTSIZELIMIT = 10;

	public final void send(final String message) throws IOException {
		if (!isOutputShutdown() && !isClosed()) {
			final OutputStream out = new BufferedOutputStream(getOutputStream());
			final PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));

			String outMessage = message;
			if (outMessage.length() >= ENCRYPTSIZELIMIT) {
				outMessage = Crypt.encrypt(outMessage, getEncryptionKey());
			}

			writer.println(outMessage);
			writer.flush();
		}
	}

	public static final String SENDER = NString.add(Strings.LOWS, Strings.UPPE, Strings.LOWN);
	public static final String RECEIVER = NString.add(Strings.LOWR, Strings.UPPE, Strings.LOWC, Strings.UPPV);
	public static final String MSG_CODE = NString.add(Strings.LOWC, Strings.LOWO, Strings.LOWD, Strings.LOWE);
	public static final String ACTION = NString.add(Strings.UPPA, Strings.UPPC, Strings.UPPT, Strings.UPPI,
			Strings.UPPO, Strings.UPPN);
	public static final String RETURN = NString.add(Strings.UPPR, Strings.UPPE, Strings.UPPT, Strings.UPPU,
			Strings.UPPR, Strings.UPPN);

	public static final String KEEPALIVE = Strings.LOWK;
	public static final String KEEPALIVE_REACTION = Strings.LOWT;
	public static final String DISCONNECT = NString.add(Strings.UPPD, Strings.UPPI, Strings.UPPS, Strings.UPPC,
			Strings.UPPO, Strings.UPPN);
	public static final String DUPLICATE_LOGIN = NString.add(Strings.UPPD, Strings.UPPU, Strings.UPPP, Strings.UPPL,
			Strings.UPPO, Strings.UPPG);
}