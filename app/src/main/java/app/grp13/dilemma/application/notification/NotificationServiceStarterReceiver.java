package app.grp13.dilemma.application.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by champen on 12-01-2016.
 *
 */
public class NotificationServiceStarterReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationReceiver.setupAlarm(context);
    }
}