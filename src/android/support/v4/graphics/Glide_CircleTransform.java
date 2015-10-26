package android.support.v4.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class Glide_CircleTransform extends BitmapTransformation {
	public Glide_CircleTransform(final Context context) {
		super(context);
	}

	@Override
	public Bitmap transform(final BitmapPool pool, final Bitmap toTransform,
			final int width, final int height) {
		return circleCrop(pool, toTransform);
	}

	private Bitmap circleCrop(final BitmapPool pool, final Bitmap source) {
		if (source == null) {
			return null;
		}

		final int size = Math.min(source.getWidth(), source.getHeight());
		final int width = (source.getWidth() - size) / 2;
		final int height = (source.getHeight() - size) / 2;

		final Bitmap squared = Bitmap.createBitmap(source, width, height, size,
				size);

		Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
		if (result == null) {
			result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
		}

		final Canvas canvas = new Canvas(result);
		final Paint paint = new Paint();
		paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP,
				BitmapShader.TileMode.CLAMP));
		paint.setAntiAlias(true);
		final float round = size / 2f;
		canvas.drawCircle(round, round, round, paint);
		return result;
	}

	@Override
	public String getId() {
		return getClass().getName();
	}
}