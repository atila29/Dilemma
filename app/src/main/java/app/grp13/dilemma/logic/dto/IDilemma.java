package app.grp13.dilemma.logic.dto;

import java.util.List;

/**
 * Created by champen on 22-11-2015.
 */
public interface IDilemma {

    List<IAnswer> getPossibleAnswers();
    String getDescription();
    List<IReply> getReplys();
    int getgravity();
    String getTitle();
    boolean isActive();
    int getID();
    void setPossibleAnswers(List<IAnswer> possibleAnswers);
    void setDescription(String description);
    void setGravity(int gravity);
    void setTitle(String title);
    void setActive(boolean active);
    void addReply(IReply reply);

    //  evt. opret owner attribute, så der er en reference til brugeren der opretter dilemma.
    //  Account getOwner();


}
