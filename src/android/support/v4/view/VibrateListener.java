package android.support.v4.view;

import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.content.SharedPreferences.Preferences;
import android.view.MotionEvent;
import android.view.View;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class VibrateListener implements View.OnTouchListener {
    public static final String IS_VIBRATE = "isVibrate";
    public static final int VIBRATE_TIME = 50;

    private final transient Context baseContext;

    private final transient Callback callback;

    public interface Callback {
	void onClick(View view);
    }

    public VibrateListener(final Context baseContext, final Callback callback) {
	this.baseContext = baseContext;
	this.callback = callback;
    }

    private final transient Handler handler = new Handler();
    private final transient Runnable vibrateRunnable = new Runnable() {
	@Override
	public void run() {
	    if (Preferences.get(baseContext, IS_VIBRATE, 1) == 0) {
		((Vibrator) baseContext.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(VIBRATE_TIME);
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
	    handler.postDelayed(vibrateRunnable, 1000 / 4);
	    break;
	case MotionEvent.ACTION_MOVE:
	    if ((int) motionEvent.getX() > view.getRight() - view.getLeft() || (int) motionEvent.getX() < 0
		    || (int) motionEvent.getY() > view.getBottom() - view.getTop() || (int) motionEvent.getY() < 0) {
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
		callback.onClick(view);
	    }
	    break;
	default:
	    break;
	}
	return false;
    }
}
