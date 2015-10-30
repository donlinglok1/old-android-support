/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.support.v4.util;

import android.os.Build;
import android.os.Build.VERSION_CODES;

/**
 * Class containing some static utility methods.
 */
public class Versions {
	public Versions() {
	};

	// @TargetApi(VERSION_CODES.HONEYCOMB)
	// public final static void enableStrictMode(final Class<?> cla) {
	// if (Utils.hasGingerbread()) {
	// final StrictMode.ThreadPolicy.Builder threadPolicyBuilder = new
	// StrictMode.ThreadPolicy.Builder()
	// .detectAll().penaltyLog();
	// final StrictMode.VmPolicy.Builder vmPolicyBuilder = new
	// StrictMode.VmPolicy.Builder()
	// .detectAll().penaltyLog();
	//
	// if (Utils.hasHoneycomb()) {
	// threadPolicyBuilder.penaltyFlashScreen();
	// vmPolicyBuilder.setClassInstanceLimit(cla, 1);
	// }
	// StrictMode.setThreadPolicy(threadPolicyBuilder.build());
	// StrictMode.setVmPolicy(vmPolicyBuilder.build());
	// }
	// }

	public final static boolean hasFroyo() {
		// Can use final static constants like FROYO, declared in later versions
		// of the OS since they are inlined at compile time. This is guaranteed
		// behavior.
		return Build.VERSION.SDK_INT >= VERSION_CODES.FROYO;
	}

	public final static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD;
	}

	public final static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
	}

	public final static boolean hasHoneycombMR1() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
	}

	public final static boolean hasJellyBean() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
	}

	public final static boolean hasKitKat() {
		return Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT;
	}
}
