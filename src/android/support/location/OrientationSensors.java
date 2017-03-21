package android.support.location;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public class OrientationSensors implements SensorEventListener {
	private final SensorManager sensorManager;
	private final SensorsCallback sensorsCallback;

	public OrientationSensors(final Context context, final SensorsCallback sensorsCallback) {
		this.sensorsCallback = sensorsCallback;
		sensorManager = (SensorManager) context.getSystemService("sensor");

		sensorManager.registerListener(this, sensorManager.getDefaultSensor(3), 0);
	}

	public final void onDestory() {
		sensorManager.unregisterListener(this);
	}

	@Override
	public void onSensorChanged(final SensorEvent event) {
		if (event.sensor.getType() == 3) {
			final float x = event.values[0];
			final float y = event.values[1];
			final float z = event.values[2];

			sensorsCallback.onSensorChanged(x, y, z);
		}
	}

	@Override
	public void onAccuracyChanged(final Sensor sensor, final int accuracy) {
	}

	public static abstract interface SensorsCallback {
		public abstract void onSensorChanged(float paramFloat1, float paramFloat2, float paramFloat3);
	}
}
