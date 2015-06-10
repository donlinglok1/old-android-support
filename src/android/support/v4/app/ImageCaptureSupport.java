package android.support.v4.app;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

public class ImageCaptureSupport extends Activity {
	private transient Activity context;
	private Context baseContext;
	public final static String IMAGE_PATH = "ImageFilePath";
	public final static int CAP_REQUESTCODE = 6260;

	private transient String filePath;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = ImageCaptureSupport.this;
		baseContext = getApplicationContext();

		if (savedInstanceState != null) {
			filePath = savedInstanceState.getString(IMAGE_PATH);

			final File mFile = new File(IMAGE_PATH);
			if (mFile.exists()) {
				final Intent rsl = new Intent();
				rsl.putExtra(IMAGE_PATH, filePath);
				setResult(Activity.RESULT_OK, rsl);
				finish();
			}
		}

		if (savedInstanceState == null) {
			final long ts = System.currentTimeMillis();

			String filePath = "/Android/data/" + context.getPackageName()
					+ "/temp/ImageCaptureSupport/";
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				filePath = Environment.getExternalStorageDirectory() + filePath;
			} else {
				filePath = baseContext.getCacheDir() + filePath;
			}
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = null;
			final File nomedia = new File(filePath + "/.nomedia");
			if (!nomedia.exists()) {
				try {
					nomedia.createNewFile();
				} catch (final IOException e) {
				}
			}

			filePath = filePath + ts + ".jpg";
			final File out = new File(filePath);

			final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); // set
			startActivityForResult(intent, CAP_REQUESTCODE);
		}
	}

	@Override
	public void onActivityResult(final int requestCode, final int resultCode,
			final Intent intent) {
		if (Activity.RESULT_OK == resultCode && CAP_REQUESTCODE == requestCode) {
			final Intent intent2 = new Intent();
			intent2.putExtra(IMAGE_PATH, filePath);
			context.setResult(Activity.RESULT_OK, intent2);
			context.finish();
		} else {
			context.finish();
		}
	}

	@Override
	protected void onSaveInstanceState(final Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(IMAGE_PATH, filePath);
	}

	// @Override
	// protected void onDestroy() {
	// super.onDestroy();
	// }
	// @Override
	// public void onConfigurationChanged(final Configuration newConfig) {
	// super.onConfigurationChanged(newConfig);
	// }
	//
	// @Override
	// protected void onRestoreInstanceState(final Bundle savedInstanceState) {
	// super.onRestoreInstanceState(savedInstanceState);
	// }
}
