package android.support.v4.content.SharedPreferences;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.security.Strings;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public final class Preferences {
	public final static String SHAREDPREFERENCESNAME = "SharedPreference";

	private Preferences() {
	}

	public static void setPreferences(final Context context, final String key,
			final boolean value) {
		setPreferences(context, SHAREDPREFERENCESNAME, key, value);
	}

	public static void setPreferences(final Context context, final String key,
			final float value) {
		setPreferences(context, SHAREDPREFERENCESNAME, key, value);
	}

	public static void setPreferences(final Context context, final String key,
			final int value) {
		setPreferences(context, SHAREDPREFERENCESNAME, key, value);
	}

	public static void setPreferences(final Context context, final String key,
			final Long value) {
		setPreferences(context, SHAREDPREFERENCESNAME, key, value);
	}

	public static void setPreferences(final Context context, final String key,
			final String value) {
		setPreferences(context, SHAREDPREFERENCESNAME, key, value);
	}

	public static boolean setPreferencesStringArray(final Context context,
			final String arrayName, final ArrayList<?> array) {
		return setPreferencesStringArray(context, SHAREDPREFERENCESNAME,
				arrayName, array);
	}

	public static boolean getBoolean(final Context context, final String key,
			final boolean defValue) {
		return getBoolean(context, SHAREDPREFERENCESNAME, key, defValue);
	}

	public static float getFloat(final Context context, final String key,
			final float defValue) {
		return getFloat(context, SHAREDPREFERENCESNAME, key, defValue);
	}

	public static int getInt(final Context context, final String key,
			final int defValue) {
		return getInt(context, SHAREDPREFERENCESNAME, key, defValue);
	}

	public static Long getLong(final Context context, final String key,
			final Long defValue) {
		return getLong(context, SHAREDPREFERENCESNAME, key, defValue);
	}

	public static String getString(final Context context, final String key,
			final String defValue) {
		return getString(context, SHAREDPREFERENCESNAME, key, defValue);
	}

	public static ArrayList<?> getPreferencesStringArray(final Context context,
			final String arrayName) {
		return getPreferencesStringArray(context, SHAREDPREFERENCESNAME,
				arrayName);
	}

	public static void setPreferences(final Context context,
			final String sharedPreferencesName, final String key,
			final boolean value) {
		final Editor editor = context.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void setPreferences(final Context context,
			final String sharedPreferencesName, final String key,
			final float value) {
		final Editor editor = context.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public static void setPreferences(final Context context,
			final String sharedPreferencesName, final String key,
			final int value) {
		final Editor editor = context.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void setPreferences(final Context context,
			final String sharedPreferencesName, final String key,
			final Long value) {
		final Editor editor = context.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static void setPreferences(final Context context,
			final String sharedPreferencesName, final String key,
			final String value) {
		final Editor editor = context.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putString(key, Strings.encrypt(value, SHAREDPREFERENCESNAME));
		editor.commit();
	}

	public static boolean setPreferencesStringArray(final Context context,
			final String sharedPreferencesName, final String arrayName,
			final ArrayList<?> array) {
		final Editor editor = context.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putInt(Strings.fString(arrayName, "_size"), array.size());
		for (int i = 0; i < array.size(); i++) {
			editor.putString(Strings.fString(arrayName, "_") + i,
					(String) array.get(i));
		}
		return editor.commit();
	}

	public static boolean getBoolean(final Context context,
			final String sharedPreferencesName, final String key,
			final boolean defValue) {
		return context.getSharedPreferences(sharedPreferencesName, 0)
				.getBoolean(key, defValue);
	}

	public static float getFloat(final Context context,
			final String sharedPreferencesName, final String key,
			final float defValue) {
		return context.getSharedPreferences(sharedPreferencesName, 0).getFloat(
				key, defValue);
	}

	public static int getInt(final Context context,
			final String sharedPreferencesName, final String key,
			final int defValue) {
		return context.getSharedPreferences(sharedPreferencesName, 0).getInt(
				key, defValue);
	}

	public static Long getLong(final Context context,
			final String sharedPreferencesName, final String key,
			final Long defValue) {
		return context.getSharedPreferences(sharedPreferencesName, 0).getLong(
				key, defValue);
	}

	public static String getString(final Context context,
			final String sharedPreferencesName, final String key,
			final String defValue) {
		String result = Strings.decrypt(
				context.getSharedPreferences(sharedPreferencesName, 0)
						.getString(key, defValue), SHAREDPREFERENCESNAME);

		if (null == result) {
			result = defValue;
		}
		return result;
	}

	public static ArrayList<?> getPreferencesStringArray(final Context context,
			final String sharedPreferencesName, final String arrayName) {
		final SharedPreferences prefs = context.getSharedPreferences(
				sharedPreferencesName, 0);
		final int size = prefs.getInt(Strings.fString(arrayName, "_size"), 0);
		final ArrayList<String> array = new ArrayList<String>(size);
		for (int i = 0; i < size; i++) {
			array.add(i,
					prefs.getString(Strings.fString(arrayName, "_") + i, null));
		}
		return array;
	}
}
