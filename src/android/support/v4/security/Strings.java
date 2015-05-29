package android.support.v4.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.SecureRandom;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import net.minidev.json.JSONObject;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public final class Strings {
	// http://www.robelle.com/smugbook/ascii.html

	// DONT CHANGE THE SORTING!!!
	public final static String UPPW = new String(new char[] { 87 });
	public final static String LOWS = new String(new char[] { 115 });
	public final static String LOWT = new String(new char[] { 116 });
	public final static String LOWW = new String(new char[] { 119 });
	public final static String LOWG = new String(new char[] { 103 });
	public final static String SYMBOL_LT = new String(new char[] { 60 });
	public final static String TWO = new String(new char[] { 50 });
	public final static String FIVE = new String(new char[] { 53 });
	public final static String NINE = new String(new char[] { 57 });
	public final static String UPPA = new String(new char[] { 65 });
	public final static String UPPH = new String(new char[] { 72 });
	public final static String LOWB = new String(new char[] { 98 });
	public final static String QUOTES = "'";
	public final static String UPPX = new String(new char[] { 88 });
	public final static String UPPY = new String(new char[] { 89 });
	public final static String UPPC = new String(new char[] { 67 });
	public final static String UPPT = new String(new char[] { 84 });
	public final static String UPPE = new String(new char[] { 69 });
	public final static String SPACE = " ";
	public final static String UPPD = new String(new char[] { 68 });
	public final static String LOWC = new String(new char[] { 99 });
	public final static String EXCLAMATION = new String(new char[] { 33 });
	public final static String CROSS = new String(new char[] { 35 });
	public final static String LOWD = new String(new char[] { 100 });
	public final static String UPPJ = new String(new char[] { 74 });
	public final static String UPPQ = new String(new char[] { 81 });
	public final static String UPPR = new String(new char[] { 82 });
	public final static String UPPS = new String(new char[] { 83 });
	public final static String EMPTY = "";
	public final static String NEWLINE = "\n";
	public final static String LOWY = new String(new char[] { 121 });
	public final static String LOWZ = new String(new char[] { 122 });
	public final static String ZERO = new String(new char[] { 48 });
	public final static String UPPK = new String(new char[] { 75 });
	public final static String UPPL = new String(new char[] { 76 });
	public final static String UPPM = new String(new char[] { 77 });
	public final static String UPPV = new String(new char[] { 86 });
	public final static String UPPF = new String(new char[] { 70 });
	public final static String UPPG = new String(new char[] { 71 });
	public final static String UPPZ = new String(new char[] { 90 });
	public final static String LOWL = new String(new char[] { 108 });
	public final static String LOWM = new String(new char[] { 109 });
	public final static String UPPN = new String(new char[] { 78 });
	public final static String UPPO = new String(new char[] { 79 });
	public final static String UPPP = new String(new char[] { 80 });
	public final static String LOWE = new String(new char[] { 101 });
	public final static String LOWN = new String(new char[] { 110 });
	public final static String EQUAL = new String(new char[] { 61 });
	public final static String ONE = new String(new char[] { 49 });
	public final static String SIX = new String(new char[] { 54 });
	public final static String SEVEN = new String(new char[] { 55 });
	public final static String EIGHT = new String(new char[] { 56 });
	public final static String THREE = new String(new char[] { 51 });
	public final static String FOUR = new String(new char[] { 52 });
	public final static String LOWU = new String(new char[] { 117 });
	public final static String LOWV = new String(new char[] { 118 });
	public final static String LOWP = new String(new char[] { 112 });
	public final static String LOWA = new String(new char[] { 97 });
	public final static String CL_SQBRACK = new String(new char[] { 93 });
	public final static String CARET = new String(new char[] { 94 });
	public final static String UNDER = new String(new char[] { 95 });
	public final static String LOWR = new String(new char[] { 114 });
	public final static String UPPU = new String(new char[] { 85 });
	// public final static String LOWQ = new String(new char[] { 113 });
	public final static String ATT = new String(new char[] { 64 });
	public final static String DOLLAR = new String(new char[] { 36 });
	public final static String LEFTBRACKETS = new String(new char[] { 40 });
	public final static String RIGHTBRACKETS = new String(new char[] { 41 });
	public final static String SLASH = new String(new char[] { 47 });
	public final static String COLON = new String(new char[] { 58 });
	public final static String UPPB = new String(new char[] { 66 });
	public final static String LOWO = new String(new char[] { 111 });
	// public final static String SYMBOL_GT = new String(new char[] { 62 });
	public final static String SYMBOL_GT = " ► ";
	public final static String COMMA = new String(new char[] { 44 });
	public final static String MINUS = new String(new char[] { 45 });
	public final static String DOT = new String(new char[] { 46 });
	public final static String SYMBOL_QM = new String(new char[] { 63 });
	public final static String OP_CUBRACK = new String(new char[] { 123 });
	public final static String VERTICAL_LINE = new String(new char[] { 124 });
	public final static String UPPI = new String(new char[] { 73 });
	public final static String CL_CUBRACK = new String(new char[] { 125 });
	public final static String TILDE = new String(new char[] { 126 });
	public final static String LOWH = new String(new char[] { 104 });
	public final static String LOWI = new String(new char[] { 105 });
	public final static String LOWJ = new String(new char[] { 106 });
	public final static String LOWK = new String(new char[] { 107 });
	public final static String LOWX = new String(new char[] { 120 });
	public final static String LOWF = new String(new char[] { 102 });
	public final static String OP_SQBRACK = new String(new char[] { 91 });
	public final static String BACKSLASH = new String(new char[] { 92 });

	private final static String DES = fString(UPPD, UPPE, UPPS);

	private Strings() {
	}

	public static JSONObject exceptionToJSONObject(final Exception exception) {
		final StringWriter sWriter = new StringWriter();
		exception.printStackTrace(new PrintWriter(sWriter));
		final JSONObject temp = new JSONObject();
		try {
			temp.put("Exception", Strings.valueOf(sWriter));
		} catch (final Exception e) {
			e.printStackTrace();
		}
		System.out.println(Strings.valueOf(sWriter));
		return temp;
	}

	// @SuppressLint("TrulyRandom")
	public static String encrypt(final String data, final String key) {
		String result = data;
		try {
			// System.out.println("size org:" + data.length());
			// data = compress(data);
			// System.out.println("size compress:" + data.length());

			final SecureRandom sRandom = new SecureRandom();
			final DESKeySpec dks = new DESKeySpec(key.getBytes());
			final SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance(DES);
			final SecretKey securekey = keyFactory.generateSecret(dks);
			final Cipher cipher = Cipher.getInstance(DES);
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sRandom);

			final String strs = Base64.encodeBytes(cipher.doFinal(data
					.getBytes()));
			// System.out.println("size encrypt:" + strs.length());
			result = strs;
		} catch (final Exception exception) {
			// exception.printStackTrace();
		}
		return result;
	}

	public static String decrypt(final String data, final String key) {
		String result = data;
		try {
			if (null != data && !Strings.EMPTY.equals(data)) {
				// System.out.println("size org:" + data.length());
				final byte[] buf = Base64.decode(data);

				final SecureRandom sRandom = new SecureRandom();
				final DESKeySpec dks = new DESKeySpec(key.getBytes());
				final SecretKeyFactory keyFactory = SecretKeyFactory
						.getInstance(DES);
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
		} catch (final Exception exception) {
			// exception.printStackTrace();
		}
		return result;
	}

	public static String fString(final String... words) {
		final StringBuffer queryBuffer = new StringBuffer();
		for (final String word : words) {
			queryBuffer.append(word);
		}
		return Strings.valueOf(queryBuffer);
	}

	public static String compress(final String str) throws IOException {
		String result = str;
		if (null != str && str.length() > 0) {
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
		if (null != str && str.length() > 0) {
			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			final ByteArrayInputStream inputStream = new ByteArrayInputStream(
					str.getBytes("ISO-8859-1"));
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

	public static boolean isNull(final Object object) {
		return null == object || valueOf(object).length() == 0;
	}

	/**
	 * Returns the string representation of the <code>Object</code> argument.
	 *
	 * @param obj
	 *            an <code>Object</code>.
	 * @return if the argument is <code>null</code>, then a string equal to
	 *         <code>"null"</code>; otherwise, the value of
	 *         <code>obj.toString()</code> is returned.
	 * @see java.lang.Object#toString()
	 */
	public static String valueOf(final Object obj) {
		return null == obj ? "" : obj.toString();
	}

	/**
	 * Returns the string representation of the <code>Object</code> argument.
	 *
	 * @param obj
	 *            an <code>Object</code>.
	 * @return if the argument is <code>null</code>, then a string equal to
	 *         <code>"null"</code>; otherwise, the value of
	 *         <code>obj.toString()</code> is returned.
	 * @see java.lang.Object#toString()
	 */
	public static String valueOf(final Object obj, final String defaultvalue) {
		return null == obj ? defaultvalue : obj.toString();
	}

	/**
	 * Returns the string representation of the <code>char</code> array
	 * argument. The contents of the character array are copied; subsequent
	 * modification of the character array does not affect the newly created
	 * string.
	 *
	 * @param data
	 *            a <code>char</code> array.
	 * @return a newly allocated string representing the same sequence of
	 *         characters contained in the character array argument.
	 */
	public static String valueOf(final char... data) {
		return new String(data);
	}

	/**
	 * Returns the string representation of a specific subarray of the
	 * <code>char</code> array argument.
	 * <p>
	 * The <code>offset</code> argument is the index of the first character of
	 * the subarray. The <code>count</code> argument specifies the length of the
	 * subarray. The contents of the subarray are copied; subsequent
	 * modification of the character array does not affect the newly created
	 * string.
	 *
	 * @param data
	 *            the character array.
	 * @param offset
	 *            the initial offset into the value of the <code>String</code>.
	 * @param count
	 *            the length of the value of the <code>String</code>.
	 * @return a string representing the sequence of characters contained in the
	 *         subarray of the character array argument.
	 * @exception IndexOutOfBoundsException
	 *                if <code>offset</code> is negative, or <code>count</code>
	 *                is negative, or <code>offset+count</code> is larger than
	 *                <code>data.length</code>.
	 */
	public static String valueOf(final char data[], final int offset,
			final int count) {
		return new String(data, offset, count);
	}

	/**
	 * Returns a String that represents the character sequence in the array
	 * specified.
	 *
	 * @param data
	 *            the character array.
	 * @param offset
	 *            initial offset of the subarray.
	 * @param count
	 *            length of the subarray.
	 * @return a <code>String</code> that contains the characters of the
	 *         specified subarray of the character array.
	 */
	public static String copyValueOf(final char data[], final int offset,
			final int count) {
		// All public String constructors now copy the data.
		return new String(data, offset, count);
	}

	/**
	 * Returns a String that represents the character sequence in the array
	 * specified.
	 *
	 * @param data
	 *            the character array.
	 * @return a <code>String</code> that contains the characters of the
	 *         character array.
	 */
	public static String copyValueOf(final char... data) {
		return copyValueOf(data, 0, data.length);
	}

	/**
	 * Returns the string representation of the <code>boolean</code> argument.
	 *
	 * @param booleans
	 *            a <code>boolean</code>.
	 * @return if the argument is <code>true</code>, a string equal to
	 *         <code>"true"</code> is returned; otherwise, a string equal to
	 *         <code>"false"</code> is returned.
	 */
	public static String valueOf(final boolean booleans) {
		return booleans ? "true" : "false";
	}

	/**
	 * Returns the string representation of the <code>int</code> argument.
	 * <p>
	 * The representation is exactly the one returned by the
	 * <code>Integer.toString</code> method of one argument.
	 *
	 * @param ints
	 *            an <code>int</code>.
	 * @return a string representation of the <code>int</code> argument.
	 * @see java.lang.Integer#toString(int, int)
	 */
	public static String valueOf(final int ints) {
		return Integer.toString(ints, 10);
	}

	/**
	 * Returns the string representation of the <code>long</code> argument.
	 * <p>
	 * The representation is exactly the one returned by the
	 * <code>Long.toString</code> method of one argument.
	 *
	 * @param longs
	 *            a <code>long</code>.
	 * @return a string representation of the <code>long</code> argument.
	 * @see java.lang.Long#toString(long)
	 */
	public static String valueOf(final long longs) {
		return Long.toString(longs, 10);
	}

	/**
	 * Returns the string representation of the <code>float</code> argument.
	 * <p>
	 * The representation is exactly the one returned by the
	 * <code>Float.toString</code> method of one argument.
	 *
	 * @param floats
	 *            a <code>float</code>.
	 * @return a string representation of the <code>float</code> argument.
	 * @see java.lang.Float#toString(float)
	 */
	public static String valueOf(final float floats) {
		return Float.toString(floats);
	}

	/**
	 * Returns the string representation of the <code>double</code> argument.
	 * <p>
	 * The representation is exactly the one returned by the
	 * <code>Double.toString</code> method of one argument.
	 *
	 * @param doubles
	 *            a <code>double</code>.
	 * @return a string representation of the <code>double</code> argument.
	 * @see java.lang.Double#toString(double)
	 */
	public static String valueOf(final double doubles) {
		return Double.toString(doubles);
	}

}
