package android.support.net.http;

import java.io.IOException;

import android.util.Log;
import net.minidev.json.JSONObject;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author kennetht
 *
 */
public class OKHTTP {
	public static abstract interface Callback {
		public abstract void onResult(Call call, String result);
	}

	public static final void longlog(final String tag, final String string) {
		final int maxLogSize = 1000;
		for (int i = 0; i <= string.length() / maxLogSize; i++) {
			final int start = i * maxLogSize;
			int end = (i + 1) * maxLogSize;
			end = end > string.length() ? string.length() : end;
			Log.v(tag, string.substring(start, end));
		}
	}

	protected static final void run(final String url, final Request request, final Callback callback) {
		OKHttpUtil.enqueue(request, new okhttp3.Callback() {
			@Override
			public void onResponse(final Call call, final Response response) throws IOException {
				final String result = response.body().string();
				OKHTTP.longlog("", url + " " + result);
				callback.onResult(call, result);
			}

			@Override
			public void onFailure(final Call call, final IOException exception) {
				exception.printStackTrace();
				callback.onResult(call, "");
			}
		});
	}

	public static final void get(final String url, final Callback callback) {
		final String para = "";

		final Request request = new Request.Builder().header("Content-Type", "application/json")
				.header("Cache-Control", "no-cache").url(url + para).build();

		run(url, request, callback);
	}

	public static final void post(final JSONObject jsonObject, final String url, final Callback callback) {
		final RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
				jsonObject.toJSONString());

		final Request request = new Request.Builder().header("Content-Type", "application/json")
				.header("Cache-Control", "no-cache").url(url).post(body).build();

		run(url, request, callback);
	}

	public static final void put(final JSONObject jsonObject, final String url, final Callback callback) {
		final RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
				jsonObject.toJSONString());

		final Request request = new Request.Builder().header("Content-Type", "application/json")
				.header("Cache-Control", "no-cache").url(url).put(body).build();

		run(url, request, callback);
	}

	public static final void delete(final JSONObject jsonObject, final String url, final Callback callback) {
		final RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
				jsonObject.toJSONString());

		final Request request = new Request.Builder().header("Content-Type", "application/json")
				.header("Cache-Control", "no-cache").url(url).delete(body).build();

		run(url, request, callback);
	}
}
