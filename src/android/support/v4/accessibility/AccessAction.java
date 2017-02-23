package android.support.v4.accessibility;

import java.util.ArrayList;
import java.util.Locale;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.n.NString;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

@SuppressLint("NewApi")
public class AccessAction {
	public final AccessibilityService service;

	public AccessAction(final AccessibilityService service) {
		this.service = service;
	}

	public void sleep(final int time) {
		try {
			Thread.sleep(time);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public AccessibilityNodeInfo find(final String type, final String key, final boolean parent) {
		return finds(service.getRootInActiveWindow(), type, key, parent).get(0);
	}

	public ArrayList<AccessibilityNodeInfo> finds(final String type, final String key, final boolean parent) {
		return finds(service.getRootInActiveWindow(), type, key, parent);
	}

	public ArrayList<AccessibilityNodeInfo> finds(final AccessibilityNodeInfo nodeInfo, final String type,
			final String key, final boolean parent) {
		final ArrayList<AccessibilityNodeInfo> findInList = findToList(nodeInfo, key, type, parent,
				new ArrayList<AccessibilityNodeInfo>());

		return findInList;
	}

	public ArrayList<AccessibilityNodeInfo> findToList(final AccessibilityNodeInfo nodeInfo, final String key,
			final String className, final boolean parent, final ArrayList<AccessibilityNodeInfo> list) {
		try {
			final int loopInt = nodeInfo.getChildCount();

			for (int i = 0; i < loopInt; i++) {
				final AccessibilityNodeInfo child = nodeInfo.getChild(i);

				String space = "";
				for (int x = 0; x < i; x++) {
					space = space + "-";
				}

				Log.i(getClass().getName(), space + child.getClassName() + "_" + child.getContentDescription() + "_"
						+ child.getText() + "_" + child.getChildCount());

				if (NString.parse(child.getClassName()).toLowerCase(Locale.US)
						.contains(className.toLowerCase(Locale.US))) {
					if (key.startsWith("!")) {
						final String tmpkey = key.substring(1, key.length());
						if (NString.parse(child.getContentDescription()).toLowerCase(Locale.US)
								.equals(tmpkey.toLowerCase(Locale.US))
								|| NString.parse(child.getText()).toLowerCase(Locale.US)
										.equals(tmpkey.toLowerCase(Locale.US))) {

							Log.e("", "found!");

							if (parent) {
								list.add(nodeInfo);
							} else {
								list.add(child);
							}
						}
					} else {
						if (NString.parse(child.getContentDescription()).toLowerCase(Locale.US)
								.contains(key.toLowerCase(Locale.US))
								|| NString.parse(child.getText()).toLowerCase(Locale.US)
										.contains(key.toLowerCase(Locale.US))) {

							Log.e("", "found!");

							if (parent) {
								list.add(nodeInfo);
							} else {
								list.add(child);
							}
						}
					}
				}

				findToList(child, key, className, parent, list);
			}
		} catch (final Exception e) {
			// TODO: handle exception
		}

		return list;
	}

	public boolean on(final int action, final AccessibilityNodeInfo nodeInfo) {
		boolean result;

		// Log.e(getClass().getName(), nodeInfo.toString());
		result = nodeInfo.performAction(action);

		sleep(100);

		return result;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressWarnings("deprecation")
	public void copyToClip(final String string) {
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
			final android.text.ClipboardManager clipboard = (android.text.ClipboardManager) service
					.getSystemService(Context.CLIPBOARD_SERVICE);
			clipboard.setText(string);
		} else {
			final android.content.ClipboardManager clipboard = (android.content.ClipboardManager) service
					.getSystemService(Context.CLIPBOARD_SERVICE);
			final android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", string);
			clipboard.setPrimaryClip(clip);
		}

		sleep(100);
	}

	public void onText(final String type, final String key, final String text) {
		on(AccessibilityNodeInfo.ACTION_FOCUS, find(type, key, false));
		on(AccessibilityNodeInfo.ACTION_CLICK, find(type, key, false));
		copyToClip(text);
		on(AccessibilityNodeInfo.ACTION_PASTE, find(type, key, false));
	}

	public void startapp(final String packageName) {
		try {
			final PackageManager pm = service.getPackageManager();
			final Intent intent = pm.getLaunchIntentForPackage(packageName);
			service.startActivity(intent);

			sleep(5000);
		} catch (final Exception e) {
			Log.e("", "未安裝");
		}
	}

	public void back() {
		service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
		sleep(1000);
	}

	public static final String SCHEME = "package";

	public static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";

	public static final String APP_PKG_NAME_22 = "pkg";

	public static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";

	public static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";

	public static void showInstalledAppDetails(final Context context, final String packageName) {
		final Intent intent = new Intent();
		final int apiLevel = Build.VERSION.SDK_INT;
		if (apiLevel >= 9) { // above 2.3
			intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			final Uri uri = Uri.fromParts(SCHEME, packageName, null);
			intent.setData(uri);
		} else { // below 2.3
			final String appPkgName = apiLevel == 8 ? APP_PKG_NAME_22 : APP_PKG_NAME_21;
			intent.setAction(Intent.ACTION_VIEW);
			intent.setClassName(APP_DETAILS_PACKAGE_NAME, APP_DETAILS_CLASS_NAME);
			intent.putExtra(appPkgName, packageName);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
