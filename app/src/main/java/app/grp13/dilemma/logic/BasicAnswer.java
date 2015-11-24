package app.grp13.dilemma.logic;

import app.grp13.dilemma.logic.IAnswer;

/**
 * Created by champen on 24-11-2015.
 */
public class BasicAnswer implements IAnswer {

    private String option;

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
