/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
package android.support.v4.os.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.n.NString;
import android.os.AsyncTask;
import android.support.v4.lang.Strings;

public class AssetsTask extends AsyncTask<Void, Void, String> {
	private final transient Context context;
	private transient AssetsCallback callback;
	private transient String[] dontCopyStrings = { "fonts", "images", "sounds", "webkit",
			"crashlytics-build.properties", "googlemap", "offlinemap", "icudt46l.zip", "icudtl.dat",
			"natives_blob_32.bin", "snapshot_blob_32.bin", "webview_licenses.notice", "webviewchromium.pak" };

	private transient ArrayList<String> dontCopy = new ArrayList<String>();

	public static abstract interface AssetsCallback {
		public abstract void onReturn(Boolean result);
	}

	public AssetsTask(final Context context) {
		super();
		this.context = context;
		for (final String dontCopyString : dontCopyStrings) {
			dontCopy.add(dontCopyString);
		}
	}

	public AssetsTask(final Context context, final ArrayList<String> dontCopy) {
		super();
		this.context = context;
		this.dontCopy = dontCopy;
		for (final String dontCopyString : dontCopyStrings) {
			dontCopy.add(dontCopyString);
		}
	}

	public AssetsTask(final Context context, final AssetsCallback callback, final ArrayList<String> dontCopy) {
		super();
		this.context = context;
		this.callback = callback;
		this.dontCopy = dontCopy;
		for (final String dontCopyString : dontCopyStrings) {
			dontCopy.add(dontCopyString);
		}
	}

	@Override
	public String doInBackground(final Void... params) {
		try {
			copyAssets(context);
			if (null != callback) {
				callback.onReturn(true);
			}
		} catch (final IOException exception) {
			exception.printStackTrace();
			if (null != callback) {
				callback.onReturn(false);
			}
		}
		return Strings.EMPTY;
	}

	private final void copyAssets(final Context context) throws IOException {
		copyAssets(context, Strings.EMPTY);
	}

	private final void copyAssets(final Context context, final String folderName) throws IOException {
		final String dirPathString = NString.add(Files.getTempFolderPath(context), folderName);
		new File(dirPathString).mkdirs();

		final AssetManager assetManager = context.getAssets();
		final String[] files = assetManager.list(folderName.replace(Strings.SLASH, Strings.EMPTY));
		if (null != files) {
			for (final String file : files) {
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
							final InputStream inputStream = assetManager.open(NString.add(folderName, file));
							final OutputStream outputStream = new FileOutputStream(outFile);
							Files.copyFile(inputStream, outputStream);
							inputStream.close();
							outputStream.flush();
							outputStream.close();
						}
					} else {
						copyAssets(context, NString.add(file, Strings.SLASH));
					}
				}
			}
		}
	}
}
