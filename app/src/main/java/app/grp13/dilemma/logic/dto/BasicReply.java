package app.grp13.dilemma.logic.dto;

import android.os.Parcel;
import android.os.Parcelable;

import app.grp13.dilemma.logic.dto.IReply;

/**
 * Created by champen on 24-11-2015.
 */
public class BasicReply implements IReply, Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.reply);
        dest.writeInt(this.id);
    }

    public BasicReply() {
    }

    protected BasicReply(Parcel in) {
        this.reply = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<BasicReply> CREATOR = new Parcelable.Creator<BasicReply>() {
        public BasicReply createFromParcel(Parcel source) {
            return new BasicReply(source);
        }

        public BasicReply[] newArray(int size) {
            return new BasicReply[size];
        }
    };
}
