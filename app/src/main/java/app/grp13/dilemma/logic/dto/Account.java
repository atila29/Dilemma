package app.grp13.dilemma.logic.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by champen on 22-11-2015.
 */
public class Account {

    private String userName;
    private String password;
    private int type;

    private boolean active;
    private int id;

    private List<IDilemma> myDilemmas;
    private List<IReply> myReplys;

    public Account(String userName, String password, int type, int id) {
        this.active = true;
        this.userName = userName;
        this.password = password;
        this.type = type;

        myDilemmas = new ArrayList<>();
        myReplys = new ArrayList<>();

       this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<IDilemma> getMyDilemmas() {
        return myDilemmas;
    }

    public void setMyDilemmas(List<IDilemma> myDilemmas) {
        this.myDilemmas = myDilemmas;
    }

    public List<IReply> getMyReplys() {
        return myReplys;
    }

    public void setMyReplys(List<IReply> myReplys) {
        this.myReplys = myReplys;
    }



    public int getId() {
        return id;
    }

}
