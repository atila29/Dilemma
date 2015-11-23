package app.grp13.dilemma.logic;

import java.util.List;

/**
 * Created by champen on 23-11-2015.
 */
public class BasicDilemma implements IDilemma {




    @Override
    public List<IAnswer> getPossibleAnswers() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public List<IReply> getReplys() {
        return null;
    }

    @Override
    public int getgravity() {
        return 0;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setPossibleAnswers(List<IAnswer> possibleAnswers) {

    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public void setGravity(int gravity) {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setActive(boolean active) {

    }
}
