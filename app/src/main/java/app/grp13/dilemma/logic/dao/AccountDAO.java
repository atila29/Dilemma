package app.grp13.dilemma.logic.dao;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import app.grp13.dilemma.logic.dto.Account;
import app.grp13.dilemma.logic.dto.BasicReply;
import app.grp13.dilemma.logic.dto.IReply;
import app.grp13.dilemma.logic.exceptions.DAOException;

/**
 * Created by champen on 05-01-2016.
 */
public class AccountDAO {

    private Firebase firebase;
    private Account account;
    private MyValueEventListener listener;



    public AccountDAO(){
        firebase = new Firebase("https://dtu-dilemma.firebaseio.com/");
        account = null;
    }




    public void getAccount(String uid, final IAccountParser parser){
        Firebase accountref = firebase.child("accounts").child(uid.replace("-",""));
        accountref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //dataSnapshot.child("active");
                String id = (String) dataSnapshot.child("id").getValue(); // skal (måske) ikke bruges.
                int type = Integer.valueOf(dataSnapshot.child("type").getValue().toString());
                String uname = (String) dataSnapshot.child("userName").getValue();
                Account a = new Account(uname, type, id);
                for(DataSnapshot dilemma : dataSnapshot.child("myDilemmas").getChildren()) {
                    a.getMyDilemmas().add(Integer.valueOf(dilemma.getValue().toString()));
                }
                for(DataSnapshot r : dataSnapshot.child("myReplys").getChildren()) {
                    IReply reply = new BasicReply();
                    reply.setID(Integer.valueOf(r.child("id").getValue().toString()));
                    reply.setReply(r.child("reply").getValue().toString());
                    a.getMyReplys().add(reply);
                }
                parser.parseAccount(a);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }





    public void saveAccount(Account account, String uid) {
        String path = uid.replace("-","");
        Log.v("SHHSH", path);
        account.setId(uid);
        Firebase ref = firebase.child("accounts").child(path);
        ref.setValue(account);
    }


    private Account getAccount() {
        return account;
    }

    private void setAccount(Account account) {
        this.account = account;
    }

    private class MyValueEventListener implements ValueEventListener {

        Firebase accountref;

        public MyValueEventListener (String uid) {
            accountref = firebase.child("accounts").child(uid.replace("-",""));
            accountref.addValueEventListener(this);
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            //dataSnapshot.child("active");
            String id = (String) dataSnapshot.child("id").getValue(); // skal (måske) ikke bruges.
            int type = Integer.valueOf(dataSnapshot.child("type").getValue().toString());
            String uname = (String) dataSnapshot.child("userName").getValue();
            Account a = new Account(uname, type, id);
            for(DataSnapshot dilemma : dataSnapshot.child("myDilemmas").getChildren()) {
                a.getMyDilemmas().add(Integer.valueOf(dilemma.getValue().toString()));
            }
            account = a;
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }
}
