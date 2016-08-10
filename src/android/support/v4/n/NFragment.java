package android.support.v4.n;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.Drawables;
import android.support.v4.view.VibrateListener;
import android.support.v4.view.VibrateListener.Callback;
import android.view.View;
import android.widget.TextView;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
/**
 *
 * @author kennetht
 *
 */
public class NFragment extends Fragment {
    public transient FragmentActivity activity;
    public transient Context appContext;

    @Override
    public void onAttach(final Activity activity) {
	super.onAttach(activity);

	this.activity = (FragmentActivity) activity;
	appContext = activity.getApplicationContext();
    }

    public void setVClick(final View view, final Callback callback) {
	view.setOnTouchListener(new VibrateListener(appContext, callback));
    }

    public View setIcon(final TextView view, final Drawable left, final Drawable top, final Drawable right,
	    final Drawable bottom) {
	view.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
	return view;
    }

    public View setIconPadding(final TextView view, final int dp) {
	view.setCompoundDrawablePadding(Drawables.dp2px(dp));
	return view;
    }

    public Drawable getImage(final int resId, final int db) {
	return getImage(resId, Drawables.dp2px(db), Drawables.dp2px(db));
    }

    public Drawable getImage(final int resId, final int db, final int db2) {
	return Drawables.get(appContext, resId, Drawables.dp2px(db), Drawables.dp2px(db2));
    }

    /**
     *
     * @author kennetht
     *
     */
    public interface UICallback {
	void run();
    }

    public void onUI(final UICallback callback) {
	activity.runOnUiThread(new Runnable() {
	    @Override
	    public void run() {
		callback.run();
	    }
	});
    }
}