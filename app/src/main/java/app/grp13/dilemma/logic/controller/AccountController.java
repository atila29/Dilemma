package app.grp13.dilemma.logic.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.grp13.dilemma.logic.dto.Account;
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

    public List<Account> getAllAccounts() {
        List<Account> temp = new ArrayList<>();
        temp.addAll(accounts.values());
        return temp;
    }
}
