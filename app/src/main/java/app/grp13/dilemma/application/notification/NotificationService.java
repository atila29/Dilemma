package app.grp13.dilemma.application.notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.grp13.dilemma.LoginActivity;
import app.grp13.dilemma.R;
import app.grp13.dilemma.application.ApplicationState;

/**
 * Created on 12-01-2016.
 * source:
 * http://stackoverflow.com/questions/20501225/using-service-to-run-background-and-create-notification
 *
 */
public class NotificationService extends IntentService {

    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";
    private static Firebase firebase;
    private static String acc;
    private static List<Integer> idListe;

    private PowerManager.WakeLock wakeLock;

    public NotificationService(){
        super(NotificationService.class.getSimpleName());
        idListe = new ArrayList<>();
        acc = ApplicationState.getAppContext().getSharedPreferences(ApplicationState.PREF_NAME, Context.MODE_PRIVATE).getString("NOT",null);
        firebase = new Firebase("https://dtu-dilemma.firebaseio.com/");
        Firebase userref = firebase.child("accounts").child(acc.replace("-", "")).child("myReplys");
        userref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idListe.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    idListe.add(Integer.valueOf(d.child("id").getValue().toString()));
                }
                for (Integer i : idListe) {
                    firebase.child("dilemmas").child(i.toString()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            //showDilemmaAnsweredNotification();
                            Log.v("NOT", "herder");
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public static Intent createIntentStartNotificationService(Context context) {

        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try{
            String action = intent.getAction();
            if(ACTION_START.equals(action))
                processStartNotification(intent);
            if(ACTION_DELETE.equals(action))
                processDeleteNotification(intent);
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void processDeleteNotification(Intent intent) {
        // Log something?
    }



    private void processStartNotification(Intent intent) {
        // hent data fra firebase

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Dilemma Titel")
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.colorPrimaryDark))
                .setContentText("dette virker faktisk wow det er satme vildt. hold da helt ferie, nu må du gerne ryge en smøg.")
                .setSmallIcon(R.drawable.ic_notification);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, new Intent(this, LoginActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(NotificationReceiver.getDeleteIntent(this));

        final NotificationManager manager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    private void showDilemmaAnsweredNotification(){
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Dilemma besvaret")
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.colorPrimaryDark))
                .setContentText("nogen har besvaret dit dilemma")
                .setSmallIcon(R.drawable.ic_notification);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, new Intent(this, LoginActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(NotificationReceiver.getDeleteIntent(this));

        final NotificationManager manager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }


}
