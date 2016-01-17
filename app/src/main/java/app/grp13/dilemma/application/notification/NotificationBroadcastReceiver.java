package app.grp13.dilemma.application.notification;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by champen on 13-01-2016.
 */
public class NotificationBroadcastReceiver extends WakefulBroadcastReceiver {

    public NotificationBroadcastReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(this.getClass().getSimpleName(), "Custom broadcast received");
        Intent serviceIntent = NotificationService.createIntentShowNotification(context);
        serviceIntent.putExtra("id", intent.getIntExtra("id",-1));
        startWakefulService(context,serviceIntent);
    }
}
