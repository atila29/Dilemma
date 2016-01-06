package app.grp13.dilemma.logic.controller;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.grp13.dilemma.logic.dao.AccountDAO;
import app.grp13.dilemma.logic.dto.Account;
import app.grp13.dilemma.logic.exceptions.DilemmaException;
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

    public static  int GUEST = 8;
    public static int ADMIN = 16;
    public static int USER = 32;

    public AccountController(){

        this.accountDAO = new AccountDAO();
    }




    public void createAccount(String username, String password) throws LoginException {
        accountDAO.createUser(username, password);
        Log.v("GHF", accountDAO.getToken());
        if(accountDAO.getToken().contains("email address is already in use"))
            throw new LoginException(accountDAO.getToken());

    }


    public Account login(String username, String password) throws LoginException {
        return accountDAO.login(username, password);
    }

    public void deleteAccount() throws Exception{
        // WIP
    }





}
