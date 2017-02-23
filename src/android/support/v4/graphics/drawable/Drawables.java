package android.support.v4.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.Bitmaps;
import android.support.v4.widget.TypedValues;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class Drawables {
	public static final Drawable get(final Context baseContext, final int resid, final int size) {
		return get(baseContext, resid, size, size);
	}

	public static final Drawable get(final Context baseContext, final int resid, final int width, final int heigth) {
		Drawable result = null;
		try {
			result = new BitmapDrawable(baseContext.getResources(),
					Bitmap.createScaledBitmap(Bitmaps.getBitmap(get(baseContext, resid)), width, heigth, true));
		} catch (final Exception exception) {
			exception.printStackTrace();
			result = new BitmapDrawable(baseContext.getResources(), Bitmap.createScaledBitmap(
					Bitmaps.getBitmap(baseContext.getResources().getDrawable(resid)), width, heigth, true));
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	public static final Drawable get(final Context baseContext, final int resId) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inPreferredConfig = Bitmap.Config.RGB_565;

		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			try {
				BitmapFactory.Options.class.getField("inNativeAlloc").setBoolean(options, true);
			} catch (final IllegalArgumentException exception) {
			} catch (final IllegalAccessException exception) {
			} catch (final NoSuchFieldException exception) {
			}
		}

		return new BitmapDrawable(BitmapFactory.decodeResource(baseContext.getResources(), resId, options));
	}

	public static final int dp2px(final int dipValue) {
		return (int) TypedValues.applyDimension(TypedValues.COMPLEX_UNIT_DIP, dipValue,
				Resources.getSystem().getDisplayMetrics());
	}

}
