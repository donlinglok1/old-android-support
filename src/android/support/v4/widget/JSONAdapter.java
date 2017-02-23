package android.support.v4.widget;

import android.app.Activity;
import android.n.NAdapter;
import android.support.v4.app.Set;
import android.support.v4.view.VibrateListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public abstract class JSONAdapter extends NAdapter {
	public final transient JSONArray listArray;
	public final transient Set set;

	public JSONAdapter(final Activity activity, final JSONArray listArray) {
		super(activity);
		this.listArray = listArray;
		this.set = new Set(activity);
	}

	@Override
	public final View getContentView(final int resid, final ViewGroup parent) {
		final View rView = LayoutInflater.from(appContext).inflate(resid, parent, false);
		rView.setOnTouchListener(new VibrateListener(appContext, new VibrateListener.Callback() {
			@Override
			public void onClick(final View view) {
			}
		}));

		return rView;
	}

	@Override
	public int getCount() {
		return listArray.size();
	}

	@Override
	public Object getItem(final int position) {
		return listArray.get(position);
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		return getView(position, convertView, parent, (JSONObject) getItem(position));
	}

	public abstract View getView(final int position, final View convertView, final ViewGroup parent,
			final JSONObject jObj);
}
