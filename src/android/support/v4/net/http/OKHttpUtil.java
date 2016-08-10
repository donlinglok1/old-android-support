package android.support.v4.net.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OKHTTP Controller
 */
public class OKHttpUtil {
    private static final OkHttpClient OKHTTPCLIENT;

    static {
	OKHTTPCLIENT = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
		.writeTimeout(10, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();
    }

    private OKHttpUtil() {

    }

    /**
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(final Request request) throws IOException {
	return OKHTTPCLIENT.newCall(request).execute();
    }

    /**
     *
     * @param request
     * @param responseCallback
     */
    public static void enqueue(final Request request, final Callback responseCallback) {
	OKHTTPCLIENT.newCall(request).enqueue(responseCallback);
    }
}