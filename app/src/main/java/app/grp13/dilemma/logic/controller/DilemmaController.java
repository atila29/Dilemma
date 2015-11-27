package app.grp13.dilemma.logic.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.grp13.dilemma.logic.builder.DilemmaBuilder;
import app.grp13.dilemma.logic.builder.ReplyBuilder;
import app.grp13.dilemma.logic.dto.BasicAnswer;
import app.grp13.dilemma.logic.dto.BasicReply;
import app.grp13.dilemma.logic.dto.IAnswer;
import app.grp13.dilemma.logic.dto.IDilemma;
import app.grp13.dilemma.logic.dto.IReply;
import app.grp13.dilemma.logic.exceptions.DilemmaException;

/**
 * Created by champen on 24-11-2015.
 */
public class DilemmaController {

    private ReplyBuilder replyBuilder;
    private DilemmaBuilder dilemmaBuilder;

    private Map<Integer,IDilemma> dilemmaMap;
    //private Map<IAnswer,IReply> answerReplyMap;

    public DilemmaController() {

        dilemmaMap = new HashMap<Integer, IDilemma>();

        Map<IAnswer, IReply> a2r = new HashMap<>();
        a2r.put(new BasicAnswer(""), new BasicReply());
        replyBuilder = new ReplyBuilder(a2r);

        dilemmaBuilder = new DilemmaBuilder();

    }

    // ikke en synderlig fleksibel måde at lave dilemmaer, men virker (nok!)
    public void createDilemma(String title, String description, int gravity, String ... answerOptions) {

        // id skal være unikt, skal derfor laves om.
        Integer key = 0;
        do {
            key = (int)(Math.random()*(2^16));
        } while(dilemmaMap.containsKey(key));

        List<IAnswer> tempAnswerOptions = new ArrayList<>();
        for(String s : answerOptions) {
            tempAnswerOptions.add(new BasicAnswer(s));
        }

        this.dilemmaMap.put(key, dilemmaBuilder.createBasicDilemma(key, title, description, gravity, tempAnswerOptions));

    }

    public List<IDilemma> getAllDilemmas(){
        List<IDilemma> temp = new ArrayList<>();
        temp.addAll(dilemmaMap.values());
        return temp;
    }

    public IDilemma[] getAllDilemmasArray() {
        //  return dilemmaMap.values().toArray();
        int i = 0;
        IDilemma[] temp = new IDilemma[dilemmaMap.values().size()];
        for(IDilemma d : dilemmaMap.values()) {
            temp[i++] = d;
        }
        return temp;
    }

    public void deleteDilemma(int id) throws DilemmaException {
        if(!dilemmaMap.containsKey(id))
            throw new DilemmaException("Dilemma not found");

        dilemmaMap.remove(id);
    }

    public IDilemma getDilemma(int id) throws DilemmaException {
        if(!dilemmaMap.containsKey(id))
            throw new DilemmaException("Dilemma not found");
        return dilemmaMap.get(id);
    }

    public void answerDilemma(int id, int answerIndex) throws IllegalAccessException, DilemmaException, InstantiationException {
            dilemmaMap.get(id).addReply(replyBuilder.createReply(dilemmaMap.get(id).getPossibleAnswers().get(answerIndex), id));
    }

}
