package app.grp13.dilemma.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by champen on 12-01-2016.
 *
 */
public class NotificaationServiceStarterReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationReceiver.setupAlarm(context);
    }
}
