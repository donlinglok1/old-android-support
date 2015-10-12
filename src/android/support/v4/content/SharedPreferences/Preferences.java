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

	public final static void set(final Context baseContext, final String key,
			final boolean value) {
		set(baseContext, SHAREDPREFERENCESNAME, key, value);
	}

	public final static void set(final Context baseContext, final String key,
			final float value) {
		set(baseContext, SHAREDPREFERENCESNAME, key, value);
	}

	public final static void set(final Context baseContext, final String key,
			final int value) {
		set(baseContext, SHAREDPREFERENCESNAME, key, value);
	}

	public final static void set(final Context baseContext, final String key,
			final Long value) {
		set(baseContext, SHAREDPREFERENCESNAME, key, value);
	}

	public final static void set(final Context baseContext, final String key,
			final String value) {
		set(baseContext, SHAREDPREFERENCESNAME, key, value);
	}

	public final static boolean setArray(final Context baseContext,
			final String arrayName, final ArrayList<?> array) {
		return setArray(baseContext, SHAREDPREFERENCESNAME, arrayName, array);
	}

	public final static boolean get(final Context baseContext, final String key,
			final boolean defValue) {
		return get(baseContext, SHAREDPREFERENCESNAME, key, defValue);
	}

	public final static float get(final Context baseContext, final String key,
			final float defValue) {
		return get(baseContext, SHAREDPREFERENCESNAME, key, defValue);
	}

	public final static int get(final Context baseContext, final String key,
			final int defValue) {
		return get(baseContext, SHAREDPREFERENCESNAME, key, defValue);
	}

	public final static Long get(final Context baseContext, final String key,
			final Long defValue) {
		return get(baseContext, SHAREDPREFERENCESNAME, key, defValue);
	}

	public final static String get(final Context baseContext, final String key,
			final String defValue) {
		return get(baseContext, SHAREDPREFERENCESNAME, key, defValue);
	}

	public final static ArrayList<?> getArray(final Context baseContext,
			final String key) {
		return getArray(baseContext, SHAREDPREFERENCESNAME, key);
	}

	public final static void set(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final boolean value) {
		final Editor editor = baseContext.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public final static void set(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final float value) {
		final Editor editor = baseContext.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public final static void set(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final int value) {
		final Editor editor = baseContext.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public final static void set(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final Long value) {
		final Editor editor = baseContext.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public final static void set(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final String value) {
		final Editor editor = baseContext.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putString(key, Strings.encrypt(value, SHAREDPREFERENCESNAME));
		editor.commit();
	}

	public final static boolean setArray(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final ArrayList<?> array) {
		final Editor editor = baseContext.getSharedPreferences(
				sharedPreferencesName, 0).edit();
		editor.putInt(Strings.fString(key, "_size"), array.size());
		for (int i = 0; i < array.size(); i++) {
			editor.putString(Strings.fString(key, "_") + i,
					(String) array.get(i));
		}
		return editor.commit();
	}

	public final static boolean get(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final boolean defValue) {
		return baseContext.getSharedPreferences(sharedPreferencesName, 0)
				.getBoolean(key, defValue);
	}

	public final static float get(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final float defValue) {
		return baseContext.getSharedPreferences(sharedPreferencesName, 0)
				.getFloat(key, defValue);
	}

	public final static int get(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final int defValue) {
		return baseContext.getSharedPreferences(sharedPreferencesName, 0)
				.getInt(key, defValue);
	}

	public final static Long get(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final Long defValue) {
		return baseContext.getSharedPreferences(sharedPreferencesName, 0)
				.getLong(key, defValue);
	}

	public final static String get(final Context baseContext,
			final String sharedPreferencesName, final String key,
			final String defValue) {
		String result = defValue;

		result = Strings.decrypt(
				baseContext.getSharedPreferences(sharedPreferencesName, 0)
						.getString(key, defValue), SHAREDPREFERENCESNAME);

		return result;
	}

	public final static ArrayList<?> getArray(final Context baseContext,
			final String sharedPreferencesName, final String key) {
		final SharedPreferences prefs = baseContext.getSharedPreferences(
				sharedPreferencesName, 0);
		final int size = prefs.getInt(Strings.fString(key, "_size"), 0);
		final ArrayList<String> array = new ArrayList<String>(size);
		for (int i = 0; i < size; i++) {
			array.add(i, prefs.getString(Strings.fString(key, "_") + i, null));
		}
		return array;
	}
}
