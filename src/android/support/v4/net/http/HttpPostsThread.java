package android.support.v4.net.http;

import java.util.List;

import net.minidev.json.JSONObject;

import org.apache.http.NameValuePair;

import android.support.v4.lang.Strings;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class HttpPostsThread extends Thread {
	private transient JSONObject dateObject;
	private transient List<NameValuePair> dateObject2;
	private transient final String url;
	private transient final HttpPostsThreadCallback callback;
	private transient final boolean isGzip;

	public interface HttpPostsThreadCallback {
		void onReturn(String result);
	}

	public HttpPostsThread(final HttpPostsThreadCallback callback,
			final String url, final JSONObject dateObject, final boolean isGzip) {
		super();
		this.callback = callback;
		this.url = url;
		this.dateObject = dateObject;
		this.isGzip = isGzip;
	}

	public HttpPostsThread(final HttpPostsThreadCallback callback,
			final String url, final List<NameValuePair> dateObject,
			final boolean isGzip) {
		super();
		this.callback = callback;
		this.url = url;
		dateObject2 = dateObject;
		this.isGzip = isGzip;
	}

	@Override
	public void run() {
		if (null == dateObject) {
			callback.onReturn(HttpPosts.postNameValuePair(url, dateObject2,
					isGzip));
		} else {
			callback.onReturn(HttpPosts.postBody(url,
					Strings.valueOf(dateObject), isGzip));
		}
	}
}