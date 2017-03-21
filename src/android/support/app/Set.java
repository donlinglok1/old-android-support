package android.support.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.n.NString;
import android.support.graphics.drawable.Drawables;
import android.support.view.VibrateListener;
import android.support.view.VibrateListener.Callback;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Set {
	public final Activity activity;
	public final Context appContext;

	public Set(final Activity activity) {
		this.activity = activity;
		appContext = activity.getApplicationContext();
	}

	public static final String string(final Object object) {
		return NString.parse(object, "");
	}

	public static final String string(final Object object, final String defaultValue) {
		return NString.parse(object, defaultValue);
	}

	private Toast toast;

	public final void alert(final String msg) {
		alert(msg, 1);
	}

	public final void alert(final String msg, final int second) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (toast != null)
					toast.cancel();

				for (int i = 0; i < second; i++) {
					toast = Toast.makeText(appContext, msg, Toast.LENGTH_LONG);
					if (!activity.isFinishing())
						toast.show();
				}
			}
		});
	}

	public final void alertCenter(final String msg, final int second) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (toast != null)
					toast.cancel();

				for (int i = 0; i < second; i++) {
					toast = Toast.makeText(appContext, msg, Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					// the default toast view group is a relativelayout
					final ViewGroup toastLayout = (ViewGroup) toast.getView();
					final TextView toastTV = (TextView) toastLayout.getChildAt(0);
					toastTV.setTextSize(30);
					if (!activity.isFinishing())
						toast.show();
				}
			}
		});
	}

	public final String string(final int resId) {
		return appContext.getString(resId);
	}

	public final View text(final View view, final Object object) {
		if (view instanceof TextView)
			((TextView) view).setText(NString.parse(object));
		else if (view instanceof Button)
			((Button) view).setText(NString.parse(object));

		return view;
	}

	public final View icon(final View view, final Drawable left, final Drawable top, final Drawable right,
			final Drawable bottom) {
		if (view instanceof TextView)
			((TextView) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
		else if (view instanceof Button)
			((Button) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
		return view;
	}

	public final View iconPadding(final View view, final int dp) {
		if (view instanceof TextView)
			((TextView) view).setCompoundDrawablePadding(Drawables.dp2px(dp));
		else if (view instanceof Button)
			((Button) view).setCompoundDrawablePadding(Drawables.dp2px(dp));
		return view;
	}

	public final void vClick(final View view, final Callback callback) {
		view.setOnTouchListener(new VibrateListener(appContext, callback));
	}

	public final Drawable image(final int resId) {
		return Drawables.get(appContext, resId);
	}

	public final Drawable image(final int resId, final int dp) {
		return image(resId, dp, dp);
	}

	public final Drawable image(final int resId, final int dp, final int dp2) {
		return Drawables.get(appContext, resId, Drawables.dp2px(dp), Drawables.dp2px(dp2));
	}

	public final Drawable imageByPx(final int resId, final int px) {
		return imageByPx(resId, px, px);
	}

	public final Drawable imageByPx(final int resId, final int px, final int px2) {
		return Drawables.get(appContext, resId, px, px2);
	}

	@SuppressWarnings("deprecation")
	public final int color(final int resId) {
		return appContext.getResources().getColor(resId);
	}

	public final View show(final View view) {
		view.setVisibility(View.VISIBLE);
		return view;
	}

	public final View gone(final View view) {
		view.setVisibility(View.GONE);
		return view;
	}

	public final View hide(final View view) {
		view.setVisibility(View.INVISIBLE);
		return view;
	}

	public final void onUI(final Runnable runnable) {
		activity.runOnUiThread(runnable);
	}
}
