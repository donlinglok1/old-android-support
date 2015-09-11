package android.support.v4.net.http;

import java.io.File;

import android.app.Activity;
import android.support.v4.lang.Strings;
import android.support.v4.os.storage.HttpDownloadTask;
import android.support.v4.os.storage.HttpDownloadTask.HttpDownloadTaskCallback;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class HttpGlide {
	public void load(final Activity context, final String path,
			final ImageView imageView) {
		final String file = path.substring(path.lastIndexOf('/') + 1,
				path.length());
		if (new File(file).exists()) {
			try {
				Glide.with(context).load(new File(file))
				// .error(R.drawable.setting_icon_notice)
						.centerCrop().into(imageView);
			} catch (final Exception exception) {
				// exception.printStackTrace();
			}
		} else {
			new HttpDownloadTask(context, new HttpDownloadTaskCallback() {
				@Override
				public void onSuccess(final File outputFile) {
					context.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								Glide.with(context).load(outputFile)
								// .error(R.drawable.setting_icon_notice)
										.centerCrop().into(imageView);
							} catch (final Exception exception) {
								// exception.printStackTrace();
							}
						}
					});
				}

				@Override
				public void onFail(final Exception exception) {
					context.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								Glide.with(context).load(path)
								// .error(R.drawable.setting_icon_notice)
										.centerCrop().into(imageView);
							} catch (final Exception exception2) {
								Strings.exceptionToJSONObject(exception2);
							}
						}
					});
				}
			}, path, file).execute();
		}
	}
}
