package android.support.net.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.n.NString;
import android.os.AsyncTask;
import android.os.Environment;

public class HttpDownloadImageTask extends AsyncTask<String, Integer, String> {
	private final transient Context context;
	private final transient HttpDownloadImageCallback callback;
	private final transient String url;
	private final transient String output;
	private final transient boolean isDialog;

	public static abstract interface HttpDownloadImageCallback {
		public abstract void onSuccess(File outputFile);

		public abstract void onFail(Exception exception);
	}

	public HttpDownloadImageTask(final Context context, final HttpDownloadImageCallback callback, final String url,
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
				final URL imageURL = new URL(url);
				final Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
				final ByteArrayOutputStream bos = new ByteArrayOutputStream();
				bitmap.compress(CompressFormat.JPEG, 90, bos);
				final byte[] bitmapdata = bos.toByteArray();

				final FileOutputStream fos = new FileOutputStream(outputFile);
				fos.write(bitmapdata);
				fos.flush();
				fos.close();
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