package app.grp13.dilemma.logic.controller;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.Serializable;
import java.util.Map;

import app.grp13.dilemma.logic.dao.AccountDAO;
import app.grp13.dilemma.logic.dto.Account;
import app.grp13.dilemma.logic.exceptions.DAOException;
import app.grp13.dilemma.logic.exceptions.LoginException;

/*
Lavet af:
Sazvan Kasim Ali - S144884
Mathias Petersen - S144874
Bao Duy Nguyen - S144880
Christian Jappe - S144866
Magnus Nielsen - S141899
Nicolai Hansen - S133974
*/
public class AccountController implements Serializable{

    private AccountDAO accountDAO;
    private Account account;
    private Authenticater auth;

    public static  int GUEST = 8;
    public static int ADMIN = 16;
    public static int USER = 32;

    private IAccountControllerActivity activity;

    public AccountController( IAccountControllerActivity activity){
        this.activity = activity;
        this.accountDAO = new AccountDAO();
        this.auth = new Authenticater();
    }




    public void createAccount(String username, String password) throws DAOException {
        auth.createUser(username, password);

    }


    public void login(String username, String password){
        auth.login(username,password);
    }

    public void deleteAccount() throws Exception{
        // WIP
    }


    private class Authenticater {

        private Firebase firebase;
        private String token;
        private String tempString;
        private String id;

        public Authenticater() {
            firebase = new Firebase("https://dtu-dilemma.firebaseio.com/");
        }

        public void createUser(String mail, String password){

            setTempString(mail); // for at spare en variabel, måske lidt for grimt?
            firebase.createUser(mail, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> stringObjectMap) {
                    Log.v("FH", (String) stringObjectMap.get("uid"));
                    setId((String) stringObjectMap.get("uid"));
                    accountDAO.saveAccount(new Account(tempString, AccountController.USER, id), id);
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    activity.ShowErrorMessage(new DAOException(firebaseError.getMessage()));
                }

            });

        }

        public void login(String mail, String password){
            firebase.authWithPassword(mail, password, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    setToken(authData.getToken());
                    Log.v("FH", authData.getProvider());
                    activity.showLoginToast("Du er nu logget ind");
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    activity.ShowErrorMessage(new LoginException(firebaseError.getMessage()));
                }
            });

            Account acc = new Account(mail, AccountController.USER, this.token);
            // mangler at få data ind.

            account = acc;
        }

        public void logout() {
            firebase.unauth();
        }

        public void authenticateUser(Account account) {
            firebase.authWithOAuthToken("password", account.getId(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    activity.showLoginToast("0");
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    activity.showLoginToast("1");
                }
            });
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setTempString(String tempString) {
            this.tempString = tempString;
        }

        public void setId(String id) {
            this.id = id;
        }
    }



}
