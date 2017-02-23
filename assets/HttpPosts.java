package android.support.v4.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class HttpPosts {
	// public static final String postBody(final String url, final String body)
	// {
	// return postBody(url, body, true, null);
	// }
	//
	// public static final String postBody(final String url, final String body,
	// final boolean isGzip,
	// final InputStream in) {
	// String result = Strings.EMPTY;
	// try {
	// final HttpPost httppost = new HttpPost(url);
	// httppost.setHeader("Accept", "application/json");
	// httppost.addHeader("Accept-encoding", "gzip, deflate");
	// httppost.setHeader("Content-type", "application/json; charset=utf-8");
	// if (It.isNull(body)) {
	// httppost.setEntity(new StringEntity(HttpCrypt.encrypt(body),
	// HTTP.UTF_8));
	// }
	// final HttpClient httpclient = createHttpClient(in);
	// final HttpResponse response = httpclient.execute(httppost);
	// if (null != response) {
	// final HttpEntity entity = response.getEntity();
	//
	// if (isGzip) {
	// final ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(4096);
	// final GZIPInputStream gzipInputStream = new
	// GZIPInputStream(entity.getContent());
	// int length;
	// final byte[] tempbyte = new byte[4096];
	// while ((length = gzipInputStream.read(tempbyte)) != -1) {
	// byteArrayBuffer.append(tempbyte, 0, length);
	// }
	// result = HttpCrypt.decrypt(new String(byteArrayBuffer.toByteArray(),
	// "utf-8"));
	// } else {
	// result = HttpCrypt.decrypt(EntityUtils.toString(entity));
	// }
	// }
	// } catch (final UnsupportedEncodingException exception) {
	// } catch (final ClientProtocolException exception) {
	// } catch (final IOException exception) {
	// }
	// return result;
	// }
	//
	// public static final String postNameValuePair(final String url, final
	// List<NameValuePair> nameValuePairs) {
	// return postNameValuePair(url, nameValuePairs, true, null);
	// }
	//
	// public static final String postNameValuePair(final String url, final
	// List<NameValuePair> nameValuePairs,
	// final boolean isGzip, final InputStream in) {
	// String result = Strings.EMPTY;
	// try {
	// final HttpPost httppost = new HttpPost(url);
	// httppost.addHeader("Accept-encoding", "gzip, deflate");
	// httppost.setHeader("Content-Type", "application/x-www-form-urlencoded;
	// charset=utf-8");
	// if (null != nameValuePairs) {
	// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
	// }
	// final HttpClient httpclient = createHttpClient(in);
	// final HttpResponse response = httpclient.execute(httppost);
	// if (null != response) {
	// final HttpEntity entity = response.getEntity();
	//
	// if (isGzip) {
	// final ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(4096);
	// final GZIPInputStream gzipInputStream = new
	// GZIPInputStream(entity.getContent());
	// int length;
	// final byte[] tempbyte = new byte[4096];
	// while ((length = gzipInputStream.read(tempbyte)) != -1) {
	// byteArrayBuffer.append(tempbyte, 0, length);
	// }
	// result = new String(byteArrayBuffer.toByteArray(), "utf-8");
	// } else {
	// result = EntityUtils.toString(entity);
	// }
	// }
	// } catch (final UnsupportedEncodingException exception) {
	// } catch (final ClientProtocolException exception) {
	// } catch (final IOException exception) {
	// }
	// return result;
	// }

	// public static final String post(final String url, final Object body) {
	// return post(url, body, true, null);
	// }

	// public static final String post(final String url, final Object body,
	// final boolean isGzip, final InputStream in) {
	// final String result = Strings.EMPTY;
	// try {
	// final HttpPost httppost = new HttpPost(url);
	// // httppost.setHeader("Accept", "application/json");
	// httppost.addHeader("Accept-encoding", "gzip, deflate");
	// httppost.setHeader("Content-type", "application/json;
	// charset=utf-8");
	// if (null != body) {
	// if (body instanceof List) {
	// @SuppressWarnings("unchecked")
	// final List<NameValuePair> list = (List<NameValuePair>) body;
	// httppost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
	// } else {
	// httppost.setEntity(new
	// StringEntity(HttpCrypt.encrypt(body.toString()), HTTP.UTF_8));
	// }
	// }
	// final HttpClient httpclient = createHttpClient(in);
	// final HttpResponse response = httpclient.execute(httppost);
	// if (null != response) {
	// final HttpEntity entity = response.getEntity();
	//
	// if (isGzip) {
	// final ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(4096);
	// final GZIPInputStream gzipInputStream = new
	// GZIPInputStream(entity.getContent());
	// int length;
	// final byte[] tempbyte = new byte[4096];
	// while ((length = gzipInputStream.read(tempbyte)) != -1) {
	// byteArrayBuffer.append(tempbyte, 0, length);
	// }
	// result = HttpCrypt.decrypt(new String(byteArrayBuffer.toByteArray(),
	// "utf-8"));
	// } else {
	// result = HttpCrypt.decrypt(EntityUtils.toString(entity));
	// }
	// }
	// } catch (final UnsupportedEncodingException exception) {
	// } catch (final ClientProtocolException exception) {
	// } catch (final IOException exception) {
	// }
	// return result;
	// }

	public interface HttpPostsCallback {
		void onReturn(final String result);
	}

	private static final int TIMEOUTSOCKET = 5000 * 4;
	private static final int TIMEOUTCONNECTION = 3000 * 4;

	// public static final HttpClient createHttpClient(final InputStream in) {
	// final HttpParams params = new BasicHttpParams();
	// HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	// HttpProtocolParams.setContentCharset(params,
	// HTTP.DEFAULT_CONTENT_CHARSET);
	// HttpProtocolParams.setUseExpectContinue(params, true);
	// HttpConnectionParams.setConnectionTimeout(params, TIMEOUTCONNECTION);
	// HttpConnectionParams.setSoTimeout(params, TIMEOUTSOCKET);
	// final SchemeRegistry schReg = new SchemeRegistry();
	// schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(),
	// 80));
	// if (null != in) {
	// newSslSocketFactory(in);
	// try {
	// final KeyStore trustStore =
	// KeyStore.getInstance(KeyStore.getDefaultType());
	// trustStore.load(null, null);
	//
	// final ALLSSLSocketFactory allsslSocketFactory = new
	// ALLSSLSocketFactory(trustStore);
	// allsslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	//
	// schReg.register(new Scheme("https", allsslSocketFactory, 443));
	// } catch (final Exception e) {
	// e.printStackTrace();
	// }
	// } else {
	// schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(),
	// 443));
	// }
	// final ClientConnectionManager conMgr = new
	// ThreadSafeClientConnManager(params, schReg);
	//
	// return new DefaultHttpClient(conMgr, params);
	// }

	private static SSLSocketFactory newSslSocketFactory(final InputStream in) {
		try {
			final KeyStore trusted = KeyStore.getInstance("BKS");
			try {
				trusted.load(in, "".toCharArray());
			} finally {
				in.close();
			}
			return new SSLSocketFactory(trusted);
		} catch (final Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
}

class ALLSSLSocketFactory extends SSLSocketFactory {
	SSLContext sslContext = SSLContext.getInstance("TLS");

	public ALLSSLSocketFactory(final KeyStore truststore)
			throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
		super(truststore);

		final TrustManager tm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(final X509Certificate[] chain, final String authType)
					throws CertificateException {
			}

			@Override
			public void checkServerTrusted(final X509Certificate[] chain, final String authType)
					throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sslContext.init(null, new TrustManager[] { tm }, null);
	}

	@Override
	public Socket createSocket(final Socket socket, final String host, final int port, final boolean autoClose)
			throws IOException, UnknownHostException {
		return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
	}

	@Override
	public Socket createSocket() throws IOException {
		return sslContext.getSocketFactory().createSocket();
	}
}
