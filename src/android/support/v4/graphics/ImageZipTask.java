package android.support.v4.graphics;

import java.io.FileOutputStream;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.support.v4.os.storage.Files;
import android.support.v4.util.AsyncTask;

public class ImageZipTask extends AsyncTask<Void, Void, String> {
	private transient final ArrayList<String> imageArrayList;
	private transient final int compressSize;
	private transient final String uploadFilePath;
	private transient final ZipImagesTaskCallback callback;

	public interface ZipImagesTaskCallback {
		void onReturn(String result);
	}

	public ImageZipTask(final ArrayList<String> imageArrayList,
			final int compressSize, final String uploadFilePath,
			final ZipImagesTaskCallback callback) {
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
				final Bitmap bitmap = Bitmaps.getBitmap(imgPath, compressSize,
						compressSize, true);

				out = new FileOutputStream(imgPath);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
				fileStrings.add(imgPath);
			} catch (final Exception exception) {
				// exception.printStackTrace();
			} finally {
				try {
					out.close();
				} catch (final Exception exception) {
					// exception.printStackTrace();
				}
			}
		}

		try {
			Files.zip(fileStrings.toArray(new String[fileStrings.size()]),
					uploadFilePath);
		} catch (final Exception exception) {
			// exception.printStackTrace();
		}

		return uploadFilePath;
	}

	@Override
	protected void onPostExecute(final String result) {
		super.onPostExecute(result);
		callback.onReturn(result);
	}
}
