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
	public static Bitmap drawable2Bitmap(final Drawable drawable) {
		Bitmap result = null;
		if (drawable instanceof BitmapDrawable) {
			result = ((BitmapDrawable) drawable).getBitmap();
		} else {
			result = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight(), Config.RGB_565);
			final Canvas canvas = new Canvas(result);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);
		}
		return result;
	}

	public static Drawable getDrawable(final Context context, final int resid,
			final int size) {
		return getDrawable(context, resid, size, size);
	}

	public static Drawable getDrawable(final Context context, final int resid,
			final int width, final int heigth) {
		Drawable result = null;
		try {
			result = new BitmapDrawable(context.getResources(),
					Bitmap.createScaledBitmap(
							drawable2Bitmap(getDrawable(context, resid)),
							width, heigth, true));
		} catch (final Exception exception) {
			result = new BitmapDrawable(context.getResources(),
					Bitmap.createScaledBitmap(drawable2Bitmap(context
							.getResources().getDrawable(resid)), width, heigth,
							true));
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	public static Drawable getDrawable(final Context context, final int resId) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inPreferredConfig = Bitmap.Config.RGB_565;

		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			try {
				BitmapFactory.Options.class.getField("inNativeAlloc")
						.setBoolean(options, true);
			} catch (final Exception exception) {
				// exception.printStackTrace();
			}
		}

		return new BitmapDrawable(BitmapFactory.decodeResource(
				context.getResources(), resId, options));
	}

	public static int dp2px(final int dipValue) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dipValue, Resources.getSystem().getDisplayMetrics());
	}

}
