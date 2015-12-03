package app.grp13.dilemma.logic.dto;

import android.os.Parcel;
import android.os.Parcelable;

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
public class BasicDilemma implements IDilemma, Serializable {

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

}
