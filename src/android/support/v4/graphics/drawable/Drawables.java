package android.support.v4.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.TypedValue;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class Drawables {
	public static Drawable getDrawable(final Context baseContext,
			final int resid, final int size) {
		return getDrawable(baseContext, resid, size, size);
	}

	public static Drawable getDrawable(final Context baseContext,
			final int resid, final int width, final int heigth) {
		Drawable result = null;
		try {
			result = new BitmapDrawable(baseContext.getResources(),
					Bitmap.createScaledBitmap(
							drawable2Bitmap(getDrawable(baseContext, resid)),
							width, heigth, true));
		} catch (final Exception exception) {
			result = new BitmapDrawable(baseContext.getResources(),
					Bitmap.createScaledBitmap(drawable2Bitmap(baseContext
							.getResources().getDrawable(resid)), width, heigth,
							true));
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	public static Drawable getDrawable(final Context baseContext,
			final int resId) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inPreferredConfig = Bitmap.Config.RGB_565;

		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			try {
				BitmapFactory.Options.class.getField("inNativeAlloc")
						.setBoolean(options, true);
			} catch (final Exception exception) {
				// Strings.exceptionToJSONObject(exception);
			}
		}

		return new BitmapDrawable(BitmapFactory.decodeResource(
				baseContext.getResources(), resId, options));
	}

	public static Bitmap drawable2Bitmap(final Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		final Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(), Config.RGB_565);
		final Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

	public static int dp2px(final int dipValue) {
		final int result = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, dipValue, Resources.getSystem()
						.getDisplayMetrics());
		return result;
	}

}
