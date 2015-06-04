package android.support.v4.net.http;

import java.util.List;
import java.util.Locale;
import java.util.zip.GZIPInputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
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
public final class HttpPosts {
	private final static int TIMEOUTSOCKET = 5000 * 2;
	private final static int TIMEOUTCONNECTION = 3000 * 2;

	private HttpPosts() {
	}

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

	public static String postJson(final String url, final String nameValuePairs) {
		String reuslt = Strings.EMPTY;
		try {
			final HttpPost httppost = new HttpPost(url);
			httppost.setHeader("Accept", "application/json");
			httppost.addHeader("Accept-encoding", "gzip, deflate");
			httppost.setHeader("Content-type",
					"application/json; charset=utf-8");
			if (null != nameValuePairs) {
				httppost.setEntity(new StringEntity(encrypt(nameValuePairs),
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
				reuslt = decrypt(new String(byteArrayBuffer.toByteArray(),
						"utf-8"));
			}
		} catch (final Exception exception) {
			// exception.printStackTrace();
		}
		return reuslt;
	}

	public static String postJsonNoGzip(final String url,
			final String nameValuePairs) {
		String result = Strings.EMPTY;
		try {
			final HttpPost httppost = new HttpPost(url);
			httppost.setHeader("Accept", "application/json");
			httppost.setHeader("Content-type",
					"application/json; charset=utf-8");
			if (null != nameValuePairs) {
				httppost.setEntity(new StringEntity(encrypt(nameValuePairs),
						HTTP.UTF_8));
			}
			final HttpClient httpclient = createHttpClient();
			final HttpResponse response = httpclient.execute(httppost);
			if (null != response) {
				final HttpEntity entity = response.getEntity();

				result = decrypt(EntityUtils.toString(entity));
			}
		} catch (final Exception exception) {
			// exception.printStackTrace();
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
		} catch (final Exception exception) {
			// exception.printStackTrace();
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
		} catch (final Exception exception) {
			// exception.printStackTrace();
		}
		return result;
	}

	private final static String AES = Strings.fString(Strings.UPPA,
			Strings.UPPE, Strings.UPPS);

	public static String decrypt(final String plaintext) {
		return decrypt(plaintext, "57238004e784498bbc2f8bf984565090");
	}

	public static String decrypt(final String plaintext, final String key) {
		String result = plaintext;
		try {
			final SecretKeySpec secretKeySpec = new SecretKeySpec(
					hexStringToByteArray(key), AES);
			final Cipher cipher = Cipher.getInstance(AES);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec,
					cipher.getParameters());
			result = new String(cipher.doFinal(hexStringToByteArray(plaintext)));
		} catch (final Exception exception) {
			// exception.printStackTrace();
		}
		return result;
	}

	public static String encrypt(final String plaintext) {
		return encrypt(plaintext, "57238004e784498bbc2f8bf984565090");
	}

	public static String encrypt(final String plaintext, final String key) {
		String result = plaintext;
		try {
			final SecretKeySpec secretKeySpec = new SecretKeySpec(
					hexStringToByteArray(key), AES);
			final Cipher cipher = Cipher.getInstance(AES);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec,
					cipher.getParameters());
			result = byteArrayToHexString(cipher.doFinal(plaintext.getBytes()));
		} catch (final Exception exception) {
			// exception.printStackTrace();
		}
		return result;
	}

	public static String byteArrayToHexString(final byte... bytes) {
		String result = new String(bytes);
		try {
			final StringBuilder stringBuilder = new StringBuilder(
					bytes.length * 2);
			for (final byte b : bytes) {
				final int calint = b & 0xff;
				if (calint < 16) {
					stringBuilder.append('0');
				}
				stringBuilder.append(Integer.toHexString(calint));
			}
			result = stringBuilder.toString().toUpperCase(Locale.US);
		} catch (final Exception exception) {
			// exception.printStackTrace();
		}
		return result;
	}

	public static byte[] hexStringToByteArray(final String string) {
		byte[] result = string.getBytes();
		try {
			final byte[] bytes = new byte[string.length() / 2];
			for (int i = 0; i < bytes.length; i++) {
				final int index = i * 2;
				final int calint = Integer.parseInt(
						string.substring(index, index + 2), 16);
				bytes[i] = (byte) calint;
			}
			result = bytes;
		} catch (final Exception exception) {
			// exception.printStackTrace();
		}
		return result;
	}
}
