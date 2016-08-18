package android.n;

import android.support.v4.lang.It;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class NString {
    // http://www.robelle.com/smugbook/ascii.html

    // DONT CHANGE THE SORTING!!!
    public static String SLASH = new String(new char[] { 47 });
    public static String COLON = new String(new char[] { 58 });
    public static String UPPB = new String(new char[] { 66 });
    public static String LOWO = new String(new char[] { 111 });
    // public static String SYMBOL_GT = new String(new char[] { 62 });
    public static String SYMBOL_GT = " ► ";
    public static String COMMA = new String(new char[] { 44 });
    public static String MINUS = new String(new char[] { 45 });
    public static String DOT = new String(new char[] { 46 });
    public static String SYMBOL_QM = new String(new char[] { 63 });
    public static String OP_CUBRACK = new String(new char[] { 123 });
    public static String VERTICAL_LINE = new String(new char[] { 124 });
    public static String UPPI = new String(new char[] { 73 });
    public static String CL_CUBRACK = new String(new char[] { 125 });
    public static String UPPR = new String(new char[] { 82 });
    public static String UPPS = new String(new char[] { 83 });
    public static String EMPTY = "";
    public static String NEWLINE = "\n";
    public static String LOWY = new String(new char[] { 121 });
    public static String UPPW = new String(new char[] { 87 });
    public static String LOWS = new String(new char[] { 115 });
    public static String LOWP = new String(new char[] { 112 });
    public static String LOWA = new String(new char[] { 97 });
    public static String CL_SQBRACK = new String(new char[] { 93 });
    public static String CARET = new String(new char[] { 94 });
    public static String UNDER = new String(new char[] { 95 });
    public static String LOWR = new String(new char[] { 114 });
    public static String UPPU = new String(new char[] { 85 });
    // public static String LOWQ = new String(new char[] { 113 });
    public static String ATT = new String(new char[] { 64 });
    public static String DOLLAR = new String(new char[] { 36 });
    public static String LEFTBRACKETS = new String(new char[] { 40 });
    public static String RIGHTBRACKETS = new String(new char[] { 41 });
    public static String LOWZ = new String(new char[] { 122 });
    public static String ZERO = new String(new char[] { 48 });
    public static String UPPK = new String(new char[] { 75 });
    public static String UPPL = new String(new char[] { 76 });
    public static String UPPM = new String(new char[] { 77 });
    public static String UPPV = new String(new char[] { 86 });
    public static String UPPF = new String(new char[] { 70 });
    public static String LOWT = new String(new char[] { 116 });
    public static String LOWW = new String(new char[] { 119 });
    public static String LOWG = new String(new char[] { 103 });
    public static String SYMBOL_LT = new String(new char[] { 60 });
    public static String TWO = new String(new char[] { 50 });
    public static String FIVE = new String(new char[] { 53 });
    public static String NINE = new String(new char[] { 57 });
    public static String UPPA = new String(new char[] { 65 });
    public static String UPPH = new String(new char[] { 72 });
    public static String LOWB = new String(new char[] { 98 });
    public static String QUOTES = "'";
    public static String UPPX = new String(new char[] { 88 });
    public static String UPPY = new String(new char[] { 89 });
    public static String UPPC = new String(new char[] { 67 });
    public static String UPPT = new String(new char[] { 84 });
    public static String UPPE = new String(new char[] { 69 });
    public static String SPACE = " ";
    public static String UPPD = new String(new char[] { 68 });
    public static String LOWC = new String(new char[] { 99 });
    public static String EXCLAMATION = new String(new char[] { 33 });
    public static String CROSS = new String(new char[] { 35 });
    public static String LOWD = new String(new char[] { 100 });
    public static String UPPJ = new String(new char[] { 74 });
    public static String UPPQ = new String(new char[] { 81 });
    public static String SEVEN = new String(new char[] { 55 });
    public static String EIGHT = new String(new char[] { 56 });
    public static String THREE = new String(new char[] { 51 });
    public static String FOUR = new String(new char[] { 52 });
    public static String LOWU = new String(new char[] { 117 });
    public static String LOWV = new String(new char[] { 118 });
    public static String UPPG = new String(new char[] { 71 });
    public static String UPPZ = new String(new char[] { 90 });
    public static String LOWL = new String(new char[] { 108 });
    public static String LOWM = new String(new char[] { 109 });
    public static String UPPN = new String(new char[] { 78 });
    public static String UPPO = new String(new char[] { 79 });
    public static String UPPP = new String(new char[] { 80 });
    public static String LOWE = new String(new char[] { 101 });
    public static String LOWN = new String(new char[] { 110 });
    public static String EQUAL = new String(new char[] { 61 });
    public static String ONE = new String(new char[] { 49 });
    public static String SIX = new String(new char[] { 54 });
    public static String TILDE = new String(new char[] { 126 });
    public static String LOWH = new String(new char[] { 104 });
    public static String LOWI = new String(new char[] { 105 });
    public static String LOWJ = new String(new char[] { 106 });
    public static String LOWK = new String(new char[] { 107 });
    public static String LOWX = new String(new char[] { 120 });
    public static String LOWF = new String(new char[] { 102 });
    public static String OP_SQBRACK = new String(new char[] { 91 });
    public static String BACKSLASH = new String(new char[] { 92 });

    public static String add(final Object... words) {
	final StringBuilder queryBuffer = new StringBuilder();
	for (final Object word : words) {
	    queryBuffer.append(word);
	}
	return parse(queryBuffer, "");
    }

    public static String parse(final Object obj) {
	return null == obj ? "" : obj.toString();
    }

    public static String parse(final Object obj, final String defaultvalue) {
	return It.isNull(obj) ? defaultvalue : obj.toString();
    }

    public static String parse(final boolean booleans) {
	return booleans ? "true" : "false";
    }

    public static String parse(final int ints) {
	return Integer.toString(ints, 10);
    }

    public static String parse(final long longs) {
	return Long.toString(longs, 10);
    }

    public static String parse(final float floats) {
	return Float.toString(floats);
    }

    public static String parse(final double doubles) {
	return Double.toString(doubles);
    }

}
