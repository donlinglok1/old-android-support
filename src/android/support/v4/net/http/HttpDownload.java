package android.support.v4.net.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Environment;
import android.support.v4.lang.Strings;

public class HttpDownload {
	public interface HttpDownloadCallback {
		void onSuccess(File outputFile);

		void onFail(Exception exception);
	}

	public HttpDownload(final Activity context,
			final HttpDownloadCallback callback, final String url,
			final String output) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					final File file = new File(Strings.fString(
							Strings.valueOf(Environment
									.getExternalStorageDirectory()),
							Strings.SLASH, Strings.UPPA, Strings.LOWN,
							Strings.LOWD, Strings.LOWR, Strings.LOWO,
							Strings.LOWI, Strings.LOWD, Strings.SLASH,
							Strings.LOWD, Strings.LOWA, Strings.LOWT,
							Strings.LOWA, Strings.SLASH, context
									.getPackageName(), Strings.SLASH));
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
						while ((len = inputStream.read(buffer)) != -1) {
							outputStream.write(buffer, 0, len);
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
			}
		}).start();
	}
}