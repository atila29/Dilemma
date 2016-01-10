package app.grp13.dilemma.logic.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
Lavet af:
Sazvan Kasim Ali - S144884
Mathias Petersen - S144874
Bao Duy Nguyen - S144880
Christian Jappe - S144866
Magnus Nielsen - S141899
Nicolai Hansen - S133974
*/
public class Account implements Serializable{

    private String userName;
    private int type;

    private boolean active;
    private String id;

    private List<Integer> myDilemmas; // lav om til relationer -> <Integer>
    private List<IReply> myReplys; // lav om til relationer -> <Integer>


    public Account(String userName, int type, String id) {
        this.active = true;
        this.userName = userName;
        this.id = id;
        this.type = type;
        myDilemmas = new ArrayList<>();
        myReplys = new ArrayList<>();
    }

    private Account() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public List<Integer> getMyDilemmas() {
        return myDilemmas;
    }

    public void setMyDilemmas(List<Integer> myDilemmas) {
        this.myDilemmas = myDilemmas;
    }

    public List<IReply> getMyReplys() {
        return myReplys;
    }

    public void setMyReplys(List<IReply> myReplys) {
        this.myReplys = myReplys;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
