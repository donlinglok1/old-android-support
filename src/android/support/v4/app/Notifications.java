package android.support.v4.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;

/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
public final class Notifications {
	@SuppressWarnings("deprecation")
	public static void showNoti(final Context context, final int notiId,
			final int smallIcon, final String title, final String content,
			final Uri isSound, final boolean ongoing,
			final PendingIntent pendingIntent) {
		final NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		final Notification notification = new NotificationCompat.Builder(
				context).setSmallIcon(smallIcon)
				.setWhen(System.currentTimeMillis()).setContentTitle(title)
				.setContentText(content).setAutoCancel(true).build();

		if (null == isSound) {
			notification.defaults |= Notification.DEFAULT_SOUND;
		} else {
			notification.sound = isSound;
		}
		notification.defaults |= Notification.DEFAULT_VIBRATE;

		if (ongoing) {
			notification.flags = Notification.FLAG_ONGOING_EVENT;
		}

		if (null == pendingIntent) {
			notification.setLatestEventInfo(context, title, content,
					PendingIntent.getActivity(
							context,
							0,
							context.getPackageManager()
									.getLaunchIntentForPackage(
											context.getPackageName()),
							PendingIntent.FLAG_CANCEL_CURRENT));
		} else {
			notification.setLatestEventInfo(context, title, content,
					pendingIntent);
		}

		notificationManager.notify(notiId, notification);
	}

	/*
	 * Remove all
	 */
	public static void removeNotification(final Context context) {
		final NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancelAll();
	}

	public static void removeNotification(final Context context,
			final int notiId) {
		final NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(notiId);
	}
}
