package app.grp13.dilemma.logic.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import app.grp13.dilemma.logic.dto.IReply;

/*
Lavet af:
Sazvan Kasim Ali - S144884
Mathias Petersen - S144874
Bao Duy Nguyen - S144880
Christian Jappe - S144866
Magnus Nielsen - S141899
Nicolai Hansen - S133974
*/
public class BasicReply implements IReply, Serializable{

    private String reply = new String("");
    private int id = 0;

    @Override
    public String getReply() {
        return this.reply;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }

    @Override
    public void setReply(String reply) {
        this.reply = reply;
    }

}
