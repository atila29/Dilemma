package app.grp13.dilemma.logic.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by champen on 24-11-2015.
 */
public class BasicAnswer implements IAnswer, Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.option);
    }

    protected BasicAnswer(Parcel in) {
        this.option = in.readString();
    }

    public static final Parcelable.Creator<BasicAnswer> CREATOR = new Parcelable.Creator<BasicAnswer>() {
        public BasicAnswer createFromParcel(Parcel source) {
            return new BasicAnswer(source);
        }

        public BasicAnswer[] newArray(int size) {
            return new BasicAnswer[size];
        }
    };
}
