package android.support.v4.graphics;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.support.v4.util.AsyncTask;
import android.view.View;
import android.widget.ImageView;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class ImageLoadTask extends AsyncTask<String, Void, Bitmap> {
	private transient final WeakReference<ImageView> imageViewReference;
	private transient final double width, heigth;
	private transient final boolean isExif;

	public ImageLoadTask(final ImageView imageView, final double width,
			final double heigth, final boolean isExif) {
		super();
		imageViewReference = new WeakReference<ImageView>(imageView);
		this.width = width;
		this.heigth = heigth;
		this.isExif = isExif;
	}

	@Override
	protected Bitmap doInBackground(final String... params) {
		return Bitmaps.getBitmap(params[0], width, heigth, isExif);
	}

	@Override
	protected void onPostExecute(final Bitmap bitmap) {
		super.onPostExecute(bitmap);
		final ImageView imageView = imageViewReference.get();
		if (null == bitmap) {
			if (null != imageView) {
				imageView.setVisibility(View.GONE);
			}
		} else {
			if (null != imageView) {
				imageView.setImageBitmap(bitmap);
			}
		}
	}
}