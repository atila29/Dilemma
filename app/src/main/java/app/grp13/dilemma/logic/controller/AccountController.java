package app.grp13.dilemma.logic.controller;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.Serializable;
import java.util.Map;

import app.grp13.dilemma.MainActivity;
import app.grp13.dilemma.logic.dao.AccountDAO;
import app.grp13.dilemma.logic.dao.IAccountParser;
import app.grp13.dilemma.logic.dto.Account;
import app.grp13.dilemma.logic.dto.RealmToken;
import app.grp13.dilemma.logic.exceptions.DAOException;
import app.grp13.dilemma.logic.exceptions.LoginException;
import io.realm.Realm;
import io.realm.RealmQuery;

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


    public void saveAccount(Account account) {
        accountDAO.saveAccount(account, account.getId());
    }

    public void createAccount(String username, String password) throws DAOException {
        auth.createUser(username, password);

    }


    public void login(String username, String password) throws LoginException {
        auth.login(username, password);
    }

    public void authenticate() throws LoginException {
        auth.authenticateUser();
    }

    public void deleteAccount() throws Exception{
        // WIP
    }

    public void logout() {
        auth.logout();
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



        public void login(String mail, String password) throws LoginException{
            firebase.authWithPassword(mail, password, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    /*try {
                        accountDAO.getAccount(authData.getUid());
                    } catch (DAOException e) {
                        activity.ShowErrorMessage(e);
                    }*/
                    Log.v("FH", authData.getProvider());
                    activity.showLoginToast("Du er nu logget ind");

                    Realm realm = Realm.getInstance(MainActivity.getContext());
                    RealmQuery<RealmToken> q = realm.where(RealmToken.class);
                    realm.beginTransaction();
                    if (q.findAll().size() > 0)
                        q.findAll().clear();
                    RealmToken t = realm.createObject(RealmToken.class);
                    t.setToken(authData.getToken());
                    Log.v("TOK", authData.getToken());
                    realm.copyToRealm(t);
                    realm.commitTransaction();
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    activity.ShowErrorMessage(new LoginException(firebaseError.getMessage()));
                }
            });

        }

        public void logout() {
            firebase.unauth();
        }

        public void authenticateUser() throws LoginException{

            if(firebase.getAuth() == null)
                throw new LoginException("Bruger ikke logget ind.");

            accountDAO.getAccount(firebase.getAuth().getUid(), new IAccountParser() {
                @Override
                public void parseAccount(Account account) {
                    activity.accountAuthentication(account);
                }
            });
            /*firebase.authWithOAuthToken("password", firebase.getAuth().getToken(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    accountDAO.getAccount(authData.getUid(), new IAccountParser() {
                        @Override
                        public void parseAccount(Account account) {
                            activity.accountAuthentication(account);
                        }
                    });
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    activity.ShowErrorMessage(new Exception(firebaseError.getMessage()));
                }
            });*/
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
