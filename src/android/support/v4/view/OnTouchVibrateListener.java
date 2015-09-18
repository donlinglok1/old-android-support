package android.support.v4.view;

import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.content.SharedPreferences.Preferences;
import android.view.MotionEvent;
import android.view.View;

public class OnTouchVibrateListener implements View.OnTouchListener {
	private final transient Context baseContext;

	private final transient OnTouchVibrateListenerCallBack callBack;

	public interface OnTouchVibrateListenerCallBack {
		void onClick(View view);
	}

	public OnTouchVibrateListener(final Context baseContext,
			final OnTouchVibrateListenerCallBack callBack) {
		this.baseContext = baseContext;
		this.callBack = callBack;
	}

	public final static String IS_VIBRATE = "isVibrate";

	private final transient Handler handler = new Handler();
	private final transient Runnable vibrateRunnable = new Runnable() {
		@Override
		public final void run() {
			if (Preferences.getInt(baseContext, IS_VIBRATE, 1) == 0) {
				((Vibrator) baseContext
						.getSystemService(Context.VIBRATOR_SERVICE))
						.vibrate(50);
			}
			toofast = false;
		}
	};
	private transient boolean toofast = true;

	@Override
	public final boolean onTouch(final View view, final MotionEvent motionEvent) {
		switch (motionEvent.getAction()) {
		case MotionEvent.ACTION_DOWN:
			toofast = true;
			handler.removeCallbacks(vibrateRunnable);
			handler.postDelayed(vibrateRunnable, 250);
			break;
		case MotionEvent.ACTION_MOVE:
			if ((int) motionEvent.getX() > view.getRight() - view.getLeft()
					|| (int) motionEvent.getX() < 0
					|| (int) motionEvent.getY() > view.getBottom()
							- view.getTop() || (int) motionEvent.getY() < 0) {
				handler.removeCallbacks(vibrateRunnable);
				toofast = false;
			} else {
				toofast = true;
			}
			break;
		case MotionEvent.ACTION_UP:
			handler.removeCallbacks(vibrateRunnable);
			if (toofast) {
				handler.post(vibrateRunnable);
				toofast = false;
				view.performClick();
				callBack.onClick(view);
			}
			break;
		default:
			break;
		}
		return false;
	}
}
