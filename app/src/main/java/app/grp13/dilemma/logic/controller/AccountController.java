package app.grp13.dilemma.logic.controller;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.grp13.dilemma.logic.dto.Account;
import app.grp13.dilemma.logic.exceptions.DilemmaException;
import app.grp13.dilemma.logic.exceptions.LoginException;

/**
 * Created by champen on 22-11-2015.
 */
public class AccountController {
    private final String FILENAME = "users.dil";


    private Map<Integer, Account> accounts;

    public static int ADMIN = 16;
    public static int USER = 32;

    public AccountController(){
        accounts = new HashMap<>();
    }

    public AccountController(List<Account> accounts) {

        this.accounts = new HashMap<>();

        for(Account a : accounts) {
            this.accounts.put(a.getId(), a);
        }
    }


    public void createAccount(String username, String password, int type) throws LoginException {

        for( Account a : accounts.values()) {
            if(username.equals(a.getUserName()))
                throw new LoginException("Username not available");
        }

        Integer key = 0;
        do {
            key = (int)(Math.random()*(2^16));
        } while(accounts.containsKey(key));

        accounts.put(key, new Account(username, password, type, key));
    }

    public Account login(String username, String password) throws LoginException {
        for(Account a : accounts.values()) {
            if(username.equals(a.getUserName()))
                if(password.equals(a.getPassword()))
                    return a;
        }
        throw new LoginException("username or password was not correct");
    }

    public void deleteAccount(int id) throws Exception{
        if(!accounts.containsKey(id))
            throw new DilemmaException("user not found");
        accounts.remove(id);
    }

    public Account getAccount(int id) throws Exception{
        if(!accounts.containsKey(id))
            throw new DilemmaException("user not found");

        return accounts.get(id);
    }



    public List<Account> getAllAccounts() {
        List<Account> temp = new ArrayList<>();
        temp.addAll(accounts.values());
        return temp;
    }

    public void saveUsersToDevice(Context ctx) throws IOException {

        FileOutputStream fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        objectOutputStream.writeObject(this.getAllAccounts());
        fos.close();
        objectOutputStream.close();
    }

    public void loadUsersFromDevice(Context ctx) throws IOException, ClassNotFoundException {
        FileInputStream inputStream = new FileInputStream(FILENAME);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        ArrayList<Account> list = (ArrayList<Account>)objectInputStream.readObject();

        this.accounts = new HashMap<>();

        for(Account a : list) {
            this.accounts.put(a.getId(), a);
        }
    }
}
