package android.support.v4.net.http;

import net.minidev.json.JSONObject;
import android.support.v4.security.Strings;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class HttpPostsThread extends Thread {
	private final transient JSONObject dateObject;
	private final transient String url;
	private final transient HttpPostsThreadCallback callback;
	private final transient boolean isGzip;

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

	@Override
	public void run() {
		try {
			if (isGzip) {
				callback.onReturn(HttpPosts.postJson(url,
						Strings.valueOf(dateObject)));
			} else {
				callback.onReturn(HttpPosts.postJsonNoGzip(url,
						Strings.valueOf(dateObject)));
			}
		} catch (final Exception exception) {
			// exception.printStackTrace();
		}
	}
}