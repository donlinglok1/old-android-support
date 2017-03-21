package android.support.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.content.SharedPreferences.Preferences;
import android.support.graphics.Bitmaps;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

/*
 * Zoom image to fullscreen
 */
public class ImageZoomView extends RelativeLayout {
	private final transient  Context baseContext;

	@SuppressWarnings("deprecation")
	public ImageZoomView(final Context context, final String path) {
		super(context);
		baseContext = context.getApplicationContext();

		final ImageView imageView = new ImageView(context);
		imageView.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT));
		imageView.setClickable(true);
		imageView.setScaleType(ScaleType.MATRIX);
		imageView.setBackgroundColor(Color.BLACK);
		imageView.setOnTouchListener(imageTouchListener);
		imageView.setImageBitmap(Bitmaps.getBitmap(path, Preferences.get(baseContext, "deviceHeight", 1920),
				Preferences.get(baseContext, "deviceWidth", 1080), true));

		addView(imageView);

		setViewImage(imageView);
	}

	private static final float SPEED_ZOOM = 30f;
	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;

	private transient Matrix matrix = new Matrix();
	private transient float oldDist = 1f;
	// private transient float maxZoom;
	// private transient float minZoom;
	private final transient  float matrixValues[] = new float[9];

	private transient int mode = NONE;
	private transient int lastX;
	private transient int lastY;

	private final void setViewImage(final View view) {
		// get the frame width and height
		final float frameWidth = Preferences.get(baseContext, "deviceWidth", 1080);
		final float frameHeight = Preferences.get(baseContext, "deviceHeight", 1920);

		// get the image width and height
		final float originalImageWidth = ((ImageView) view).getDrawable().getIntrinsicWidth();
		final float originalImageHeight = ((ImageView) view).getDrawable().getIntrinsicHeight();

		// setup the limit for zoom in and out
		// maxZoom = 1.0f * view.getResources().getDisplayMetrics().density;

		float usedScaleFactor = 1;

		if (originalImageWidth == frameWidth || originalImageHeight == frameHeight) {
			// minZoom = frameWidth / originalImageWidth;
			usedScaleFactor = frameWidth / originalImageWidth;
		} else {
			// If frame is bigger than image
			// => Crop it, keep aspect ratio and position it at the bottom and
			// center horizontally
			// minZoom = Math.max(frameWidth / originalImageWidth, frameHeight /
			// originalImageHeight);

			final float fitHorizontallyScaleFactor = frameWidth / originalImageWidth;
			final float fitVerticallyScaleFactor = frameHeight / originalImageHeight;

			usedScaleFactor = Math.max(fitHorizontallyScaleFactor, fitVerticallyScaleFactor);
		}

		// Obtain the new image width and height
		final float newImageWidth = originalImageWidth * usedScaleFactor;
		final float newImageHeight = originalImageHeight * usedScaleFactor;

		// Scale up the image by matrix
		final Matrix matrix = ((ImageView) view).getImageMatrix();

		// Replaces the old matrix completly
		matrix.setScale(usedScaleFactor, usedScaleFactor, 0, 0);
		matrix.postTranslate((frameWidth - newImageWidth) / 2, frameHeight - newImageHeight);

		((ImageView) view).setImageMatrix(matrix);
	}

	private final transient  PointF start = new PointF();
	private final transient  PointF mid = new PointF();

	private final transient  OnTouchListener imageTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(final View view, final MotionEvent event) {
			if (((ImageView) view).getDrawable() != null) {
				matrix = ((ImageView) view).getImageMatrix();
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					doActionDown(event);
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					doPointerDown(event);
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
					doPointerUp();
					view.performClick();
					break;
				case MotionEvent.ACTION_MOVE:
					if (mode == DRAG) {
						doDrag(view, event);
					} else if (mode == ZOOM) {
						// doZoom(view, event);
					}
					break;
				default:
					break;
				}
				((ImageView) view).setImageMatrix(matrix);
				view.invalidate();
			}
			return true;
		}

		// private void doZoom(final View view, final MotionEvent event) {
		// final float newDist = spacing(event);
		// if (newDist > SPEED_ZOOM) {
		// float scale = newDist / oldDist;
		//
		// matrix.getValues(matrixValues);
		// final float currentScale = matrixValues[Matrix.MSCALE_X];
		//
		// if (scale * currentScale > maxZoom) {
		// scale = maxZoom / currentScale;
		// } else if (scale * currentScale < minZoom) {
		// scale = minZoom / currentScale;
		// }
		// matrix.postScale(scale, scale, mid.x, mid.y);
		// adjustPan(view);
		// }
		// }

		private final void doDrag(final View view, final MotionEvent event) {
			final int nowX = (int) event.getRawX();
			final int nowY = (int) event.getRawY();

			final int moveX = nowX - lastX;
			final int moveY = nowY - lastY;

			matrix.postTranslate(moveX, moveY);

			adjustPan(view);

			lastX = (int) event.getRawX();
			lastY = (int) event.getRawY();
		}

		private final void doPointerUp() {
			mode = NONE;
		}

		private final void doPointerDown(final MotionEvent event) {
			oldDist = spacing(event);
			if (oldDist > SPEED_ZOOM) {
				midPoint(mid, event);
				mode = ZOOM;
			}
		}

		private final void doActionDown(final MotionEvent event) {
			lastX = (int) event.getRawX();
			lastY = (int) event.getRawY();

			start.set(event.getX(), event.getY());
			mode = DRAG;
		}
	};

	private final void adjustPan(final View view) {
		matrix.getValues(matrixValues);

		final float currentY = matrixValues[Matrix.MTRANS_Y];
		final float currentX = matrixValues[Matrix.MTRANS_X];
		final float currentScale = matrixValues[Matrix.MSCALE_X];
		final float contentWidth = ((ImageView) view).getDrawable().getIntrinsicWidth();
		final float contentHeight = ((ImageView) view).getDrawable().getIntrinsicHeight();

		final float currentHeight = contentHeight * currentScale;
		final float currentWidth = contentWidth * currentScale;

		final float newX = currentX;
		final float newY = currentY;

		final RectF drawingRect = new RectF(newX, newY, newX + currentWidth, newY + currentHeight);

		final float diffUp = Math.min(view.getBottom() - drawingRect.bottom, view.getTop() - drawingRect.top);
		final float diffDown = Math.max(view.getBottom() - drawingRect.bottom, view.getTop() - drawingRect.top);
		final float diffLeft = Math.min(view.getLeft() - drawingRect.left, view.getRight() - drawingRect.right);
		final float diffRight = Math.max(view.getLeft() - drawingRect.left, view.getRight() - drawingRect.right);

		float xxxx = 0;
		float yyyy = 0;
		if (diffUp > 0) {
			yyyy += diffUp;
		}
		if (diffDown < 0) {
			yyyy += diffDown;
		}
		if (diffLeft > 0) {
			xxxx += diffLeft;
		}
		if (diffRight < 0) {
			xxxx += diffRight;
		}
		if (currentWidth < view.getWidth()) {
			xxxx = -currentX + (view.getWidth() - currentWidth) / 2;
		}
		if (currentHeight < view.getHeight()) {
			yyyy = -currentY + (view.getHeight() - currentHeight) / 2;
		}
		matrix.postTranslate(xxxx, yyyy);

	}

	private final void midPoint(final PointF point, final MotionEvent event) {
		final float xxxx = event.getX(0) + event.getX(1);
		final float yyyy = event.getY(0) + event.getY(1);
		point.set(xxxx / 2, yyyy / 2);
	}

	private final float spacing(final MotionEvent event) {
		final float xxxx = event.getX(0) - event.getX(1);
		final float yyyy = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(xxxx * xxxx + yyyy * yyyy);
	}

}
