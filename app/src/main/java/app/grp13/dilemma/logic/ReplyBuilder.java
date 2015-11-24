package app.grp13.dilemma.logic;

import java.util.Map;

import app.grp13.dilemma.logic.exceptions.DilemmaException;

/**
 * Created by champen on 22-11-2015.
 */
public class ReplyBuilder {

    Map<IAnswer, IReply> map;

    public ReplyBuilder(Map<IAnswer,IReply> map) {
        this.map = map;

    }

    public IReply createReply(IAnswer answerOption, String reply, int id) throws IllegalAccessException, InstantiationException, DilemmaException {
        IReply temp = null;

        for(IAnswer a : map.keySet()){
            if(answerOption.getClass().equals(a.getClass())){
                temp = this.map.get(a).getClass().newInstance();
                temp.setReply(reply);
                temp.setID(id);
            }
        }
        if(temp == null)
            throw new DilemmaException("Reply kunne ikke findes");
        return temp;
    }

}
