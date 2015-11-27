package app.grp13.dilemma.logic.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by champen on 24-11-2015.
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
