package android.support.v4.util;

import java.math.BigDecimal;
import java.util.List;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.SharedPreferences.Preferences;
import android.support.v4.lang.Strings;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public final class Tools {
	private Tools() {
	}

	public static void setBadge(final Context context, final int count) {
		final String launcherClassName = getLauncherClassName(context);
		if (null == launcherClassName) {
			return;
		}

		Preferences.setPreferences(context, "BADGE_COUNT_UPDATE", count);

		final Intent intent = new Intent(
				"android.intent.action.BADGE_COUNT_UPDATE");
		intent.putExtra("badge_count", count);
		intent.putExtra("badge_count_package_name", context.getPackageName());
		intent.putExtra("badge_count_class_name", launcherClassName);
		context.sendBroadcast(intent);
	}

	public static boolean isOnline(final Context context) {
		final ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
		boolean result = false;
		if (null != netInfo && netInfo.isConnected()) {
			result = true;
		}
		return result;
	}

	public static void checkMemory() {
		System.out.println("maxMemory" + Runtime.getRuntime().maxMemory()
				/ 1024);

		System.out.println("freeMemory" + Runtime.getRuntime().freeMemory()
				/ 1024);

		System.out.println("totalMemory" + Runtime.getRuntime().totalMemory()
				/ 1024);
	}

	public static JSONArray joinJSONArray(final JSONArray firstArray,
			final JSONArray secArray) {
		final StringBuffer buffer = new StringBuffer();

		int len = firstArray.size();
		for (int i = 0; i < len; i++) {
			final JSONObject obj1 = (JSONObject) firstArray.get(i);
			if (i == len - 1) {
				buffer.append(Strings.valueOf(obj1));
			} else {
				buffer.append(Strings.valueOf(obj1)).append(Strings.COMMA);
			}
		}
		len = secArray.size();
		if (len > 0) {
			buffer.append(Strings.COMMA);
		}
		for (int i = 0; i < len; i++) {
			final JSONObject obj1 = (JSONObject) secArray.get(i);
			if (i == len - 1) {
				buffer.append(Strings.valueOf(obj1));
			} else {
				buffer.append(Strings.valueOf(obj1)).append(Strings.COMMA);
			}
		}
		buffer.insert(0, Character.valueOf('[')).append(Character.valueOf(']'));
		return (JSONArray) JSONValue.parse(Strings.valueOf(buffer));

	}

	public static double addDouble(final Number value1, final Number value2) {
		final BigDecimal bigDecimal1 = new BigDecimal(Double.toString(value1
				.doubleValue()));
		final BigDecimal bigDecimal2 = new BigDecimal(Double.toString(value2
				.doubleValue()));
		return bigDecimal1.add(bigDecimal2).doubleValue();
	}

	public static double subDouble(final Number value1, final Number value2) {
		final BigDecimal bigDecimal1 = new BigDecimal(Double.toString(value1
				.doubleValue()));
		final BigDecimal bigDecimal2 = new BigDecimal(Double.toString(value2
				.doubleValue()));
		return bigDecimal1.subtract(bigDecimal2).doubleValue();
	}

	private final static double RATIO_LIMIT = 1.0;

	public static double calculateBeaconAccuracy(final int txPower,
			final double rssi) {
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

	private final static double EARTH_RADIUS = 6378137.0;

	public static double latlng2m(final Double lat_a, final Double lng_a,
			final Double lat_b, final Double lng_b) {

		final double radLat1 = lat_a * Math.PI / 180.0;
		final double radLat2 = lat_b * Math.PI / 180.0;
		final double step1 = radLat1 - radLat2;
		final double step2 = (lng_a - lng_b) * Math.PI / 180.0;
		return Math.round(2
				* Math.asin(Math.sqrt(Math.pow(Math.sin(step1 / 2), 2)
						+ Math.cos(radLat1) * Math.cos(radLat2)
						* Math.pow(Math.sin(step2 / 2), 2))) * EARTH_RADIUS
				* 10000) / 10000;
	}

	private static String capitalize(final String string) {
		String result = null;
		if (!Strings.isNull(string)) {
			final char first = string.charAt(0);
			if (Character.isUpperCase(first)) {
				result = string;
			} else {
				result = Character.toUpperCase(first) + string.substring(1);
			}
		}
		return result;
	}

	public static String getDeviceName() {
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

	public static String getLauncherClassName(final Context context) {
		String result = null;
		final PackageManager packageManager = context.getPackageManager();

		final Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);

		final List<ResolveInfo> resolveInfos = packageManager
				.queryIntentActivities(intent, 0);
		for (int i = 0; i < resolveInfos.size(); i++) {
			final String pkgName = resolveInfos.get(i).activityInfo.applicationInfo.packageName;
			if (pkgName.equalsIgnoreCase(context.getPackageName())) {
				result = resolveInfos.get(i).activityInfo.name;
			}
		}
		return result;
	}

}
