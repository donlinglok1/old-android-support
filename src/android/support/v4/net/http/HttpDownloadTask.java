package android.support.v4.net.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.n.NString;
import android.os.AsyncTask;
import android.os.Environment;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class HttpDownloadTask extends AsyncTask<String, Integer, String> {
	private final transient Context context;
	private final transient HttpDownloadTaskCallback callback;
	private final transient String url;
	private final transient String output;
	private final transient boolean isDialog;

	public static abstract interface HttpDownloadTaskCallback {
		public abstract void onSuccess(File outputFile);

		public abstract void onFail(Exception exception);
	}

	public HttpDownloadTask(final Context context, final HttpDownloadTaskCallback callback, final String url,
			final String output, final boolean isDialog) {
		super();
		this.context = context;
		this.callback = callback;
		this.url = url;
		this.output = output;
		this.isDialog = isDialog;
	}

	private transient ProgressDialog progressDialog;

	@Override
	public void onPreExecute() {
		if (isDialog && context instanceof Activity && !((Activity) context).isFinishing()) {
			((Activity) context).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					progressDialog = new ProgressDialog(context);
					progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					progressDialog.setIndeterminate(false);
					progressDialog.setCancelable(false);
					progressDialog.setMax(100);
					progressDialog.show();
				}
			});
		}
	}

	@Override
	protected void onProgressUpdate(final Integer... progress) {
		if (isDialog) {
			progressDialog.setProgress(progress[0]);
		}
	}

	@Override
	protected String doInBackground(final String... arg0) {
		try {
			final File file = new File(NString.add(Environment.getExternalStorageDirectory(), "/Android/data/",
					context.getPackageName(), "/"));
			file.mkdirs();
			final File outputFile = new File(file, output);
			if (!outputFile.exists()) {
				final HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
				httpURLConnection.setRequestMethod("GET");
				httpURLConnection.setDoOutput(true);
				httpURLConnection.connect();
				final FileOutputStream outputStream = new FileOutputStream(outputFile);
				final InputStream inputStream = httpURLConnection.getInputStream();

				final byte[] buffer = new byte[1024];
				int len = 0;
				long total = 0;
				while ((len = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, len);
					total += len;
					if (isDialog) {
						publishProgress((int) (total * 100 / httpURLConnection.getContentLength()));
					}
				}
				outputStream.close();
				inputStream.close();
			}
			callback.onSuccess(outputFile);
		} catch (final MalformedURLException exception) {
			callback.onFail(exception);
		} catch (final IOException exception) {
			callback.onFail(exception);
		}

		if (isDialog) {
			progressDialog.dismiss();
		}

		return "";
	}
}