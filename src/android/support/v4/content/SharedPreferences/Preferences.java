package android.support.v4.content.SharedPreferences;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.lang.Strings;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class Preferences {
	public final static String SHAREDPREFERENCESNAME = "SharedPreference";

	public final static void setPreferences(final Context baseContext,
			final String key, final boolean value) {
		setPreferences(baseContext, SHAREDPREFERENCESNAME, key, value);
	}

	public final static void setPreferences(final Context baseContext,
			final String key, final float value) {
		setPreferences(baseContext, SHAREDPREFERENCESNAME, key, value);
	}

	public final static void setPreferences(final Context baseContext,
			final String key, final int value) {
		setPreferences(baseContext, SHAREDPREFERENCESNAME, key, value);
	}

	public final static void setPreferences(final Context baseContext,
			final String key, final Long value) {
		setPreferences(baseContext, SHAREDPREFERENCESNAME, key, value);
	}

	public final static void setPreferences(final Context baseContext,
			final String key, final String value) {
		setPreferences(baseContext, SHAREDPREFERENCESNAME, key, value);
	}

	public final static boolean setPreferencesStringArray(
			final Context baseContext, final String arrayName,
			final ArrayList<?> array) {
		return setPreferencesStringArray(baseContext, SHAREDPREFERENCESNAME,
				arrayName, array);
	}

	public final static boolean getBoolean(final Context baseContext,
			final String key, final boolean defValue) {
		return getBoolean(baseContext, SHAREDPREFERENCESNAME, key, defValue);
	}

	public final static float getFloat(final Context baseContext,
			final String key, final float defValue) {
		return getFloat(baseContext, SHAREDPREFERENCESNAME, key, defValue);
	}

	public final static int getInt(final Context baseContext, final String key,
			final int defValue) {
		return getInt(baseContext, SHAREDPREFERENCESNAME, key, defValue);
	}

	public final static Long getLong(final Context baseContext,
			final String key, final Long defValue) {
		return getLong(baseContext, SHAREDPREFERENCESNAME, key, defValue);
	}

	public final static String getString(final Context baseContext,
			final String key, final String defValue) {
		return getString(baseContext, SHAREDPREFERENCESNAME, key, defValue);
	}

	public final static ArrayList<?> getPreferencesStringArray(
			final Context baseContext, final String arrayName) {
		return getPreferencesStringArray(baseContext, SHAREDPREFERENCESNAME,
				arrayName);
	}

	public final static void setPreferences(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final boolean value) {
		final Editor editor = baseContext.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public final static void setPreferences(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final float value) {
		final Editor editor = baseContext.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public final static void setPreferences(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final int value) {
		final Editor editor = baseContext.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public final static void setPreferences(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final Long value) {
		final Editor editor = baseContext.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public final static void setPreferences(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final String value) {
		final Editor editor = baseContext.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putString(key, Strings.encrypt(value, SHAREDPREFERENCESNAME));
		editor.commit();
	}

	public final static boolean setPreferencesStringArray(
			final Context baseContext, final String sharedPreferencesName,
			final String arrayName, final ArrayList<?> array) {
		final Editor editor = baseContext.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putInt(Strings.fString(arrayName, "_size"), array.size());
		for (int i = 0; i < array.size(); i++) {
			editor.putString(Strings.fString(arrayName, "_") + i,
					(String) array.get(i));
		}
		return editor.commit();
	}

	public final static boolean getBoolean(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final boolean defValue) {
		return baseContext.getSharedPreferences(sharedPreferencesName, 0)
				.getBoolean(key, defValue);
	}

	public final static float getFloat(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final float defValue) {
		return baseContext.getSharedPreferences(sharedPreferencesName, 0)
				.getFloat(key, defValue);
	}

	public final static int getInt(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final int defValue) {
		return baseContext.getSharedPreferences(sharedPreferencesName, 0)
				.getInt(key, defValue);
	}

	public final static Long getLong(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final Long defValue) {
		return baseContext.getSharedPreferences(sharedPreferencesName, 0)
				.getLong(key, defValue);
	}

	public final static String getString(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final String defValue) {
		String result = defValue;

		result = Strings.decrypt(
				baseContext.getSharedPreferences(sharedPreferencesName, 0)
						.getString(key, defValue), SHAREDPREFERENCESNAME);

		return result;
	}

	public final static ArrayList<?> getPreferencesStringArray(
			final Context baseContext, final String sharedPreferencesName,
			final String arrayName) {
		final SharedPreferences prefs = baseContext.getSharedPreferences(
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
