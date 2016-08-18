package android.n;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.support.v4.lang.It;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class NDate {
    public static final String ISO_INSTANT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "HH:mm:ss";
    public static final String FORMAT_TIME_NOS = "HH:mm";
    public static final String FORMAT_DATETIME = NString.add(FORMAT_DATE, NString.SPACE, FORMAT_TIME);

    private static Date myTime;

    public static final void setDate(final Date date) {
	myTime = date;
    }

    public static final Date getDate() {
	return getInstance().getTime();
    }

    public static final Calendar getInstance() {
	final Calendar calendar = Calendar.getInstance();

	if (!It.isNull(myTime)) {
	    calendar.setTime(myTime);
	}

	return calendar;
    }

    public static final String toString(final String formFormat, final DateFormat toFormat, final String date,
	    final String offset) {
	final Calendar calendar = getInstance();
	try {
	    calendar.setTime(new SimpleDateFormat(formFormat, Locale.US).parse(date));
	} catch (final ParseException e) {
	    e.printStackTrace();
	}
	toFormat.setTimeZone(TimeZone.getTimeZone("GMT" + offset));

	return toFormat.format(calendar.getTime());
    }

    public static final Date parse(final DateFormat formFormat, final String date, final int offset) {
	final Calendar calendar = getInstance();
	try {
	    calendar.setTime(formFormat.parse(date));
	} catch (final ParseException e) {
	    e.printStackTrace();
	}
	calendar.add(Calendar.HOUR_OF_DAY, offset);

	return calendar.getTime();
    }

    public static final String toString(final String formFormat, final Date date, final int offset) {
	return toString(new SimpleDateFormat(formFormat, Locale.US), date, offset);
    }

    public static final String toString(final DateFormat formFormat, final Date date, final int offset) {
	final Calendar calendar = getInstance();
	calendar.setTime(date);
	calendar.add(Calendar.HOUR_OF_DAY, offset);

	return formFormat.format(calendar.getTime());
    }
}