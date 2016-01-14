package app.grp13.dilemma.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.firebase.client.Firebase;

import app.grp13.dilemma.logic.controller.AccountController;
import app.grp13.dilemma.logic.controller.DilemmaController;
import app.grp13.dilemma.logic.controller.IAccountControllerActivity;
import app.grp13.dilemma.logic.exceptions.DAOException;

/**
 * Created by champen on 11-01-2016.
 */
public class ApplicationState extends Application{
    private static ApplicationState ourInstance;
    private static Context appContext;
    private AccountController accountController;
    private DilemmaController dilemmaController;
    public static final String PREF_NAME = "DIL_PREFS";


    @Override
    public void onCreate(){
        super.onCreate();
        ourInstance = this;
        appContext = getApplicationContext();
        Firebase.setAndroidContext(getApplicationContext());
        dilemmaController = new DilemmaController();
    }

    public void setAccountActivityFocus(IAccountControllerActivity activity) {
        this.accountController = new AccountController(activity);
    }

    public void refreshDilemmas() throws DAOException {
        this.dilemmaController = new DilemmaController(dilemmaController.getDilemmaDAO().getDilemmas());
    }

    public boolean isNetworkAvalible(){
        ConnectivityManager cm = (ConnectivityManager) ApplicationState.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni !=null && ni.isConnected();
    }

    public static ApplicationState getInstance() {
        return ourInstance;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public AccountController getAccountController() {
        return accountController;
    }

    public DilemmaController getDilemmaController() {
        return dilemmaController;
    }
}
