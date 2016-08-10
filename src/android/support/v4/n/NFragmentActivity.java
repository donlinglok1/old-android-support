package android.support.v4.n;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.Drawables;
import android.support.v4.util.Tools;
import android.support.v4.view.VibrateListener;
import android.support.v4.view.VibrateListener.Callback;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

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
public class NFragmentActivity extends FragmentActivity {
    private transient Toast toast;

    public void alert(final String msg) {
	alert(msg, 1);
    }

    public void alert(final String msg, final int second) {
	runOnUiThread(new Runnable() {
	    @Override
	    public void run() {
		if (toast != null) {
		    toast.cancel();
		}

		for (int i = 0; i < second; i++) {
		    toast = Toast.makeText(appContext, msg, Toast.LENGTH_LONG);
		    if (!activity.isFinishing()) {
			toast.show();
		    }
		}
	    }
	});
    }

    private transient ProgressDialog progressDialog;

    public void showProgressDialog() {
	runOnUiThread(new Runnable() {
	    @Override
	    public void run() {
		if (null == progressDialog) {
		    progressDialog = new ProgressDialog(appContext);
		    progressDialog.setCancelable(false);
		}
		if (!progressDialog.isShowing() && !((Activity) activity).isFinishing()) {
		    progressDialog.show();
		}
	    }
	});
    }

    public void closeProgressDialog() {
	runOnUiThread(new Runnable() {
	    @Override
	    public void run() {
		if (null != progressDialog && progressDialog.isShowing() && !((Activity) activity).isFinishing()) {
		    progressDialog.cancel();
		}
	    }
	});
    }

    public void fadeIn(final View view) {
	if (View.VISIBLE != view.getVisibility()) {
	    view.startAnimation(AnimationUtils.loadAnimation(appContext, android.R.anim.fade_in));
	    view.setVisibility(View.VISIBLE);
	}
    }

    public void fadeOut(final View view) {
	if (View.GONE != view.getVisibility()) {
	    view.startAnimation(AnimationUtils.loadAnimation(appContext, android.R.anim.fade_out));
	    view.setVisibility(View.GONE);
	}
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
	return getImage(resId, size, size);
    }

    public Drawable getImage(final int resId, final int size, final int size2) {
	return Drawables.get(appContext, resId, size, size2);
    }

    private final void setConfigCallback(final WindowManager windowManager) {
	try {
	    Field field = WebView.class.getDeclaredField("mWebViewCore");
	    field = field.getType().getDeclaredField("mBrowserFrame");
	    field = field.getType().getDeclaredField("sConfigCallback");
	    field.setAccessible(true);
	    final Object configCallback = field.get(null);

	    if (configCallback == null) {
		return;
	    }

	    field = field.getType().getDeclaredField("mWindowManager");
	    field.setAccessible(true);
	    field.set(configCallback, windowManager);
	} catch (final NoSuchFieldException localNoSuchFieldException) {
	} catch (final IllegalAccessException localIllegalAccessException) {
	} catch (final IllegalArgumentException localIllegalArgumentException) {
	}
    }

    public transient FragmentActivity activity;
    public transient Context appContext;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
	onCreate(savedInstanceState, true);
    }

    private final transient Thread.UncaughtExceptionHandler onRuntimeError = new Thread.UncaughtExceptionHandler() {
	@Override
	public void uncaughtException(final Thread thread, final Throwable exception) {
	    Tools.exceptionToJSONObject((Exception) exception);
	    final Intent intent = appContext.getPackageManager().getLaunchIntentForPackage(appContext.getPackageName());
	    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
	    startActivity(intent);
	    System.exit(0);
	}
    };

    public void onCreate(final Bundle savedInstanceState, final boolean isUndead) {
	super.onCreate(savedInstanceState);

	activity = this;
	appContext = activity.getApplicationContext();

	if (isUndead) {
	    Thread.setDefaultUncaughtExceptionHandler(onRuntimeError);
	}

	setConfigCallback((WindowManager) getApplicationContext().getSystemService("window"));

	setStatusBarColor(0);
    }

    @SuppressLint("NewApi")
    public void setStatusBarColor(final int color) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
	    final Window window = getWindow();
	    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
	    window.setStatusBarColor(color);
	}
    }

    @Override
    public void onDestroy() {
	setConfigCallback(null);
	super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(final Bundle bundle) {
	// No call for super(). Bug on API Level > 11.
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