package android.support.v4.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.SharedPreferences.Preferences;
import android.support.v4.lang.It;
import android.support.v4.lang.NString;
import android.support.v4.lang.Strings;
import android.util.Log;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class Tools {
    public static final String loadFile(final File file) {
	String json = null;
	try {
	    final InputStream is = new FileInputStream(file);
	    final int size = is.available();
	    final byte[] buffer = new byte[size];
	    is.read(buffer);
	    is.close();
	    json = new String(buffer, "UTF-8");
	} catch (final IOException exception) {
	    Tools.exceptionToJSONObject(exception);
	    return null;
	}
	return json;
    }

    public static JSONObject exceptionToJSONObject(final Exception exception) {
	final StringWriter sWriter = new StringWriter();
	// exception.printStackTrace(new PrintWriter(sWriter));
	final JSONObject temp = new JSONObject();
	temp.put("Exception", NString.parse(sWriter));

	// System.out.println(NString.parse(sWriter));

	Log.e("", temp.toJSONString());
	return temp;
    }

    public static JSONArray joinJSONArray(final JSONArray firstArray, final JSONArray secArray) {
	final StringBuilder buffer = new StringBuilder();

	int len = firstArray.size();
	for (int i = 0; i < len; i++) {
	    final JSONObject obj1 = (JSONObject) firstArray.get(i);
	    if (i == len - 1) {
		buffer.append(NString.parse(obj1));
	    } else {
		buffer.append(NString.parse(obj1)).append(Strings.COMMA);
	    }
	}
	len = secArray.size();
	if (len > 0) {
	    buffer.append(Strings.COMMA);
	}
	for (int i = 0; i < len; i++) {
	    final JSONObject obj1 = (JSONObject) secArray.get(i);
	    if (i == len - 1) {
		buffer.append(NString.parse(obj1));
	    } else {
		buffer.append(NString.parse(obj1)).append(Strings.COMMA);
	    }
	}
	buffer.insert(0, Character.valueOf('[')).append(Character.valueOf(']'));
	return (JSONArray) JSONValue.parse(NString.parse(buffer));
    }

    public static final boolean isInstalled(final Context context, final String packageName) {
	Boolean result = false;
	try {
	    context.getPackageManager().getApplicationInfo(packageName, 0);
	    result = true;
	} catch (final NameNotFoundException e) {
	}
	return result;
    }

    public static final void setBadge(final Context context, final int count) {
	final String launcherClassName = getLauncherClassName(context);
	if (null == launcherClassName) {
	    return;
	}

	Preferences.set(context, "BADGE_COUNT_UPDATE", count);

	final Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
	intent.putExtra("badge_count", count);
	intent.putExtra("badge_count_package_name", context.getPackageName());
	intent.putExtra("badge_count_class_name", launcherClassName);
	context.sendBroadcast(intent);
    }

    public static final boolean isOnline(final Context context) {
	final ConnectivityManager connectivityManager = (ConnectivityManager) context
		.getSystemService(Context.CONNECTIVITY_SERVICE);
	final NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
	boolean result = false;
	if (null != netInfo && netInfo.isConnected()) {
	    result = true;
	}
	return result;
    }

    public static final void checkMemory() {
	System.out.println("maxMemory" + Runtime.getRuntime().maxMemory() / 1024);

	System.out.println("freeMemory" + Runtime.getRuntime().freeMemory() / 1024);

	System.out.println("totalMemory" + Runtime.getRuntime().totalMemory() / 1024);
    }

    public static final double addDouble(final Number value1, final Number value2) {
	final BigDecimal bigDecimal1 = new BigDecimal(Double.toString(value1.doubleValue()));
	final BigDecimal bigDecimal2 = new BigDecimal(Double.toString(value2.doubleValue()));
	return bigDecimal1.add(bigDecimal2).doubleValue();
    }

    public static final double subDouble(final Number value1, final Number value2) {
	final BigDecimal bigDecimal1 = new BigDecimal(Double.toString(value1.doubleValue()));
	final BigDecimal bigDecimal2 = new BigDecimal(Double.toString(value2.doubleValue()));
	return bigDecimal1.subtract(bigDecimal2).doubleValue();
    }

    private static final double RATIO_LIMIT = 1.0;

    public static final double calculateBeaconAccuracy(final int txPower, final double rssi) {
	double result = -1.0;
	if (0 != rssi) {
	    final double ratio = rssi * 1.0 / txPower;
	    if (ratio < RATIO_LIMIT) {
		result = Math.pow(ratio, 10);
	    } else {
		result = 0.89976 * Math.pow(ratio, 7.7095) + 0.111;
	    }
	}
	return result;
    }

    private static final double EARTH_RADIUS = 6378137.0;

    public static final double latlng2m(final Double lat_a, final Double lng_a, final Double lat_b,
	    final Double lng_b) {

	final double radLat1 = lat_a * Math.PI / 180.0;
	final double radLat2 = lat_b * Math.PI / 180.0;
	final double step1 = radLat1 - radLat2;
	final double step2 = (lng_a - lng_b) * Math.PI / 180.0;
	return Math.round(2
		* Math.asin(Math.sqrt(Math.pow(Math.sin(step1 / 2), 2)
			+ Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(step2 / 2), 2)))
		* EARTH_RADIUS * 10000) / 10000;
    }

    private static String capitalize(final String string) {
	String result = null;
	if (!It.isNull(string)) {
	    final char first = string.charAt(0);
	    if (Character.isUpperCase(first)) {
		result = string;
	    } else {
		result = Character.toUpperCase(first) + string.substring(1);
	    }
	}
	return result;
    }

    public static final String getDeviceName() {
	final String manufacturer = Build.MANUFACTURER;
	final String model = Build.MODEL;
	String result;
	if (model.startsWith(manufacturer)) {
	    result = capitalize(model);
	} else {
	    result = capitalize(manufacturer) + " " + model;
	}
	return result;
    }

    public static final String getLauncherClassName(final Context context) {
	String result = null;
	final PackageManager packageManager = context.getPackageManager();

	final Intent intent = new Intent(Intent.ACTION_MAIN);
	intent.addCategory(Intent.CATEGORY_LAUNCHER);

	final List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
	for (int i = 0; i < resolveInfos.size(); i++) {
	    final String pkgName = resolveInfos.get(i).activityInfo.applicationInfo.packageName;
	    if (pkgName.equalsIgnoreCase(context.getPackageName())) {
		result = resolveInfos.get(i).activityInfo.name;
	    }
	}
	return result;
    }

}
