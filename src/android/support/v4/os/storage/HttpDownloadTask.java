package android.support.v4.os.storage;

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
import android.os.Environment;
import android.support.v4.graphics.bitmapfun.AsyncTask;
import android.support.v4.lang.Strings;

public class HttpDownloadTask extends AsyncTask<String, String, String> {
	final private transient Context context;
	final private transient HttpDownloadTaskCallback callback;
	final private transient String url;
	final private transient String output;

	public interface HttpDownloadTaskCallback {
		void onSuccess(File outputFile);

		void onFail(Exception exception);
	}

	public HttpDownloadTask(final Activity context,
			final HttpDownloadTaskCallback callback, final String url,
			final String output) {
		super();
		this.context = context;
		this.callback = callback;
		this.url = url;
		this.output = output;
	}

	private transient ProgressDialog progressDialog;

	@Override
	public final void onPreExecute() {
		if (context instanceof Activity && !((Activity) context).isFinishing()) {
			((Activity) context).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					progressDialog = new ProgressDialog(context);
					progressDialog
							.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					progressDialog.setIndeterminate(false);
					progressDialog.setCancelable(false);
					progressDialog.setMax(100);
					progressDialog.show();
				}
			});
		}
	}

	@Override
	public final void onProgressUpdate(final String... progress) {
		progressDialog.setProgress(Integer.parseInt(progress[0]));
	}

	@Override
	public final String doInBackground(final String... arg0) {
		try {
			final File file = new File(Strings.fString(
					Strings.valueOf(Environment.getExternalStorageDirectory()),
					Strings.SLASH, Strings.UPPA, Strings.LOWN, Strings.LOWD,
					Strings.LOWR, Strings.LOWO, Strings.LOWI, Strings.LOWD,
					Strings.SLASH, Strings.LOWD, Strings.LOWA, Strings.LOWT,
					Strings.LOWA, Strings.SLASH, context.getPackageName(),
					Strings.SLASH));
			file.mkdirs();
			final File outputFile = new File(file, output);
			if (!outputFile.exists()) {
				final HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(
						url).openConnection();
				httpURLConnection.setRequestMethod("GET");
				httpURLConnection.setDoOutput(true);
				httpURLConnection.connect();
				final FileOutputStream outputStream = new FileOutputStream(
						outputFile);
				final InputStream inputStream = httpURLConnection
						.getInputStream();

				final byte[] buffer = new byte[1024];
				int len = 0;
				long total = 0;
				while ((len = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, len);
					total += len;
					publishProgress(Strings.valueOf(total * 100
							/ httpURLConnection.getContentLength()));
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

		progressDialog.dismiss();

		return Strings.EMPTY;
	}
}