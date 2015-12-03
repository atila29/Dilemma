package app.grp13.dilemma.logic.dto;

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
