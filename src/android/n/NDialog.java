package android.n;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.Drawables;
import android.support.v4.view.VibrateListener;
import android.support.v4.view.VibrateListener.Callback;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class NDialog extends Dialog {
    public transient Activity activity;
    public final Context appContext;

    public NDialog(final Activity activity, final int resId) {
	super(activity, resId);
	this.activity = activity;
	appContext = activity.getApplicationContext();
	getWindow().setGravity(Gravity.CENTER);
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

    public String getString(final int resId) {
	return appContext.getString(resId);
    }

    public int getRColor(final int resId) {
	return appContext.getResources().getColor(resId);
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
