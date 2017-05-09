package android.support.accessibility;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.n.NString;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

@TargetApi(30)
public class AccessAction {
	public final AccessibilityService service;

	public AccessAction(final AccessibilityService service) {
		this.service = service;
	}

	public List<AccessibilityNodeInfo> finds(final String className, final String keyword) {
		return findsOn(service.getRootInActiveWindow(), className, keyword);
	}

	public List<AccessibilityNodeInfo> findsOn(final AccessibilityNodeInfo nodeInfo, final String className,
			final String keyword) {
		return findToList(nodeInfo, className, keyword, new ArrayList<AccessibilityNodeInfo>());
	}

	private List<AccessibilityNodeInfo> findToList(final AccessibilityNodeInfo nodeInfo, final String className,
			final String keyword, final List<AccessibilityNodeInfo> list) {
		final int loopInt = nodeInfo.getChildCount();
		for (int i = 0; i < loopInt; i++) {
			final AccessibilityNodeInfo child = nodeInfo.getChild(i);

			String space = "o";
			for (int x = 0; x < i; x++) {
				space = NString.add(space, "-");
			}

			Log.i(getClass().getName(),
					NString.add(space, "\n getClassName ", child.getClassName(),
							//
							"\n getContentDescription ", child.getContentDescription(),
							//
							"\n getText ", child.getText(),
							//
							"\n getChildCount ", child.getChildCount(),
							//
							"\n getActionList ", child.getActionList(),
							// "\n getClass ", child.getClass(),
							// "\n getCollectionInfo ",
							// child.getCollectionInfo(),
							// "\n getCollectionItemInfo ",
							// child.getCollectionItemInfo(),
							// "\n getDrawingOrder ", child.getDrawingOrder(),
							// "\n getError ", child.getError(),
							// "\n getExtras ", child.getExtras(),
							// "\n getInputType ", child.getInputType(),
							// "\n getLabeledBy ", child.getLabeledBy(),
							// "\n getLabelFor ", child.getLabelFor(),
							// "\n getLiveRegion ", child.getLiveRegion(),
							// "\n getMaxTextLength ", child.getMaxTextLength(),
							// "\n getMovementGranularities ",
							// child.getMovementGranularities(),
							// "\n getPackageName ", child.getPackageName(),
							// "\n getRangeInfo ", child.getRangeInfo(),
							// "\n getText ", child.getText(),
							// "\n getTextSelectionEnd ",
							// child.getTextSelectionEnd(),
							// "\n getTextSelectionStart ",
							// child.getTextSelectionStart(),
							// "\n getTraversalAfter ",
							// child.getTraversalAfter(),
							// "\n getTraversalBefore ",
							// child.getTraversalBefore(),
							// "\n getViewIdResourceName ",
							// child.getViewIdResourceName(),
							// "\n getWindow ", child.getWindow(),
							// "\n getWindowId ", child.getWindowId(),
							"\n", child, ""));

			if (checkItem(child, className, keyword)) {
				list.add(child);
			}

			findToList(child, className, keyword, list);
		}

		return list;
	}

	private boolean checkItem(final AccessibilityNodeInfo child, final String className, final String key) {
		if (!NString.parse(child.getClassName()).contains(className)) {
			return false;
		}

		if (key.startsWith("!")) {
			final String tmpkey = key.substring(1, key.length());
			if (NString.parse(child.getContentDescription()).equalsIgnoreCase(tmpkey)
					|| NString.parse(child.getText()).equalsIgnoreCase(tmpkey)) {
				Log.e("", "found!");
				return true;
			}
		}

		if (NString.parse(child.getContentDescription()).toLowerCase(Locale.US).contains(key.toLowerCase(Locale.US))
				|| NString.parse(child.getText()).toLowerCase(Locale.US).contains(key.toLowerCase(Locale.US))) {
			Log.e("", "found!");
			return true;
		}

		return false;
	}

	public boolean run(final int action, final AccessibilityNodeInfo nodeInfo) {
		final boolean result = nodeInfo.performAction(action);

		sleep(100);

		return result;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressWarnings("deprecation")
	private void copyToClip(final String string) {
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

	public void textOn(final String className, final String key, final String text) {
		run(AccessibilityNodeInfo.ACTION_FOCUS, finds(className, key).get(0));
		run(AccessibilityNodeInfo.ACTION_CLICK, finds(className, key).get(0));
		copyToClip(text);
		run(AccessibilityNodeInfo.ACTION_PASTE, finds(className, key).get(0));
	}

	public void sleep(final int time) {
		try {
			Thread.sleep(time);
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startapp(final String packageName) {
		final Intent intent = service.getPackageManager().getLaunchIntentForPackage(packageName);
		service.startActivity(intent);

		sleep(5000);
	}

	public void back() {
		service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);

		sleep(1000);
	}

	public void home() {
		service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);

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
