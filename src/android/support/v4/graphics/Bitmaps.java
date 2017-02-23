package android.support.v4.graphics;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.support.v4.graphics.bitmapfun.ImageResizer;
import android.view.View;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class Bitmaps {
	public static final int[] getBitmapSize(final String path) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		return new int[] { options.outHeight, options.outWidth };
	}

	public static final int getBitmapOrientation(final String path) {
		int result = 0;
		try {
			ExifInterface exif;
			exif = new ExifInterface(path);
			final int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_270:
				result = 270;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				result = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_90:
				result = 90;
				break;
			default:
				result = 0;
				break;
			}
		} catch (final IOException exception) {
		}
		return result;
	}

	public static final Bitmap getBitmap(final String path, final boolean isExif) {
		final int[] size = getBitmapSize(path);
		return ImageResizer.decodeSampledBitmapFromFile(path, size[0], size[1], null);
	}

	public static final Bitmap getBitmap(final String path, final int size, final boolean isExif) {
		return ImageResizer.decodeSampledBitmapFromFile(path, size, size, null);
	}

	public static final Bitmap getBitmap(final String path, final int width, final int height, final boolean isExif) {
		return ImageResizer.decodeSampledBitmapFromFile(path, width, height, null);
	}

	// public static final Bitmap getBitmap(final String path, final boolean
	// isExif) {
	// Bitmap result = null;
	// final BitmapFactory.Options options = new BitmapFactory.Options();
	// options.inJustDecodeBounds = true;
	//
	// try {
	// BitmapFactory.decodeStream(new FileInputStream(new File(path)),
	// null, options);
	//
	// options.inJustDecodeBounds = false;
	// options.inPurgeable = true;
	// options.inInputShareable = true;
	// options.inPreferredConfig = Bitmap.Config.RGB_565;
	//
	// if (android.os.Build.VERSION.SDK_INT <
	// android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
	// try {
	// BitmapFactory.Options.class.getField("inNativeAlloc")
	// .setBoolean(options, true);
	// } catch (final IllegalArgumentException exception) {
	// } catch (final IllegalAccessException exception) {
	// } catch (final NoSuchFieldException exception) {
	// }
	// }
	//
	// final Bitmap bitmap = BitmapFactory.decodeStream(
	// new FileInputStream(new File(path)), null, options);
	//
	// if (isExif) {
	// final Matrix matrix = new Matrix();
	// matrix.postRotate(getBitmapOrientation(path));
	// final Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
	// bitmap.getWidth(), bitmap.getHeight(), matrix, false);
	//
	// final SoftReference<Bitmap> softBitmap = new SoftReference<Bitmap>(
	// rotatedBitmap);
	// result = softBitmap.get();
	// } else {
	// final SoftReference<Bitmap> softBitmap = new SoftReference<Bitmap>(
	// bitmap);
	// result = softBitmap.get();
	// }
	// } catch (final FileNotFoundException exception) {
	// }
	// return result;
	// }

	// public static final Bitmap getBitmap(final String path, final double
	// size,
	// final boolean isExif) {
	// return getBitmap(path, size, size, isExif);
	// }
	//
	// public static final Bitmap getBitmap(final String path, final double
	// width,
	// final double heigth, final boolean isExif) {
	// Bitmap result = null;
	// final BitmapFactory.Options options = new BitmapFactory.Options();
	// options.inJustDecodeBounds = true;
	//
	// try {
	// BitmapFactory.decodeStream(new FileInputStream(new File(path)),
	// null, options);
	//
	// final int width_tmp = options.outWidth;
	// final int height_tmp = options.outHeight;
	// final int minSideLength = Math.min(width_tmp, height_tmp);
	// try {
	// options.inSampleSize = computeSampleSize(options,
	// minSideLength, width * heigth);
	// } catch (final IOException exception) {
	// }
	//
	// options.inJustDecodeBounds = false;
	// options.inPurgeable = true;
	// options.inInputShareable = true;
	// options.inPreferredConfig = Bitmap.Config.RGB_565;
	//
	// if (android.os.Build.VERSION.SDK_INT <
	// android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
	// try {
	// BitmapFactory.Options.class.getField("inNativeAlloc")
	// .setBoolean(options, true);
	// } catch (final IllegalArgumentException exception) {
	// } catch (final IllegalAccessException exception) {
	// } catch (final NoSuchFieldException exception) {
	// }
	// }
	//
	// final Bitmap bitmap = BitmapFactory.decodeStream(
	// new FileInputStream(new File(path)), null, options);
	//
	// if (isExif) {
	// final Matrix matrix = new Matrix();
	// matrix.postRotate(getBitmapOrientation(path));
	// final Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
	// bitmap.getWidth(), bitmap.getHeight(), matrix, false);
	//
	// final SoftReference<Bitmap> softBitmap = new SoftReference<Bitmap>(
	// rotatedBitmap);
	// result = softBitmap.get();
	// } else {
	// final SoftReference<Bitmap> softBitmap = new SoftReference<Bitmap>(
	// bitmap);
	// result = softBitmap.get();
	// }
	// } catch (final FileNotFoundException exception) {
	// }
	// return result;
	// }

	// public static final Bitmap getBitmap(final Context context,
	// final int resid, final double size) {
	// return getBitmap(context, resid, size, size);
	// }
	//
	// public static final Bitmap getBitmap(final Context context,
	// final int resid, final double width, final double heigth) {
	// Bitmap result = null;
	// final BitmapFactory.Options options = new BitmapFactory.Options();
	// options.inJustDecodeBounds = true;
	//
	// BitmapFactory.decodeResource(context.getResources(), resid, options);
	//
	// final int width_tmp = options.outWidth;
	// final int height_tmp = options.outHeight;
	// final int minSideLength = Math.min(width_tmp, height_tmp);
	// try {
	// options.inSampleSize = computeSampleSize(options, minSideLength,
	// width * heigth);
	// } catch (final IOException exception) {
	// }
	//
	// options.inJustDecodeBounds = false;
	// options.inPurgeable = true;
	// options.inInputShareable = true;
	// options.inPreferredConfig = Bitmap.Config.RGB_565;
	//
	// if (android.os.Build.VERSION.SDK_INT <
	// android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
	// try {
	// BitmapFactory.Options.class.getField("inNativeAlloc")
	// .setBoolean(options, true);
	// } catch (final IllegalArgumentException exception) {
	// } catch (final IllegalAccessException exception) {
	// } catch (final NoSuchFieldException exception) {
	// }
	// }
	//
	// final Bitmap bitmap = BitmapFactory.decodeResource(
	// context.getResources(), resid, options);
	//
	// final SoftReference<Bitmap> softBitmap = new SoftReference<Bitmap>(
	// bitmap);
	// result = softBitmap.get();
	// return result;
	// }

	public static final Bitmap replaceColor(final Bitmap bitmap, final int fromColor, final int toColor) {
		Bitmap result = null;
		if (null != bitmap) {
			final int width = bitmap.getWidth();
			final int height = bitmap.getHeight();
			final int[] pixels = new int[width * height];
			bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

			for (int x = 0; x < pixels.length; ++x) {
				pixels[x] = pixels[x] == fromColor ? toColor : pixels[x];
			}
			result = Bitmap.createBitmap(width, height, bitmap.getConfig());
			result.setPixels(pixels, 0, width, 0, 0, width, height);
		}
		return result;
	}

	public static final Bitmap getBitmap(final View view) {
		final Bitmap bmImg = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_4444);

		final Canvas canvas = new Canvas(bmImg);
		view.draw(canvas);

		return Bitmap.createScaledBitmap(bmImg, bmImg.getWidth(), bmImg.getHeight(), true);
	}

	public static final Bitmap getBitmap(final View view, final int outputWidth, final int outputHeight,
			final int quality) throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		view.setDrawingCacheEnabled(true);
		view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
		view.buildDrawingCache(true);
		final int width = view.getDrawingCache().getWidth();
		final int height = view.getDrawingCache().getHeight();
		final Matrix mat = new Matrix();
		mat.postScale(outputWidth / width, outputHeight / height);
		final Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, width, height, mat, false);
		bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos);
		bitmap.recycle();
		final byte[] bytes = baos.toByteArray();
		baos.close();
		return getBitmap(bytes);
	}

	public static final Bitmap getBitmap(final byte... bytes) {
		Bitmap result = null;
		if (0 != bytes.length) {
			result = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		}
		return result;
	}

	public static final Bitmap getBitmap(final Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		final Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
				Config.RGB_565);
		final Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

	private static final int INITIALSIZELIMIT = 8;

	public static final int computeSampleSize(final BitmapFactory.Options options, final int minSideLength,
			final double maxNumOfPixels) throws IOException {
		final int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
		int roundedSize;
		if (initialSize <= INITIALSIZELIMIT) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / INITIALSIZELIMIT * INITIALSIZELIMIT;
		}
		return roundedSize;
	}

	public static final int computeInitialSampleSize(final BitmapFactory.Options options, final int minSideLength,
			final double maxNumOfPixels) throws IOException {
		int result = 1;
		final double width = options.outWidth;
		final double height = options.outHeight;
		final int lowerBound = maxNumOfPixels == -1 ? 1 : (int) Math.ceil(Math.sqrt(width * height / maxNumOfPixels));
		final int upperBound = minSideLength == -1 ? 128
				: (int) Math.min(Math.floor(width / minSideLength), Math.floor(height / minSideLength));
		if (upperBound < lowerBound) {
			result = lowerBound;
		}
		if (maxNumOfPixels == -1 && minSideLength == -1) {
			result = 1;
		} else if (minSideLength == -1) {
			result = lowerBound;
		} else {
			result = upperBound;
		}
		return result;
	}
}
