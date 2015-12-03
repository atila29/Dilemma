package app.grp13.dilemma.logic.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/*
Lavet af:
Sazvan Kasim Ali - S144884
Mathias Petersen - S144874
Bao Duy Nguyen - S144880
Christian Jappe - S144866
Magnus Nielsen - S141899
Nicolai Hansen - S133974
*/
public class BasicAnswer implements IAnswer, Serializable{

    private String option = "";

    public BasicAnswer(String option){
        this.option = option;
    }

    @Override
    public String getAnswer() {
        return this.option;
    }

    @Override
    public int getID() {
        return 0;
    }



}
