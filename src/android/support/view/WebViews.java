package android.support.view;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class WebViews extends WebView {
	public WebViews(final Context context) {
		super(context);

		setBackgroundColor(0);
		requestFocus();
		setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(final View view, final MotionEvent event) {
				if (event.getAction() == 0 || event.getAction() == 1) {
					if (!view.hasFocus()) {
						view.requestFocus();
					}
					view.performClick();
				}
				return false;
			}
		});

		setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(final View view, final int keyCode, final KeyEvent event) {
				if (event.getAction() == 0 && keyCode == 4 && ((WebView) view).canGoBack()) {
					((WebView) view).goBack();
					return true;
				}
				return false;
			}
		});

		setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(final WebView view, final String url) {
				view.clearCache(true);
			}
		});
	}

}