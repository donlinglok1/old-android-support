package android.support.v4.os.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.lang.Strings;

public class Assets extends AsyncTask<Void, Void, String> {
	private transient final Context context;
	private transient AssetsCallback callback;
	private transient String[] dontCopy;

	public interface AssetsCallback {
		void onReturn(Boolean result);
	}

	public Assets(final Context context) {
		super();
		this.context = context;
	}

	public Assets(final Context context, final String... dontCopy) {
		super();
		this.context = context;
		this.dontCopy = dontCopy;
	}

	public Assets(final Context context, final AssetsCallback callback,
			final String... dontCopy) {
		super();
		this.context = context;
		this.callback = callback;
		this.dontCopy = dontCopy;
	}

	@Override
	public String doInBackground(final Void... params) {
		try {
			copyAssets(context);
			if (null != callback) {
				callback.onReturn(true);
			}
		} catch (final IOException exception) {
			Strings.exceptionToJSONObject(exception);
			if (null != callback) {
				callback.onReturn(false);
			}
		}
		return Strings.EMPTY;
	}

	private void copyAssets(final Context context) throws IOException {
		copyAssets(context, Strings.EMPTY);
	}

	private void copyAssets(final Context context, final String folderName)
			throws IOException {
		final String dirPathString = Strings.fString(
				Strings.valueOf(Environment.getExternalStorageDirectory()),
				Strings.SLASH, Strings.UPPA, Strings.LOWN, Strings.LOWD,
				Strings.LOWR, Strings.LOWO, Strings.LOWI, Strings.LOWD,
				Strings.SLASH, Strings.LOWD, Strings.LOWA, Strings.LOWT,
				Strings.LOWA, Strings.SLASH, context.getPackageName(),
				Strings.SLASH, folderName);
		new File(dirPathString).mkdirs();

		final AssetManager assetManager = context.getAssets();
		final String[] files = assetManager.list(folderName.replace(
				Strings.SLASH, Strings.EMPTY));
		if (null != files) {
			for (final String file : files) {
				try {
					boolean isNoCopyFile = false;
					for (final String element : dontCopy) {
						if (element.equals(file)) {
							isNoCopyFile = true;
						}
					}
					if (!isNoCopyFile) {
						if (file.contains(".")) {
							final File outFile = new File(dirPathString, file);
							if (!outFile.exists()) {
								final InputStream inputStream = assetManager
										.open(Strings.fString(folderName, file));
								final OutputStream outputStream = new FileOutputStream(
										outFile);
								Files.copyFile(inputStream, outputStream);
								inputStream.close();
								outputStream.flush();
								outputStream.close();
							}
						} else {
							copyAssets(context,
									Strings.fString(file, Strings.SLASH));
						}
					}
				} catch (final Exception exception) {
					Strings.exceptionToJSONObject(exception);
				}
			}
		}
	}

}
