package app.grp13.dilemma.logic.dao;

import android.util.Log;
import android.widget.MultiAutoCompleteTextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.security.token.TokenGenerator;

import java.util.HashMap;
import java.util.Map;

import app.grp13.dilemma.logic.controller.AccountController;
import app.grp13.dilemma.logic.dto.Account;
import app.grp13.dilemma.logic.exceptions.DAOException;

/**
 * Created by champen on 05-01-2016.
 */
public class AccountDAO {

    private Firebase firebase;



    public AccountDAO(){
        firebase = new Firebase("https://dtu-dilemma.firebaseio.com/");

    }




    public Account getAccount(String uid) throws DAOException{
        throw new DAOException("FUNCTION MISSING");
    }





    public void saveAccount(Account account, String uid) {
        String path = uid.replace("-","");
        Log.v("SHHSH", path);
        account.setId(uid);
        Firebase ref = firebase.child("accounts").child(path);
        ref.setValue(account);
    }



}
