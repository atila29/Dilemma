package app.grp13.dilemma.logic.controller;

import android.util.Log;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.Serializable;
import java.util.Map;

import app.grp13.dilemma.MainActivity;
import app.grp13.dilemma.application.ApplicationState;
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
    private Authenticater auth;


    private IAccountControllerActivity activity;

    public AccountController( IAccountControllerActivity activity){
        this.activity = activity;
        this.accountDAO = new AccountDAO();
        this.auth = new Authenticater();
    }


    public void saveAccount(Account account) {
        accountDAO.saveAccount(account, account.getId());
    }

    public void createAccount(String username, String password, Runnable run) throws DAOException {
        auth.createUser(username, password, run);

    }

    public void changeUserPass(String mail, String oldPass, String newPass, Runnable r) {
        auth.changeUserPassword(mail, oldPass, newPass, r);
    }


    public void login(String username, String password) throws LoginException {
        auth.login(username, password);
    }

    public void changeUserMail(String oldMail, String newMail, String password) {
        auth.changeUserMail(oldMail,newMail, password);
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


        //runnable i metoden er cowboyderløsning for at sørge for at brugeren ikke findes i forvejen, og i hvilket tilfælde den gældne aktivitet ikke lukkes.
        public void createUser(String mail, String password, final Runnable run){

            setTempString(mail); // for at spare en variabel, måske lidt for grimt?
            firebase.createUser(mail, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> stringObjectMap) {
                    Log.v("FH", (String) stringObjectMap.get("uid"));
                    setId((String) stringObjectMap.get("uid"));
                    accountDAO.saveAccount(new Account(tempString, Account.USER, id), id);
                    run.run();
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

                    Realm realm = Realm.getInstance(ApplicationState.getAppContext());
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

        public void changeUserMail(String oldMail, final String newMail, String password) {
            Log.v("FH2", newMail);
            Log.v("FH2", oldMail);
            Log.v("FH2", password);
            firebase.changeEmail(oldMail, password, newMail, new Firebase.ResultHandler() {
                @Override
                public void onSuccess() {
                    activity.showLoginToast(newMail);

                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    activity.ShowErrorMessage(new LoginException(firebaseError.getMessage()));
                }
            });
        }

        public void changeUserPassword(String mail, String oldPass, String newPass, final Runnable r) {
            firebase.changePassword(mail, oldPass, newPass, new Firebase.ResultHandler() {
                @Override
                public void onSuccess() {
                    r.run();
                }

                @Override
                public void onError(FirebaseError firebaseError) {

                }
            });
        }


    }



}
