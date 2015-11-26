package app.grp13.dilemma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateDilemma extends AppCompatActivity implements View.OnClickListener {
    Button createDilemma;
    TextView answerText;
    EditText answer1;
    EditText answer2;
    EditText answer3;
    EditText answer4;
    EditText answer5;
    Button gravity1Btn, gravity2Btn, gravity3Btn, gravity4Btn, gravity5Btn;
    Button selectedButton;

    String[] gravity = {"1", "2", "3", "4", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dilemma);

        answer1 = (EditText) findViewById(R.id.answer1);
        answer2 = (EditText) findViewById(R.id.answer2);
        answer3 = (EditText) findViewById(R.id.answer3);
        answer4 = (EditText) findViewById(R.id.answer4);
        answer5 = (EditText) findViewById(R.id.answer5);
        gravity1Btn = (Button) findViewById(R.id.gravity1Btn);
        gravity2Btn = (Button) findViewById(R.id.gravity2Btn);
        gravity3Btn = (Button) findViewById(R.id.gravity3Btn);
        gravity4Btn = (Button) findViewById(R.id.gravity4Btn);
        gravity5Btn = (Button) findViewById(R.id.gravity5Btn);
        gravity1Btn.setOnClickListener(this);
        gravity2Btn.setOnClickListener(this);
        gravity3Btn.setOnClickListener(this);
        gravity4Btn.setOnClickListener(this);
        gravity5Btn.setOnClickListener(this);

        answer1.setVisibility(View.VISIBLE);
        answer2.setVisibility(View.VISIBLE);


        selectedButton = gravity1Btn;


        answer1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !answer1.getText().equals("")) {
                    answer3.setVisibility(View.VISIBLE);
                }
            }
        });
        answer2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !answer2.getText().equals("")) {
                    answer4.setVisibility(View.VISIBLE);
                }
            }
        });
        answer3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !answer3.getText().equals("")) {
                    answer5.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == gravity1Btn) {
            gravity1Btn.setBackgroundResource(R.drawable.gravity1_btn_selected);
            gravity2Btn.setBackgroundResource(R.drawable.gravity2_button);
            gravity3Btn.setBackgroundResource(R.drawable.gravity3_button);
            gravity4Btn.setBackgroundResource(R.drawable.gravity4_button);
            gravity5Btn.setBackgroundResource(R.drawable.gravity5_button);
        } else if (v == gravity2Btn) {
            gravity1Btn.setBackgroundResource(R.drawable.gravity1_button);
            gravity2Btn.setBackgroundResource(R.drawable.gravity2_btn_selected);
            gravity3Btn.setBackgroundResource(R.drawable.gravity3_button);
            gravity4Btn.setBackgroundResource(R.drawable.gravity4_button);
            gravity5Btn.setBackgroundResource(R.drawable.gravity5_button);
        } else if (v == gravity3Btn) {
            gravity1Btn.setBackgroundResource(R.drawable.gravity1_button);
            gravity2Btn.setBackgroundResource(R.drawable.gravity2_button);
            gravity3Btn.setBackgroundResource(R.drawable.gravity3_btn_selected);
            gravity4Btn.setBackgroundResource(R.drawable.gravity4_button);
            gravity5Btn.setBackgroundResource(R.drawable.gravity5_button);
        } else if (v == gravity4Btn) {
            gravity1Btn.setBackgroundResource(R.drawable.gravity1_button);
            gravity2Btn.setBackgroundResource(R.drawable.gravity2_button);
            gravity3Btn.setBackgroundResource(R.drawable.gravity3_button);
            gravity4Btn.setBackgroundResource(R.drawable.gravity4_btn_selected);
            gravity5Btn.setBackgroundResource(R.drawable.gravity5_button);
        } else if (v == gravity5Btn) {
            gravity1Btn.setBackgroundResource(R.drawable.gravity1_button);
            gravity2Btn.setBackgroundResource(R.drawable.gravity2_button);
            gravity3Btn.setBackgroundResource(R.drawable.gravity3_button);
            gravity4Btn.setBackgroundResource(R.drawable.gravity4_button);
            gravity5Btn.setBackgroundResource(R.drawable.gravity5_btn_selected);
        }
    }
}
