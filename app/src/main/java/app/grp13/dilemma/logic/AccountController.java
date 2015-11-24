package app.grp13.dilemma.logic;

import android.accounts.AccountsException;

import java.util.HashMap;
import java.util.Map;

import app.grp13.dilemma.logic.exceptions.DilemmaException;

/**
 * Created by champen on 22-11-2015.
 */
public class AccountController {

    private Map<Integer, Account> accounts;


    public AccountController(){
        accounts = new HashMap<>();
    }


    public void createAccount(String username, String password, int type) {
        Integer key = 0;
        do {
            key = (int)(Math.random()*(2^16));
        } while(accounts.containsKey(key));

        accounts.put(key, new Account(username, password, type, key));
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
}
