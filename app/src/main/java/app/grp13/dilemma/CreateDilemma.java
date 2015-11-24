package app.grp13.dilemma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.wefika.horizontalpicker.HorizontalPicker;

import org.w3c.dom.Text;

public class CreateDilemma extends AppCompatActivity implements View.OnClickListener {
    HorizontalPicker gravityPicker;
    HorizontalPicker answerCount;
    Button setCountButton;
    Button createDilemma;
    TextView answerText;
    EditText answer1;
    EditText answer2;
    EditText answer3;
    EditText answer4;
    EditText answer5;

    String[] gravity = {"1", "2", "3", "4", "5"};
    String[] ACount = {"2", "3", "4", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dilemma);
        gravityPicker = (HorizontalPicker) findViewById(R.id.gravityPicker);
        answerCount = (HorizontalPicker) findViewById(R.id.answerCount);
        setCountButton = (Button) findViewById(R.id.setCountButton);
        createDilemma = (Button) findViewById(R.id.createDilButton);
        answerText = (TextView) findViewById(R.id.answerText);
        answer1 = (EditText) findViewById(R.id.answer1);
        answer2 = (EditText) findViewById(R.id.answer2);
        answer3 = (EditText) findViewById(R.id.answer3);
        answer4 = (EditText) findViewById(R.id.answer4);
        answer5 = (EditText) findViewById(R.id.answer5);
        answerCount.setValues(ACount);
        answerCount.setSelectedItem(2);
        gravityPicker.setValues(gravity);
        gravityPicker.setSelectedItem(2);
        setCountButton.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if(v == setCountButton){
            int tempCount = answerCount.getSelectedItem()+2;
            answerCount.setVisibility(View.GONE);
            setCountButton.setVisibility(View.GONE);
            answerText.setText("Svarmuligheder: ");
            createDilemma.setVisibility(View.VISIBLE);
            if(tempCount==2){
                answer1.setVisibility(View.VISIBLE);
                answer2.setVisibility(View.VISIBLE);
            }
            else if(tempCount==3){
                answer1.setVisibility(View.VISIBLE);
                answer2.setVisibility(View.VISIBLE);
                answer3.setVisibility(View.VISIBLE);
            }
            else if(tempCount==4){
                answer1.setVisibility(View.VISIBLE);
                answer2.setVisibility(View.VISIBLE);
                answer3.setVisibility(View.VISIBLE);
                answer4.setVisibility(View.VISIBLE);
            }
            else if(tempCount==5){
                answer1.setVisibility(View.VISIBLE);
                answer2.setVisibility(View.VISIBLE);
                answer3.setVisibility(View.VISIBLE);
                answer4.setVisibility(View.VISIBLE);
                answer5.setVisibility(View.VISIBLE);
            }
        }
    }
}
