package android.support.v4.lang;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import android.graphics.Bitmap;
import android.support.v4.graphics.Bitmaps;
import android.support.v4.util.Tools;
import android.util.Base64OutputStream;

public class Base64s {
    public static String compress(final String str) throws IOException {
	String result = str;
	if (!It.isNull(str)) {
	    final ByteArrayOutputStream out = new ByteArrayOutputStream();
	    final GZIPOutputStream gzip = new GZIPOutputStream(out);
	    gzip.write(str.getBytes());
	    gzip.close();
	    result = out.toString("ISO-8859-1");
	}
	return result;
    }

    public static String unCompress(final String str) throws IOException {
	String result = str;
	if (!It.isNull(str)) {
	    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    final ByteArrayInputStream inputStream = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
	    final GZIPInputStream gzip = new GZIPInputStream(inputStream);
	    final byte[] buffer = new byte[256];

	    int len;
	    while ((len = gzip.read(buffer)) > 0) {
		outputStream.write(buffer, 0, len);
	    }
	    result = outputStream.toString("UTF-8");
	}
	return result;
    }

    private static String DES = NString.parse(Strings.UPPD, Strings.UPPE, Strings.UPPS);

    public static String encrypt(final String data, final String key) {
	String result = data;
	try {
	    // System.out.println("size org:" + data.length());
	    // data = compress(data);
	    // System.out.println("size compress:" + data.length());

	    final SecureRandom sRandom = new SecureRandom();
	    final DESKeySpec dks = new DESKeySpec(key.getBytes());
	    final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
	    final SecretKey securekey = keyFactory.generateSecret(dks);
	    final Cipher cipher = Cipher.getInstance(DES);
	    cipher.init(Cipher.ENCRYPT_MODE, securekey, sRandom);

	    final String strs = Base64.encodeBytes(cipher.doFinal(data.getBytes()));
	    // System.out.println("size encrypt:" + strs.length());
	    result = strs;
	} catch (final IllegalArgumentException exception) {
	} catch (final IllegalBlockSizeException exception) {
	} catch (final Exception exception) {
	    Tools.exceptionToJSONObject(exception);
	}
	return result;
    }

    public static String decrypt(final String data, final String key) {
	String result = data;
	try {
	    if (!It.isNull(data)) {
		// System.out.println("size org:" + data.length());
		final byte[] buf = Base64.decode(data);

		final SecureRandom sRandom = new SecureRandom();
		final DESKeySpec dks = new DESKeySpec(key.getBytes());
		final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		final SecretKey securekey = keyFactory.generateSecret(dks);
		final Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sRandom);

		final String resultString = new String(cipher.doFinal(buf));
		// System.out.println("size decrypt:" + resultString.length());
		// return unCompress(resultString);
		// System.out.println("size unCompress:"
		// + unCompress(resultString).length());
		result = resultString;
	    }
	} catch (final IllegalArgumentException exception) {
	} catch (final IllegalBlockSizeException exception) {
	} catch (final IOException exception) {
	} catch (final Exception exception) {
	    Tools.exceptionToJSONObject(exception);
	}
	return result;
    }

    public static final String imgToBase64(final String filePath, final int size) {
	final Bitmap bitmap = Bitmaps.getBitmap(filePath, size, true);
	final ByteArrayOutputStream baos = new ByteArrayOutputStream();
	bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
	final byte[] byteArrayImage = baos.toByteArray();
	try {
	    baos.close();
	} catch (final IOException e) {
	}
	bitmap.recycle();

	return android.util.Base64.encodeToString(byteArrayImage, android.util.Base64.DEFAULT).replace("\n", "")
		.replace("=", "");
    }

    public static final String fileToBase64(final String filePath) {
	InputStream inputStream = null;
	try {
	    inputStream = new FileInputStream(filePath);
	} catch (final FileNotFoundException e1) {
	}
	final byte[] buffer = new byte[8192];
	int bytesRead;
	final ByteArrayOutputStream output = new ByteArrayOutputStream();
	final Base64OutputStream output64 = new Base64OutputStream(output, android.util.Base64.DEFAULT);
	try {
	    while ((bytesRead = inputStream.read(buffer)) != -1) {
		output64.write(buffer, 0, bytesRead);
	    }
	} catch (final IOException e) {
	}
	try {
	    output64.close();
	} catch (final IOException e) {
	}
	try {
	    inputStream.close();
	} catch (final IOException e) {
	}

	return android.util.Base64.encodeToString(buffer, android.util.Base64.DEFAULT).replace("\n", "").replace("=",
		"");
    }
}
