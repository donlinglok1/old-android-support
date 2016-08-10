package android.support.v4.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.lang.Base64s;
import android.support.v4.lang.NString;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
/**
 *
 * @author kennetht
 *
 */
public class Preferences {
    public static final String NAME = "SharedPreference";

    public static final String DEVICEHEIGHT = "bRezEqe8";
    public static final String DEVICEWIDTH = "dat5xemE";
    public static final String NEGATIVEBUTTON = "wAVa7ewA";
    public static final String USER = "rUQu4uth";
    public static final String POSITIVEBUTTON = "maYabRu8";
    public static final String RESULT = "result";
    public static final String STATUS = "status";
    public static final String VALUE = "ChaKetr4";

    /**
     *
     * @param baseContext
     * @param key
     * @param value
     */
    public static final void set(final Context baseContext, final String key, final boolean value) {
	set(baseContext, NAME, key, value);
    }

    /**
     *
     * @param baseContext
     * @param key
     * @param value
     */
    public static final void set(final Context baseContext, final String key, final float value) {
	set(baseContext, NAME, key, value);
    }

    /**
     *
     * @param baseContext
     * @param key
     * @param value
     */
    public static final void set(final Context baseContext, final String key, final int value) {
	set(baseContext, NAME, key, value);
    }

    /**
     *
     * @param baseContext
     * @param key
     * @param value
     */
    public static final void set(final Context baseContext, final String key, final Long value) {
	set(baseContext, NAME, key, value);
    }

    /**
     *
     * @param baseContext
     * @param key
     * @param value
     */
    public static final void set(final Context baseContext, final String key, final String value) {
	set(baseContext, NAME, key, value);
    }

    /**
     *
     * @param baseContext
     * @param arrayName
     * @param array
     * @return
     */
    public static final boolean setArray(final Context baseContext, final String arrayName, final List<?> array) {
	return setArray(baseContext, NAME, arrayName, array);
    }

    /**
     *
     * @param baseContext
     * @param key
     * @param defValue
     * @return
     */
    public static final boolean get(final Context baseContext, final String key, final boolean defValue) {
	return get(baseContext, NAME, key, defValue);
    }

    /**
     *
     * @param baseContext
     * @param key
     * @param defValue
     * @return
     */
    public static final float get(final Context baseContext, final String key, final float defValue) {
	return get(baseContext, NAME, key, defValue);
    }

    /**
     *
     * @param baseContext
     * @param key
     * @param defValue
     * @return
     */
    public static final int get(final Context baseContext, final String key, final int defValue) {
	return get(baseContext, NAME, key, defValue);
    }

    /**
     *
     * @param baseContext
     * @param key
     * @param defValue
     * @return
     */
    public static final Long get(final Context baseContext, final String key, final Long defValue) {
	return get(baseContext, NAME, key, defValue);
    }

    /**
     *
     * @param baseContext
     * @param key
     * @param defValue
     * @return
     */
    public static final String get(final Context baseContext, final String key, final String defValue) {
	return get(baseContext, NAME, key, defValue);
    }

    /**
     *
     * @param baseContext
     * @param key
     * @return
     */
    public static final List<?> getArray(final Context baseContext, final String key) {
	return getArray(baseContext, NAME, key);
    }

    /**
     *
     * @param baseContext
     * @param name
     * @param key
     * @param value
     */
    public static final void set(final Context baseContext, final String name, final String key, final boolean value) {
	final Editor editor = baseContext.getSharedPreferences(name, 0).edit();
	editor.putBoolean(key, value);
	editor.commit();
    }

    /**
     *
     * @param baseContext
     * @param name
     * @param key
     * @param value
     */
    public static final void set(final Context baseContext, final String name, final String key, final float value) {
	final Editor editor = baseContext.getSharedPreferences(name, 0).edit();
	editor.putFloat(key, value);
	editor.commit();
    }

    /**
     *
     * @param baseContext
     * @param name
     * @param key
     * @param value
     */
    public static final void set(final Context baseContext, final String name, final String key, final int value) {
	final Editor editor = baseContext.getSharedPreferences(name, 0).edit();
	editor.putInt(key, value);
	editor.commit();
    }

    /**
     *
     * @param baseContext
     * @param name
     * @param key
     * @param value
     */
    public static final void set(final Context baseContext, final String name, final String key, final Long value) {
	final Editor editor = baseContext.getSharedPreferences(name, 0).edit();
	editor.putLong(key, value);
	editor.commit();
    }

    /**
     *
     * @param baseContext
     * @param name
     * @param key
     * @param value
     */
    public static final void set(final Context baseContext, final String name, final String key, final String value) {
	final Editor editor = baseContext.getSharedPreferences(name, 0).edit();
	editor.putString(key, Base64s.encrypt(value, NAME));
	editor.commit();
    }

    /**
     *
     * @param baseContext
     * @param name
     * @param key
     * @param array
     * @return
     */
    public static final boolean setArray(final Context baseContext, final String name, final String key,
	    final List<?> array) {
	final Editor editor = baseContext.getSharedPreferences(name, 0).edit();
	editor.putInt(NString.parse(key, "_size"), array.size());
	for (int i = 0; i < array.size(); i++) {
	    editor.putString(NString.parse(key, "_") + i, (String) array.get(i));
	}
	return editor.commit();
    }

    /**
     *
     * @param baseContext
     * @param name
     * @param key
     * @param defValue
     * @return
     */
    public static final boolean get(final Context baseContext, final String name, final String key,
	    final boolean defValue) {
	return baseContext.getSharedPreferences(name, 0).getBoolean(key, defValue);
    }

    /**
     *
     * @param baseContext
     * @param name
     * @param key
     * @param defValue
     * @return
     */
    public static final float get(final Context baseContext, final String name, final String key,
	    final float defValue) {
	return baseContext.getSharedPreferences(name, 0).getFloat(key, defValue);
    }

    /**
     *
     * @param baseContext
     * @param name
     * @param key
     * @param defValue
     * @return
     */
    public static final int get(final Context baseContext, final String name, final String key, final int defValue) {
	return baseContext.getSharedPreferences(name, 0).getInt(key, defValue);
    }

    /**
     *
     * @param baseContext
     * @param name
     * @param key
     * @param defValue
     * @return
     */
    public static final Long get(final Context baseContext, final String name, final String key, final Long defValue) {
	return baseContext.getSharedPreferences(name, 0).getLong(key, defValue);
    }

    /**
     *
     * @param baseContext
     * @param name
     * @param key
     * @param defValue
     * @return
     */
    public static final String get(final Context baseContext, final String name, final String key,
	    final String defValue) {
	return Base64s.decrypt(baseContext.getSharedPreferences(name, 0).getString(key, defValue), NAME);
    }

    /**
     *
     * @param baseContext
     * @param name
     * @param key
     * @return
     */
    public static final List<?> getArray(final Context baseContext, final String name, final String key) {
	final SharedPreferences prefs = baseContext.getSharedPreferences(name, 0);
	final int size = prefs.getInt(NString.parse(key, "_size"), 0);
	final ArrayList<String> array = new ArrayList<String>(size);
	for (int i = 0; i < size; i++) {
	    array.add(i, prefs.getString(NString.parse(key, "_") + i, null));
	}
	return array;
    }
}
