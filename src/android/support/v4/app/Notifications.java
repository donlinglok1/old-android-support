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
@SuppressWarnings("deprecation")
public class Notifications {
	public final static Notification show(final Context context,
			final String title, final String content, final int smallIcon,
			final Uri sound, final boolean ongoing, final int notiId) {
		final PendingIntent contentIntent = PendingIntent.getActivity(
				context,
				0,
				context.getPackageManager().getLaunchIntentForPackage(
						context.getPackageName()),
				PendingIntent.FLAG_CANCEL_CURRENT);

		return show(context, title, content, smallIcon, sound, ongoing, notiId,
				contentIntent);
	}

	public final static Notification show(final Context context,
			final String title, final String content, final int smallIcon,
			final Uri sound, final boolean ongoing, final int notiId,
			final PendingIntent pendingIntent) {
		final NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		final Notification notification = new NotificationCompat.Builder(
				context).setSmallIcon(smallIcon)
				.setWhen(System.currentTimeMillis()).setContentTitle(title)
				.setContentText(content).setAutoCancel(true).build();

		if (null == sound) {
			notification.defaults |= Notification.DEFAULT_SOUND;
		} else {
			notification.sound = sound;
		}
		notification.defaults |= Notification.DEFAULT_VIBRATE;

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

		if (ongoing) {
			notification.flags = Notification.FLAG_ONGOING_EVENT;
		} else {
			notificationManager.notify(notiId, notification);
		}

		return notification;
	}

	public final static void removeAll(final Context context) {
		final NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancelAll();
	}

	public final static void remove(final Context context, final int notiId) {
		final NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(notiId);
	}
}
