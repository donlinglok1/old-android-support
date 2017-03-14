package android.support.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.n.It;
import android.n.NFragmentActivity;
import android.n.NString;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.MediaColumns;
import android.support.graphics.Bitmaps;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
/**
 *
 * @author kennetht
 *
 */
public class ImageEditorActivity extends NFragmentActivity {
	public static final String IMAGE_KEY = "image_path";
	public static final int CAP_PHOTO = 6262;
	public static final int PICK_PHOTO = 2626;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		start();
	}

	public final void start() {
		if (It.isEqual(MediaStore.ACTION_IMAGE_CAPTURE, getIntent().getExtras().getString("method"))) {
			startActivityForResult(new Intent(appContext, ImageCaptureSupport.class), CAP_PHOTO);
		} else {
			startActivityForResult(new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI), PICK_PHOTO);
		}
	}

	@Override
	public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
		if (It.isEqual(resultCode, Activity.RESULT_OK)) {
			String path;
			if (It.isEqual(CAP_PHOTO, requestCode)) {
				path = intent.getStringExtra(ImageCaptureSupport.IMAGE_PATH);
			} else {
				try {
					final String[] filePathColumn = { MediaColumns.DATA };
					final Cursor cursor = appContext.getContentResolver().query(intent.getData(), filePathColumn, null,
							null, null);
					cursor.moveToFirst();
					path = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
					cursor.close();
				} catch (final Exception exception) {
					exception.printStackTrace();
					final String[] filePathColumn = { MediaColumns.DATA.substring(0, MediaColumns.DATA.length() - 1) };
					final Cursor cursor = appContext.getContentResolver().query(intent.getData(), filePathColumn, null,
							null, null);
					cursor.moveToFirst();
					path = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
					cursor.close();
				}
			}

			String filePath = It.isEqual(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)
					? Environment.getExternalStorageDirectory().getAbsolutePath()
					: activity.getCacheDir().getAbsolutePath();

			filePath = NString.add(filePath, "/Android/data/", activity.getPackageName(), "/temp/ImageCaptureSupport/");

			final File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}

			final File nomedia = new File(filePath + "/.nomedia");
			if (!nomedia.exists()) {
				try {
					nomedia.createNewFile();
				} catch (final IOException exception) {
					exception.printStackTrace();
				}
			}

			try {
				filePath = filePath + System.currentTimeMillis() + ".jpg";
				final FileOutputStream outStream = new FileOutputStream(filePath);
				Bitmaps.getBitmap(path, true).compress(Bitmap.CompressFormat.JPEG, 90, outStream);
				outStream.flush();
				outStream.close();
				path = filePath;
			} catch (final IOException exception) {
				exception.printStackTrace();
			}

			result(path);
		} else {
			final Intent intent2 = new Intent();
			activity.setResult(Activity.RESULT_CANCELED, intent2);
			activity.finish();
		}
	}

	public final void result(final String path) {
		final Intent intent2 = new Intent();
		intent2.putExtra(IMAGE_KEY, path);
		activity.setResult(Activity.RESULT_OK, intent2);
		activity.finish();
	}
}
