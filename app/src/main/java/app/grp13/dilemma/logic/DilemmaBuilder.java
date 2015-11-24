package app.grp13.dilemma.logic;

import java.util.List;

/**
 * Created by champen on 22-11-2015.
 * følger factory pattern indtil videre, skal dog evt. laves om.
 */
public class DilemmaBuilder {



    public IDilemma createBasicDilemma(int id, String title, String description, int gravity, List<IAnswer> answerOptions) {
        return new BasicDilemma(id, title, description, gravity, answerOptions);
    }
}
