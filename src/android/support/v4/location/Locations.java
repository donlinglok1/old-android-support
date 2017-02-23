/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
package android.support.v4.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.SharedPreferences.Preferences;

public class Locations implements LocationListener {
	public final transient  Context baseContext;

	public transient Location location;
	private transient LocationManager locationManager;
	private transient String provider;

	public Locations(final Context baseContext) {
		this.baseContext = baseContext;
	}

	public final boolean initLocation() {
		boolean result = false;
		locationManager = (LocationManager) baseContext.getSystemService("location");

		if (locationManager != null) {
			provider = "network";

			if (locationManager.isProviderEnabled("network")
					&& setLocation(locationManager.getLastKnownLocation("network"))) {
				provider = "network";
			}

			if (provider != null) {
				result = setLocation(locationManager.getLastKnownLocation(provider));

				locationManager.removeUpdates(Locations.this);
				locationManager.requestLocationUpdates(provider, 1L, 1.0F, Locations.this);
			}
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

	private transient float orientation;
	private transient float clockwise;
	private transient float updown;
	private transient OrientationSensors sensors;

	private final void initSensors() {
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

	public final float getOrientation() {
		initSensors();
		return orientation;
	}

	public final float getClockwise() {
		initSensors();
		return clockwise;
	}

	public final float getUpdown() {
		initSensors();
		return updown;
	}

	public final void onDestroy() {
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