package android.support.v4.net.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

import android.support.v4.lang.Strings;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class HttpPosts {
	private static final int TIMEOUTSOCKET = 5000 * 4;
	private static final int TIMEOUTCONNECTION = 3000 * 4;

	public static HttpClient createHttpClient() {
		final HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params,
				HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		HttpConnectionParams.setConnectionTimeout(params, TIMEOUTCONNECTION);
		HttpConnectionParams.setSoTimeout(params, TIMEOUTSOCKET);
		final SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));
		final ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
				params, schReg);

		return new DefaultHttpClient(conMgr, params);
	}

	public static String postBody(final String url, final String body) {
		String reuslt = Strings.EMPTY;
		try {
			final HttpPost httppost = new HttpPost(url);
			httppost.setHeader("Accept", "application/json");
			httppost.addHeader("Accept-encoding", "gzip, deflate");
			httppost.setHeader("Content-type",
					"application/json; charset=utf-8");
			if (null != body) {
				httppost.setEntity(new StringEntity(HttpCrypt.encrypt(body),
						HTTP.UTF_8));
			}
			final HttpClient httpclient = createHttpClient();
			final HttpResponse response = httpclient.execute(httppost);
			if (null != response) {
				final HttpEntity entity = response.getEntity();

				final ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(
						4096);
				final GZIPInputStream gzipInputStream = new GZIPInputStream(
						entity.getContent());
				int length;
				final byte[] tempbyte = new byte[4096];
				while ((length = gzipInputStream.read(tempbyte)) != -1) {
					byteArrayBuffer.append(tempbyte, 0, length);
				}
				reuslt = HttpCrypt.decrypt(new String(
						byteArrayBuffer.toByteArray(), "utf-8"));
			}
		} catch (final UnsupportedEncodingException exception) {
		} catch (final ClientProtocolException exception) {
		} catch (final IOException exception) {
		}
		return reuslt;
	}

	public static String postBodyNoGzip(final String url, final String body) {
		String result = Strings.EMPTY;
		try {
			final HttpPost httppost = new HttpPost(url);
			httppost.setHeader("Accept", "application/json");
			httppost.setHeader("Content-type",
					"application/json; charset=utf-8");
			if (null != body) {
				httppost.setEntity(new StringEntity(HttpCrypt.encrypt(body),
						HTTP.UTF_8));
			}
			final HttpClient httpclient = createHttpClient();
			final HttpResponse response = httpclient.execute(httppost);
			if (null != response) {
				final HttpEntity entity = response.getEntity();

				result = HttpCrypt.decrypt(EntityUtils.toString(entity));
			}
		} catch (final UnsupportedEncodingException exception) {
		} catch (final ClientProtocolException exception) {
		} catch (final IOException exception) {
		}
		return result;
	}

	public static String postNameValuePair(final String url,
			final List<NameValuePair> nameValuePairs) {
		String result = Strings.EMPTY;
		try {
			final HttpPost httppost = new HttpPost(url);
			httppost.addHeader("Accept-encoding", "gzip, deflate");
			httppost.setHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=utf-8");
			if (null != nameValuePairs) {
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
						HTTP.UTF_8));
			}
			final HttpClient httpclient = createHttpClient();
			final HttpResponse response = httpclient.execute(httppost);
			if (null != response) {
				final HttpEntity entity = response.getEntity();

				final ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(
						4096);
				final GZIPInputStream gzipInputStream = new GZIPInputStream(
						entity.getContent());
				int length;
				final byte[] tempbyte = new byte[4096];
				while ((length = gzipInputStream.read(tempbyte)) != -1) {
					byteArrayBuffer.append(tempbyte, 0, length);
				}
				result = new String(byteArrayBuffer.toByteArray(), "utf-8");
			}
		} catch (final UnsupportedEncodingException exception) {
		} catch (final ClientProtocolException exception) {
		} catch (final IOException exception) {
		}
		return result;
	}

	public static String postNameValuePairNoGzip(final String url,
			final List<NameValuePair> nameValuePairs) {
		String result = Strings.EMPTY;
		try {
			final HttpPost httppost = new HttpPost(url);
			httppost.setHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=utf-8");
			if (null != nameValuePairs) {
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
						HTTP.UTF_8));
			}
			final HttpClient httpclient = createHttpClient();
			final HttpResponse response = httpclient.execute(httppost);
			if (null != response) {
				final HttpEntity entity = response.getEntity();

				result = EntityUtils.toString(entity);
			}
		} catch (final UnsupportedEncodingException exception) {
		} catch (final ClientProtocolException exception) {
		} catch (final IOException exception) {
		}
		return result;
	}
}
