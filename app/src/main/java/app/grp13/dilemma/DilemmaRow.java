package app.grp13.dilemma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*
Lavet af:
Sazvan Kasim Ali - S144884
Mathias Petersen - S144874
Bao Duy Nguyen - S144880
Christian Jappe - S144866
Magnus Nielsen - S141899
Nicolai Hansen - S133974
*/
public class DilemmaRow {

    private int gravity;
    private String question;
    private int id;

    public DilemmaRow(int gravity, String question, int id){
        this.gravity = gravity;
        this.question = question;
        this.id = id;
    }

    public DilemmaRow(){
    }

    public int getGravity(){
        return this.gravity;
    }

    public String getQuestion(){
        return this.question;
    }

    public int getId(){
        return this.id;
    }
}
