package android.support.v4.n;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.Drawables;
import android.support.v4.view.VibrateListener;
import android.support.v4.view.VibrateListener.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class NAdapter extends BaseAdapter {
    public transient final Activity activity;
    public transient final Context appContext;

    public NAdapter(final Activity activity) {
	super();
	this.activity = activity;
	appContext = activity.getApplicationContext();
    }

    private int resid;

    public void setContentView(final int resid) {
	this.resid = resid;
    }

    public View getContentView(final ViewGroup parent) {
	final View rView = LayoutInflater.from(appContext).inflate(resid, parent, false);
	rView.setOnTouchListener(new VibrateListener(appContext, new Callback() {
	    @Override
	    public void onClick(final View view) {
	    }
	}));

	return rView;
    }

    public String getString(final int resId) {
	return appContext.getString(resId);
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

    public void setVClick(final View view, final Callback callback) {
	view.setOnTouchListener(new VibrateListener(appContext, callback));
    }

    public Drawable getImage(final int resId, final int size) {
	return Drawables.get(appContext, resId, size);
    }

    @Override
    public int getCount() {
	return 0;
    }

    @Override
    public Object getItem(final int position) {
	return null;
    }

    @Override
    public long getItemId(final int position) {
	return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
	return null;
    }
}
