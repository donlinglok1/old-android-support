/*
 * Copyright (c) 2014 Kenneth Tu <don.ling.lok@gmail.com>
 *
 * All rights reserved. No warranty, explicit or implicit, provided.
 *
 * @author Kenneth Tu
 * @version 1.0.0
 */
package android.support.app;

import java.io.IOException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

public class Notifications {
	public static final int SOUND_CHANNEL_SYSTEM = 0;
	public static final int SOUND_CHANNEL_ALERT = 1;
	public static final int SOUND_CHANNEL_MEDIA = 2;

	public static final Notification get(final Context context, final String title, final String content,
			final int smallIcon) {
		return get(context, title, content, smallIcon, null, SOUND_CHANNEL_SYSTEM, null);
	}

	public static final Notification get(final Context context, final String title, final String content,
			final int smallIcon, final Uri sound, final int soundChannel) {
		return get(context, title, content, smallIcon, sound, soundChannel, null);
	}

	public static final Notification get(final Context context, final String title, final String content,
			final int smallIcon, final Uri sound, final int soundChannel, final PendingIntent pendingIntent) {
		final Notification notification = new NotificationCompat.Builder(context).setSmallIcon(smallIcon)
				.setWhen(System.currentTimeMillis()).setContentTitle(title).setContentText(content).setAutoCancel(true)
				.build();

		notification.defaults |= Notification.DEFAULT_VIBRATE;
		if (null == sound) {
			notification.defaults |= Notification.DEFAULT_SOUND;
		} else {
			if (soundChannel == SOUND_CHANNEL_SYSTEM) {
				notification.sound = sound;
			} else if (soundChannel == SOUND_CHANNEL_ALERT) {
				final Ringtone r = RingtoneManager.getRingtone(context, sound);
				r.play();
			} else if (soundChannel == SOUND_CHANNEL_MEDIA) {
				final MediaPlayer player = new MediaPlayer();
				try {
					player.setDataSource(context, sound);
					player.prepare();
					player.start();
				} catch (final IOException e) {
				}
			}
		}

		// if (null == pendingIntent) {
		// notification.setLatestEventInfo(context, title, content,
		// PendingIntent.getActivity(context, 0,
		// context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()),
		// PendingIntent.FLAG_CANCEL_CURRENT));
		// } else {
		// notification.setLatestEventInfo(context, title, content,
		// pendingIntent);
		// }

		return notification;
	}

	public static final void removeAll(final Context context) {
		final NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancelAll();
	}

	public static final void remove(final Context context, final int notiId) {
		final NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(notiId);
	}
}
