package android.support.v4.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.support.v4.security.Strings;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public final class Dates {
	public final static String FORMAT_DATE = Strings.fString(Strings.LOWY,
			Strings.LOWY, Strings.LOWY, Strings.LOWY, Strings.MINUS,
			Strings.UPPM, Strings.UPPM, Strings.MINUS, Strings.LOWD,
			Strings.LOWD);
	public final static String FORMAT_TIME = Strings.fString(Strings.UPPH,
			Strings.UPPH, Strings.COLON, Strings.LOWM, Strings.LOWM,
			Strings.COLON, Strings.LOWS, Strings.LOWS);
	public final static String FORMAT_TIME_NOS = Strings.fString(Strings.UPPH,
			Strings.UPPH, Strings.COLON, Strings.LOWM, Strings.LOWM);
	public final static String FORMAT_DATETIME = Strings.fString(FORMAT_DATE,
			Strings.SPACE, FORMAT_TIME);

	private static Date myTime;

	private Dates() {
	}

	public static void setTime(final Date date) {
		myTime = new Date();
		myTime.setTime(date.getTime());
	}

	public static Calendar getInstance() {
		final Calendar calendar = Calendar.getInstance();
		if (null != myTime) {
			calendar.setTime(myTime);
		}
		return calendar;
	}

	public static boolean isToday(final Date date) {
		return isToday(date, 0);
	}

	public static boolean isToday(final Date date, final int allowance) {
		final Calendar calendar = getInstance();
		calendar.add(Calendar.HOUR, allowance);
		return isSameDay(date, calendar.getTime());
	}

	public static boolean isSameDay(final Date date1, final Date date2) {
		boolean result = false;
		if (null != date1 && null != date2) {
			final Calendar calendar1 = getInstance();
			calendar1.setTime(date1);
			final Calendar calendar2 = getInstance();
			calendar2.setTime(date2);
			result = calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA)
					&& calendar1.get(Calendar.YEAR) == calendar2
							.get(Calendar.YEAR)
					&& calendar1.get(Calendar.DAY_OF_YEAR) == calendar2
							.get(Calendar.DAY_OF_YEAR);
		}
		return result;
	}

	public static String format(final String formFormat, final String toFormat,
			final String date) throws ParseException {
		final Calendar cal = getInstance();
		cal.setTime(new SimpleDateFormat(formFormat, Locale.US).parse(date));
		return new SimpleDateFormat(toFormat, Locale.US).format(cal.getTime());
	}

	public static String format(final DateFormat formFormat,
			final String toFormat, final String date) throws ParseException {
		final Calendar cal = getInstance();
		cal.setTime(formFormat.parse(date));
		return new SimpleDateFormat(toFormat, Locale.US).format(cal.getTime());
	}

	public static String format(final String formFormat,
			final DateFormat toFormat, final String date) throws ParseException {
		final Calendar cal = getInstance();
		cal.setTime(new SimpleDateFormat(formFormat, Locale.US).parse(date));
		return toFormat.format(cal.getTime());
	}

	public static String format(final DateFormat formFormat,
			final DateFormat toFormat, final String date) throws ParseException {
		final Calendar cal = getInstance();
		cal.setTime(formFormat.parse(date));
		return toFormat.format(cal.getTime());
	}

	public static Date format(final String formFormat, final String date)
			throws ParseException {
		final Calendar cal = getInstance();
		cal.setTime(new SimpleDateFormat(formFormat, Locale.US).parse(date));
		return cal.getTime();
	}

	public static String format(final String formFormat, final Date date) {
		final Calendar calendar = getInstance();
		calendar.setTime(date);
		return new SimpleDateFormat(formFormat, Locale.US).format(calendar
				.getTime());
	}

	public static String now() {
		final Calendar calendar = getInstance();
		return new SimpleDateFormat(FORMAT_DATETIME, Locale.US).format(calendar
				.getTime());
	}

	public static int nowHour() {
		final Calendar calendar = getInstance();
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static String add(final int millisecond) {
		final Calendar calendar = getInstance();
		calendar.add(Calendar.MILLISECOND, millisecond);
		return new SimpleDateFormat(FORMAT_DATETIME, Locale.US).format(calendar
				.getTime());
	}

	public static String add(final Calendar calendar, final int millisecond) {
		calendar.add(Calendar.MILLISECOND, millisecond);
		return new SimpleDateFormat(FORMAT_DATETIME, Locale.US).format(calendar
				.getTime());
	}
}
