package android.support.v4.view;

import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.content.SharedPreferences.Preferences;
import android.view.MotionEvent;
import android.view.View;

public class OnTouchVibrateListener implements View.OnTouchListener {
	private transient final Context baseContext;

	private transient final OnTouchVibrateListenerCallBack callBack;

	public interface OnTouchVibrateListenerCallBack {
		void onClick(View view);
	}

	public OnTouchVibrateListener(final Context baseContext,
			final OnTouchVibrateListenerCallBack callBack) {
		this.baseContext = baseContext;
		this.callBack = callBack;
	}

	public final static String IS_VIBRATE = "isVibrate";

	private transient final Handler handler = new Handler();
	private transient final Runnable vibrateRunnable = new Runnable() {
		@Override
		public void run() {
			if (Preferences.getInt(baseContext, IS_VIBRATE, 0) == 0) {
				((Vibrator) baseContext
						.getSystemService(Context.VIBRATOR_SERVICE))
						.vibrate(50);
			}
			toofast = false;
		}
	};
	private transient boolean toofast = true;

	@Override
	public boolean onTouch(final View view, final MotionEvent motionEvent) {
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
