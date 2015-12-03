package app.grp13.dilemma.logic.controller;

import android.content.Context;
import android.os.Environment;

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
    private final String FILENAME = "users.bin";


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

        AccountStorage storage = new AccountStorage();
        storage.setList(this.getAllAccounts());

        File file = new File(ctx.getFilesDir() + FILENAME);
        if(!file.exists())
            file.createNewFile();

        FileOutputStream fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        objectOutputStream.writeObject(storage);
        fos.flush();
        objectOutputStream.flush();
        fos.close();
    }

    public void loadUsersFromDevice(Context ctx) throws IOException, ClassNotFoundException {

        FileInputStream inputStream = ctx.openFileInput(FILENAME);

        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        AccountStorage temp = (AccountStorage)objectInputStream.readObject();

        inputStream.close();

        this.accounts = new HashMap<>();

        for(Account a : temp.getList()) {
            this.accounts.put(a.getId(), a);
        }
    }

    private class AccountStorage implements Serializable{
        private List<Account> list = new ArrayList<>();

        public List<Account> getList() {
            return list;
        }

        public void setList(List<Account> list) {
            this.list = list;
        }
    }
}
