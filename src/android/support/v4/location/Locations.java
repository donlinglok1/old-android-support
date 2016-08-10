package android.support.v4.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.SharedPreferences.Preferences;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class Locations implements LocationListener {
    public final transient Context baseContext;
    public transient Location location;
    private transient LocationManager locationManager;

    public Locations(final Context baseContext) {
	this.baseContext = baseContext;
    }

    public final boolean initLocation() {
	boolean result = false;
	try {
	    locationManager = (LocationManager) baseContext.getSystemService("location");

	    if (locationManager != null) {
		String provider = null;

		if (locationManager.isProviderEnabled("network")) {
		    if (setLocation(locationManager.getLastKnownLocation("network"))) {
			provider = "network";
		    }
		}
		if (provider != null) {
		    result = setLocation(locationManager.getLastKnownLocation(provider));

		    locationManager.removeUpdates(this);
		    locationManager.requestLocationUpdates(provider, 1L, 1.0F, this);
		}
	    }
	} catch (final Exception localException) {
	}
	return result;
    }

    public boolean setLocation(final Location location) {
	boolean result = false;
	try {
	    this.location = location;
	    result = true;
	} catch (final Exception localException) {
	}
	return result;
    }

    public double getLatitude() {
	double result = Preferences.get(baseContext, "mylat", 22.292685F);
	try {
	    result = location.getLatitude();
	    Preferences.set(baseContext, "mylat", (float) result);
	} catch (final Exception localException) {
	}
	return result;
    }

    public double getLongitude() {
	double result = Preferences.get(baseContext, "mylng", 114.18355F);
	try {
	    result = location.getLongitude();
	    Preferences.set(baseContext, "mylng", (float) result);
	} catch (final Exception localException) {
	}
	return result;
    }

    private transient OrientationSensors sensors;

    private void initSensors() {
	if (sensors == null) {
	    sensors = new OrientationSensors(baseContext, new OrientationSensors.SensorsCallback() {
		@Override
		public void onSensorChanged(final float x, final float y, final float z) {
		    orientation = x;
		    clockwise = y;
		    updown = z;
		}

	    });
	}
    }

    private transient float orientation;
    private transient float clockwise;
    private transient float updown;

    public float getOrientation() {
	initSensors();
	return orientation;
    }

    public float getClockwise() {
	initSensors();
	return clockwise;
    }

    public float getUpdown() {
	initSensors();
	return updown;
    }

    public void onDestroy() {
	if (locationManager != null) {
	    locationManager.removeUpdates(this);
	}
    }

    @Override
    public void onLocationChanged(final Location location) {
	setLocation(location);
    }

    @Override
    public void onProviderDisabled(final String provider) {
    }

    @Override
    public void onProviderEnabled(final String provider) {
    }

    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) {
    }
}