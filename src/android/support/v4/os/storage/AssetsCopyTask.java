package android.support.v4.os.storage;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.security.Strings;

public class AssetsCopyTask extends AsyncTask<Void, Void, String> {
	private transient final Context context;
	private transient AssetsCopyTaskCallback callback;

	public interface AssetsCopyTaskCallback {
		void onReturn(Boolean result);
	}

	public AssetsCopyTask(final Context context,
			final AssetsCopyTaskCallback callback) {
		super();
		this.context = context;
		this.callback = callback;
	}

	public AssetsCopyTask(final Context context) {
		super();
		this.context = context;
	}

	@Override
	public String doInBackground(final Void... params) {
		try {
			Files.copyAssets(context);
			callback.onReturn(true);
		} catch (final Exception exception) {
			// exception.printStackTrace();
			callback.onReturn(false);
		}
		return Strings.EMPTY;
	}
}
