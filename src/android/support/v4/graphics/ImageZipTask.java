package android.support.v4.graphics;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.os.storage.Files;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class ImageZipTask extends AsyncTask<Void, Void, String> {
	private final transient ArrayList<String> imageArrayList;
	private final transient int compressSize;
	private final transient String uploadFilePath;
	private final transient ImageZipTaskCallback callback;

	public static abstract interface ImageZipTaskCallback {
		public abstract void onReturn(String result);
	}

	public ImageZipTask(final ArrayList<String> imageArrayList, final int compressSize, final String uploadFilePath,
			final ImageZipTaskCallback callback) {
		super();
		this.imageArrayList = imageArrayList;
		this.compressSize = compressSize;
		this.uploadFilePath = uploadFilePath;
		this.callback = callback;
	}

	@Override
	public String doInBackground(final Void... params) {
		final ArrayList<String> fileStrings = new ArrayList<String>();

		final int loopStopNum = imageArrayList.size();
		for (int i = 0; i < loopStopNum; i++) {
			final String imgPath = imageArrayList.get(i);

			FileOutputStream out = null;
			try {
				final Bitmap bitmap = Bitmaps.getBitmap(imgPath, compressSize, true);

				out = new FileOutputStream(imgPath);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
				fileStrings.add(imgPath);
			} catch (final IOException exception) {
			} finally {
				try {
					out.close();
				} catch (final IOException exception) {
				}
			}
		}

		try {
			Files.zip(fileStrings.toArray(new String[fileStrings.size()]), uploadFilePath);
		} catch (final IOException exception) {
		}

		return uploadFilePath;
	}

	@Override
	public void onPostExecute(final String result) {
		super.onPostExecute(result);
		callback.onReturn(result);
	}
}
