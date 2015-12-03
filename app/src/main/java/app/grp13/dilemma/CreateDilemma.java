package app.grp13.dilemma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import app.grp13.dilemma.logic.controller.DilemmaController;

public class CreateDilemma extends AppCompatActivity implements View.OnClickListener {
    EditText dilemmaName;
    EditText dilemmaDesc;
    Button createDilemma;
    EditText answer1;
    EditText answer2;
    EditText answer3;
    EditText answer4;
    EditText answer5;
    Button gravity1Btn, gravity2Btn, gravity3Btn, gravity4Btn, gravity5Btn, gravitySelected;
    int selectedGravity;
    DilemmaController dilemmaController;

    String[] gravity = {"1", "2", "3", "4", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dilemma);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        createDilemma = (Button) findViewById(R.id.createDilButton);
        dilemmaName = (EditText) findViewById(R.id.dilemmaName);
        dilemmaDesc = (EditText) findViewById(R.id.dilemmaDesc);
        gravity1Btn.setOnClickListener(this);
        gravity2Btn.setOnClickListener(this);
        gravity3Btn.setOnClickListener(this);
        gravity4Btn.setOnClickListener(this);
        gravity5Btn.setOnClickListener(this);
        createDilemma.setOnClickListener(this);
        dilemmaController = new DilemmaController();
        try {
            dilemmaController.loadDilemmasFromDevice(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        selectedGravity = 1;



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

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == gravity1Btn) {
            gravity1Btn.setBackgroundResource(R.drawable.gravity1_btn_selected);
            gravity2Btn.setBackgroundResource(R.drawable.gravity2_button);
            gravity3Btn.setBackgroundResource(R.drawable.gravity3_button);
            gravity4Btn.setBackgroundResource(R.drawable.gravity4_button);
            gravity5Btn.setBackgroundResource(R.drawable.gravity5_button);
            selectedGravity = 1;
        } else if (v == gravity2Btn) {
            gravity1Btn.setBackgroundResource(R.drawable.gravity1_button);
            gravity2Btn.setBackgroundResource(R.drawable.gravity2_btn_selected);
            gravity3Btn.setBackgroundResource(R.drawable.gravity3_button);
            gravity4Btn.setBackgroundResource(R.drawable.gravity4_button);
            gravity5Btn.setBackgroundResource(R.drawable.gravity5_button);
            selectedGravity = 2;
        } else if (v == gravity3Btn) {
            gravity1Btn.setBackgroundResource(R.drawable.gravity1_button);
            gravity2Btn.setBackgroundResource(R.drawable.gravity2_button);
            gravity3Btn.setBackgroundResource(R.drawable.gravity3_btn_selected);
            gravity4Btn.setBackgroundResource(R.drawable.gravity4_button);
            gravity5Btn.setBackgroundResource(R.drawable.gravity5_button);
            selectedGravity = 3;
        } else if (v == gravity4Btn) {
            gravity1Btn.setBackgroundResource(R.drawable.gravity1_button);
            gravity2Btn.setBackgroundResource(R.drawable.gravity2_button);
            gravity3Btn.setBackgroundResource(R.drawable.gravity3_button);
            gravity4Btn.setBackgroundResource(R.drawable.gravity4_btn_selected);
            gravity5Btn.setBackgroundResource(R.drawable.gravity5_button);
            selectedGravity = 4;
        } else if (v == gravity5Btn) {
            gravity1Btn.setBackgroundResource(R.drawable.gravity1_button);
            gravity2Btn.setBackgroundResource(R.drawable.gravity2_button);
            gravity3Btn.setBackgroundResource(R.drawable.gravity3_button);
            gravity4Btn.setBackgroundResource(R.drawable.gravity4_button);
            gravity5Btn.setBackgroundResource(R.drawable.gravity5_btn_selected);
            selectedGravity = 5;
        } else if (v == createDilemma) {
            if (!dilemmaName.getText().toString().matches("") && !dilemmaDesc.getText().toString().matches("")) {

                if (answer3.getVisibility() == View.VISIBLE && answer3.getText().toString().matches("")) {
                    if(!answer1.getText().toString().matches(answer2.getText().toString())){
                        dilemmaController.createDilemma(dilemmaName.getText().toString(), dilemmaDesc.getText().toString(),
                                selectedGravity, answer1.getText().toString(), answer2.getText().toString());
                        try {
                            dilemmaController.saveDilemmasToDevice(getApplicationContext());
                            finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Noget gik galt! Tjek alle felter og prøv igen.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(this, "Svarmuligheder må ikke være det samme. Tjek dine svarmuligheder og prøv igen.", Toast.LENGTH_SHORT).show();
                    }
                } else if (answer4.getVisibility() == View.VISIBLE && answer4.getText().toString().matches("")) {
                    if(!answer1.getText().toString().matches(answer2.getText().toString()) || !answer1.getText().toString().matches(answer3.getText().toString()) || !answer2.getText().toString().matches(answer3.getText().toString())){
                        dilemmaController.createDilemma(dilemmaName.getText().toString(), dilemmaDesc.getText().toString(),
                                selectedGravity, answer1.getText().toString(), answer2.getText().toString(), answer3.getText().toString());
                        try {
                            dilemmaController.saveDilemmasToDevice(getApplicationContext());
                            finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Noget gik galt! Tjek alle felter og prøv igen.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Svarmuligheder må ikke være det samme. Tjek dine svarmuligheder og prøv igen", Toast.LENGTH_SHORT).show();
                    }

                } else if (answer5.getVisibility() == View.VISIBLE && answer5.getText().toString().matches("")) {
                    if(!answer1.getText().toString().matches(answer2.getText().toString()) || !answer1.getText().toString().matches(answer3.getText().toString()) || answer1.getText().toString().matches(answer4.getText().toString()) || !answer2.getText().toString().matches(answer3.getText().toString()) || !answer2.getText().toString().matches(answer4.getText().toString()) || !answer3.getText().toString().matches(answer4.getText().toString())){
                        dilemmaController.createDilemma(dilemmaName.getText().toString(), dilemmaDesc.getText().toString(),
                                selectedGravity, answer1.getText().toString(), answer2.getText().toString(),
                                answer3.getText().toString(), answer4.getText().toString());
                        try {
                            dilemmaController.saveDilemmasToDevice(getApplicationContext());
                            finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Noget gik galt! Tjek alle felter og prøv igen.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Svarmuligheder må ikke være det samme. Tjek dine svarmuligheder og prøv igen", Toast.LENGTH_SHORT).show();
                    }

                } else if (answer5.getVisibility() == View.VISIBLE && !answer5.getText().toString().matches("")) {
                    if(!answer1.getText().toString().matches(answer2.getText().toString()) || !answer1.getText().toString().matches(answer3.getText().toString()) || answer1.getText().toString().matches(answer4.getText().toString()) || answer1.getText().toString().matches(answer5.getText().toString()) || !answer2.getText().toString().matches(answer3.getText().toString()) || !answer2.getText().toString().matches(answer4.getText().toString()) || answer2.getText().toString().matches(answer5.getText().toString()) || !answer3.getText().toString().matches(answer4.getText().toString()) || answer3.getText().toString().matches(answer5.getText().toString()) || answer4.getText().toString().matches(answer5.getText().toString())){
                        dilemmaController.createDilemma(dilemmaName.getText().toString(), dilemmaDesc.getText().toString(),
                                selectedGravity, answer1.getText().toString(), answer2.getText().toString(),
                                answer3.getText().toString(), answer4.getText().toString(), answer5.getText().toString());
                        try {
                            dilemmaController.saveDilemmasToDevice(getApplicationContext());
                            finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Noget gik galt! Tjek alle felter og prøv igen.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Svarmuligheder må ikke være det samme. Tjek dine svarmuligheder og prøv igen", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "Noget gik galt! Tjek alle felter og prøv igen.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Noget gik galt! Tjek alle felter og prøv igen.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
