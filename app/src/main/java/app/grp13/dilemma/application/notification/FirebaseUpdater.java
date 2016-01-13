package app.grp13.dilemma.application.notification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.Path;
import com.firebase.client.core.Repo;

import java.util.ArrayList;
import java.util.List;

import app.grp13.dilemma.application.ApplicationState;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by champen on 13-01-2016.
 */
public class FirebaseUpdater {
    private String acc;
    private List<Integer> idListe;
    private Firebase firebase;

    public Firebase getFirebase() {
        return  firebase;
    }

    public FirebaseUpdater(String url) {
        firebase = new Firebase(url);
        idListe = new ArrayList<>();
        firebase.setAndroidContext(ApplicationState.getAppContext());
        // lav fejlhåndtering for dette statement
        acc = ApplicationState.getAppContext().getSharedPreferences(ApplicationState.PREF_NAME, Context.MODE_PRIVATE).getString("NOT", null);
        Firebase userref = firebase.child("accounts").child(acc.replace("-", "")).child("myDilemmas");
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idListe.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    idListe.add(Integer.valueOf(d.getValue().toString()));
                    Log.v("NOT", "herder");
                }

                for(Integer i : idListe) {
                    firebase.child("dilemmas").child(i.toString()).child("replys").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Log.v("NOT", "besvaret Dilemma");
                            Intent i = new Intent("app.grp13.dilemma.NOTIFICATION");
                            ApplicationState.getAppContext().sendBroadcast(i);
                            //notifier.showDilemmaAnsweredNotification();
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

        for(Integer i : idListe) {
            firebase.child("dilemmas").child(i.toString()).child("replys").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.v("NOT", "besvaret Dilemma");
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

}
