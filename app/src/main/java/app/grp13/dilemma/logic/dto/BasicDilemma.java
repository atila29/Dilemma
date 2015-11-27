package app.grp13.dilemma.logic.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by champen on 23-11-2015.
 */
public class BasicDilemma implements IDilemma, Parcelable {

    private List<IAnswer> answerOptions;
    private String title;
    private String description;
    private int gravity;

    private int id;
    private boolean active;

    private List<IReply> answers;

    public BasicDilemma(int id, String title, String description, int gravity, List<IAnswer> answerOptions) {
        this.answerOptions = answerOptions;
        this.title = title;
        this.description = description;
        this.gravity = gravity;
        this.id = id;

        this.active = true;
        this.answers = new ArrayList<>();
    }

    @Override
    public List<IAnswer> getPossibleAnswers() {
        return this.answerOptions;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public List<IReply> getReplys() {
        return this.answers;
    }

    @Override
    public int getgravity() {
        return this.gravity;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void setPossibleAnswers(List<IAnswer> possibleAnswers) {
        this.answerOptions = possibleAnswers;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void addReply(IReply reply) {
        this.answers.add(reply);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.answerOptions);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeInt(this.gravity);
        dest.writeInt(this.id);
        dest.writeByte(active ? (byte) 1 : (byte) 0);
        dest.writeList(this.answers);
    }

    protected BasicDilemma(Parcel in) {
        this.answerOptions = new ArrayList<IAnswer>();
        in.readList(this.answerOptions, List.class.getClassLoader());
        this.title = in.readString();
        this.description = in.readString();
        this.gravity = in.readInt();
        this.id = in.readInt();
        this.active = in.readByte() != 0;
        this.answers = new ArrayList<IReply>();
        in.readList(this.answers, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<BasicDilemma> CREATOR = new Parcelable.Creator<BasicDilemma>() {
        public BasicDilemma createFromParcel(Parcel source) {
            return new BasicDilemma(source);
        }

        public BasicDilemma[] newArray(int size) {
            return new BasicDilemma[size];
        }
    };
}
