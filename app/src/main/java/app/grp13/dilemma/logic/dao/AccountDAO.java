package app.grp13.dilemma.logic.dao;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import app.grp13.dilemma.logic.dto.Account;
import app.grp13.dilemma.logic.exceptions.DAOException;

/**
 * Created by champen on 05-01-2016.
 */
public class AccountDAO {

    private Firebase firebase;
    private Account account;



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
                parser.parseAccount(new Account(uname, type, id));
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
}
