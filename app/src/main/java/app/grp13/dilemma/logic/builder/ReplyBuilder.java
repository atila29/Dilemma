package app.grp13.dilemma.logic.builder;

import java.io.Serializable;
import java.util.Map;

import app.grp13.dilemma.logic.dto.IAnswer;
import app.grp13.dilemma.logic.dto.IReply;
import app.grp13.dilemma.logic.exceptions.DilemmaException;

/*
Lavet af:
Sazvan Kasim Ali - S144884
Mathias Petersen - S144874
Bao Duy Nguyen - S144880
Christian Jappe - S144866
Magnus Nielsen - S141899
Nicolai Hansen - S133974
*/
public class ReplyBuilder implements Serializable {

    Map<IAnswer, IReply> map;

    public ReplyBuilder(Map<IAnswer,IReply> map) {
        this.map = map;

    }

    public IReply createReply(IAnswer answerOption, int id) throws IllegalAccessException, InstantiationException, DilemmaException {
        IReply temp = null;

        for(IAnswer a : map.keySet()){
            if(answerOption.getClass().equals(a.getClass())){
                temp = this.map.get(a).getClass().newInstance();
                temp.setReply(answerOption.getAnswer());
                temp.setID(id);
            }
        }
        if(temp == null)
            throw new DilemmaException("Reply kunne ikke findes");
        return temp;
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
