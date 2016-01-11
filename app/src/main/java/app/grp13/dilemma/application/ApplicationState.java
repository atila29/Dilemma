package app.grp13.dilemma.application;

import android.app.Application;
import android.content.Context;

import app.grp13.dilemma.logic.controller.AccountController;
import app.grp13.dilemma.logic.controller.IAccountControllerActivity;

/**
 * Created by champen on 11-01-2016.
 */
public class ApplicationState extends Application{
    private static ApplicationState ourInstance;
    private static Context appContext;
    private AccountController accountController;


    @Override
    public void onCreate(){
        super.onCreate();
        ourInstance = this;
        appContext = getApplicationContext();
    }

    public void setAccountActivityFocus(IAccountControllerActivity activity) {
        this.accountController = new AccountController(activity);
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
}
