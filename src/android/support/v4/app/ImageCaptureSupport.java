package android.support.v4.app;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.lang.NString;
import android.support.v4.util.Tools;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
/**
 * give the signal, say no more and take my image path.
 * 
 * @author kennetht
 *
 */
public class ImageCaptureSupport extends Activity {
    private transient Activity activiry;

    public static final String IMAGE_PATH = "image_path";
    private static final String TEMP_PATH = "ImageFilePath";
    private static final int TEMP_CODE = 6260;
    private transient String tempFilePath;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	activiry = this;

	if (savedInstanceState == null) {
	    String filePath = "/Android/data/" + activiry.getPackageName() + "/temp/ImageCaptureSupport/";
	    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
		filePath = NString.parse(Environment.getExternalStorageDirectory().getAbsolutePath(), filePath);
	    } else {
		filePath = NString.parse(activiry.getCacheDir().getAbsolutePath() + filePath);
	    }
	    final File file = new File(filePath);
	    if (!file.exists()) {
		file.mkdirs();
	    }
	    final File nomedia = new File(filePath + "/.nomedia");
	    if (!nomedia.exists()) {
		try {
		    nomedia.createNewFile();
		} catch (final IOException exception) {
		    Tools.exceptionToJSONObject(exception);
		}
	    }

	    tempFilePath = filePath + System.currentTimeMillis() + ".jpg";

	    final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(tempFilePath)));
	    startActivityForResult(intent, TEMP_CODE);
	} else {
	    tempFilePath = savedInstanceState.getString(TEMP_PATH);

	    final File mFile = new File(TEMP_PATH);
	    if (mFile.exists()) {
		final Intent intent = new Intent();
		intent.putExtra(IMAGE_PATH, tempFilePath);
		setResult(Activity.RESULT_OK, intent);
		finish();
	    }
	}
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
	if (TEMP_CODE == requestCode && resultCode == Activity.RESULT_OK) {
	    final Intent intent2 = new Intent();
	    intent2.putExtra(IMAGE_PATH, tempFilePath);
	    activiry.setResult(Activity.RESULT_OK, intent2);
	}
	activiry.finish();
    }

    @Override
    public void onSaveInstanceState(final Bundle bundle) {
	bundle.putString(TEMP_PATH, tempFilePath);
	super.onSaveInstanceState(bundle);
    }
}
