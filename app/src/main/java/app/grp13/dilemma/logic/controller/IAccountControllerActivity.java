package app.grp13.dilemma.logic.controller;

import app.grp13.dilemma.logic.dto.Account;

/**
 * Created by champen on 07-01-2016.
 */
public interface IAccountControllerActivity {

    void ShowErrorMessage(Exception e);
    void showLoginToast(String msg);
    void accountAuthentication(Account acc);
}
