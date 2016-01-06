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
    private String id;
    private String token;

    private String tempString = "";


    public AccountDAO(){
        firebase = new Firebase("https://dtu-dilemma.firebaseio.com/");
        id = "";
        token = "";
    }

    private void setId(String id) {
        this.id = id;
    }

    public void createUser(String mail, String password){

        setTempString(mail); // for at spare en variabel, måske lidt for grimt?
        firebase.createUser(mail, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                Log.v("FH", (String) stringObjectMap.get("uid"));
                setId((String) stringObjectMap.get("uid"));
                saveAccount(new Account(tempString, AccountController.USER, id));
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                setToken(firebaseError.getMessage());
            }
        });



    }

    public Account getAccount(Account account) throws DAOException{
        throw new DAOException("FUNCTION MISSING");
    }

    public boolean authenticateUser(Account account) {
        return  false;
    }

    public Account login(String mail, String password){
        firebase.authWithPassword(mail, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                setToken(authData.getToken());
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                setToken("nologin");
            }
        });

        return new Account(mail, AccountController.USER, this.token);
    }


    private void saveAccount(Account account) {
        String path = this.id.replace("-","");
        Log.v("SHHSH", path);
        Firebase ref = firebase.child("accounts").child(path);
        ref.setValue(account);
    }



    public void logout() {
        firebase.unauth();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String getTempString() {
        return tempString;
    }

    private void setTempString(String tempString) {
        this.tempString = tempString;
    }
}
