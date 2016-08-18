package android.n;

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
    public final transient Activity activity;
    public final transient Context appContext;

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

    public void setVClick(final View view, final Callback callback) {
	view.setOnTouchListener(new VibrateListener(appContext, callback));
    }

    public View setIconPadding(final TextView view, final int dp) {
	view.setCompoundDrawablePadding(dp);
	return view;
    }

    public Drawable getImage(final int resId, final int dp) {
	return getImage(resId, dp, dp);
    }

    public Drawable getImage(final int resId, final int dp, final int dp2) {
	return Drawables.get(appContext, resId, Drawables.dp2px(dp), Drawables.dp2px(dp2));
    }

    public Drawable getImageByPx(final int resId, final int px) {
	return getImageByPx(resId, px, px);
    }

    public Drawable getImageByPx(final int resId, final int px, final int px2) {
	return Drawables.get(appContext, resId, px, px2);
    }

    public int getRColor(final int resId) {
	return appContext.getResources().getColor(resId);
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
