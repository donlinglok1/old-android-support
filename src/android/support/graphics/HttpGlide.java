package android.support.graphics;

import java.io.File;

import com.bumptech.glide.Glide;

import android.app.Activity;
import android.support.net.http.HttpDownloadTask;
import android.support.net.http.HttpDownloadTask.HttpDownloadTaskCallback;
import android.widget.ImageView;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class HttpGlide {
	public final void load(final Activity context, final String path, final ImageView imageView) {
		final String file = path.substring(path.lastIndexOf('/') + 1, path.length());
		if (new File(file).exists()) {
			try {
				Glide.with(context).load(new File(file)).centerCrop().into(imageView);
			} catch (final Exception exception) {
			}
		} else {
			new HttpDownloadTask(context, new HttpDownloadTaskCallback() {
				@Override
				public void onSuccess(final File outputFile) {
					context.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								Glide.with(context).load(outputFile).centerCrop().into(imageView);
							} catch (final Exception exception) {
							}
						}
					});
				}

				@Override
				public void onFail(final Exception exception) {
					context.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								Glide.with(context).load(path).centerCrop().into(imageView);
							} catch (final Exception exception) {
								exception.printStackTrace();
							}
						}
					});
				}
			}, path, file, false).execute();
		}
	}
}
