package app.grp13.dilemma.logic.builder;

import java.io.Serializable;
import java.util.List;

import app.grp13.dilemma.logic.dto.BasicDilemma;
import app.grp13.dilemma.logic.dto.IAnswer;
import app.grp13.dilemma.logic.dto.IDilemma;

/*
Lavet af:
Sazvan Kasim Ali - S144884
Mathias Petersen - S144874
Bao Duy Nguyen - S144880
Christian Jappe - S144866
Magnus Nielsen - S141899
Nicolai Hansen - S133974
Denne klasse følger midlertidigt factory pattern
*/
public class DilemmaBuilder implements Serializable{



    public IDilemma createBasicDilemma(int id, String title, String description, int gravity, List<IAnswer> answerOptions) {
        return new BasicDilemma(id, title, description, gravity, answerOptions);
    }
}
