package android.support.v4.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.support.v4.lang.It;
import android.support.v4.lang.NString;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class Dates {
    public static final String ISO_INSTANT = "yyyy-MM-dd'T'HH:mm:SS.sss'Z'";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "HH:mm:ss";
    public static final String FORMAT_TIME_NOS = "HH:mm";
    public static final String FORMAT_DATETIME = NString.parse(FORMAT_DATE, NString.SPACE, FORMAT_TIME);

    private Dates() {
    }

    private static Date myTime;

    public static final void setTime(final Date date) {
	myTime = date;
    }

    public static final Calendar getInstance() {
	final Calendar calendar = Calendar.getInstance();

	if (!It.isNull(myTime)) {
	    calendar.setTime(myTime);
	}

	return calendar;
    }

    public static final String toString(final String formFormat, final String toFormat, final String date) {
	return toString(formFormat, new SimpleDateFormat(toFormat, Locale.US), date, -8);
    }

    public static final String toString(final String formFormat, final DateFormat toFormat, final String date) {
	return toString(formFormat, toFormat, date, -8);
    }

    public static final String toString(final String formFormat, final DateFormat toFormat, final String date,
	    final int offset) {
	final Calendar calendar = getInstance();
	try {
	    calendar.setTime(new SimpleDateFormat(formFormat, Locale.US).parse(date));
	} catch (final ParseException e) {
	    e.printStackTrace();
	}
	calendar.add(Calendar.HOUR_OF_DAY, offset);

	return toFormat.format(calendar.getTime());
    }

    public static final Date toDate(final String formFormat, final String date) {
	return toDate(new SimpleDateFormat(formFormat, Locale.US), date, -8);
    }

    public static final Date toDate(final DateFormat formFormat, final String date, final int offset) {
	final Calendar calendar = getInstance();
	try {
	    calendar.setTime(formFormat.parse(date));
	} catch (final ParseException e) {
	    e.printStackTrace();
	}
	calendar.add(Calendar.HOUR_OF_DAY, offset);

	return calendar.getTime();
    }

    public static final String getDate(final DateFormat formFormat, final Date date) {
	return getDate(formFormat, date, -8);
    }

    public static final String getDate(final String formFormat, final Date date) {
	return getDate(new SimpleDateFormat(formFormat, Locale.US), date, -8);
    }

    public static final String getDate(final String formFormat, final Date date, final int offset) {
	return getDate(new SimpleDateFormat(formFormat, Locale.US), date, offset);
    }

    public static final String getDate(final DateFormat formFormat, final Date date, final int offset) {
	final Calendar calendar = getInstance();
	calendar.setTime(date);
	calendar.add(Calendar.HOUR_OF_DAY, offset);

	return formFormat.format(calendar.getTime());
    }

    public static final boolean isToday(final Date date) {
	return isToday(date, 0);
    }

    public static final boolean isToday(final Date date, final int allowance) {
	final Calendar calendar = getInstance();
	calendar.add(Calendar.HOUR, allowance);

	return isSameDay(date, calendar.getTime());
    }

    public static final boolean isSameDay(final Date date1, final Date date2) {
	boolean result = false;
	if (null != date1 && null != date2) {
	    final Calendar calendar1 = getInstance();
	    calendar1.setTime(date1);
	    final Calendar calendar2 = getInstance();
	    calendar2.setTime(date2);
	    result = calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA)
		    && calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
		    && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);
	}
	return result;
    }

    public static final String now() {
	final Calendar calendar = getInstance();
	return new SimpleDateFormat(FORMAT_DATETIME, Locale.US).format(calendar.getTime());
    }

    public static final int nowHour() {
	final Calendar calendar = getInstance();
	return calendar.get(Calendar.HOUR_OF_DAY);
    }
}