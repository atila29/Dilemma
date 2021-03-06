package app.grp13.dilemma;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import app.grp13.dilemma.application.ApplicationState;
import app.grp13.dilemma.logic.controller.AccountController;
import app.grp13.dilemma.logic.controller.DilemmaController;
import app.grp13.dilemma.logic.controller.IAccountControllerActivity;
import app.grp13.dilemma.logic.dao.AccountDAO;
import app.grp13.dilemma.logic.dao.DilemmaFirebaseDAO;
import app.grp13.dilemma.logic.dto.Account;
import app.grp13.dilemma.logic.exceptions.DAOException;
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
public class CreateDilemma extends AppCompatActivity implements View.OnClickListener, IAccountControllerActivity {
    private EditText dilemmaName;
    private EditText dilemmaDesc;
    private Button createDilemma;
    private EditText answer1;
    private EditText answer2;
    private EditText answer3;
    private EditText answer4;
    private EditText answer5;
    private Button gravity1Btn, gravity2Btn, gravity3Btn, gravity4Btn, gravity5Btn, gravitySelected;
    private int selectedGravity;
    private DilemmaController dilemmaController;
    private int id;

    private String[] gravity = {"1", "2", "3", "4", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dilemma);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeUIElements();
        gravity1Btn.setOnClickListener(this);
        gravity2Btn.setOnClickListener(this);
        gravity3Btn.setOnClickListener(this);
        gravity4Btn.setOnClickListener(this);
        gravity5Btn.setOnClickListener(this);
        createDilemma.setOnClickListener(this);
        dilemmaController = ApplicationState.getInstance().getDilemmaController();
        ApplicationState.getInstance().setAccountActivityFocus(this);
        selectedGravity = 1;

        //Følgende 3 if statements håndtere visibilitet af svarmulighedernes tekst boxe (EditTexts)
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
            //Tjekker for at dilemma titlen ikke er tom, og at beskrivelsen ikke er tom.
            if (!dilemmaName.getText().toString().matches("") && !dilemmaDesc.getText().toString().matches("")) {
                //Tjekker for om svarmulighed 3 er tom, da vi sådan kan vide hvor mange svarmuligheder vi skal tage med
                if (answer3.getVisibility() == View.VISIBLE && answer3.getText().toString().matches("")) {
                    //Sørger for der ikke er 2 af samme svar blandt første 2 svar, og opretter hermed et dilemma med 2 svar.
                    if(!answer1.getText().toString().matches(answer2.getText().toString())){
                        id = dilemmaController.createDilemma(dilemmaName.getText().toString(), dilemmaDesc.getText().toString(),
                                selectedGravity, answer1.getText().toString(), answer2.getText().toString());
                        try {
                            ApplicationState.getInstance().getAccountController().authenticate();
                        } catch (DAOException e) {
                            e.printStackTrace();
                        }
                    }
                    //Sørger for at oplyse brugeren om at noget ikke stemmer.
                    else{
                        Toast.makeText(this, "Svarmuligheder må ikke være det samme. Tjek dine svarmuligheder og prøv igen.", Toast.LENGTH_SHORT).show();
                    }
                // Her tjekkes for om answer 4 er synlig men tom, da dette kan konkludere at vi kun har 3 mulige svar at oprette dilemmaet med
                } else if (answer4.getVisibility() == View.VISIBLE && answer4.getText().toString().matches("")) {
                    //Tjekker for dublikater og opretter herefter et dilemma med 3 svarmuligheder
                    if(!answer1.getText().toString().matches(answer2.getText().toString()) || !answer1.getText().toString().matches(answer3.getText().toString()) || !answer2.getText().toString().matches(answer3.getText().toString())){
                        id = dilemmaController.createDilemma(dilemmaName.getText().toString(), dilemmaDesc.getText().toString(),
                                selectedGravity, answer1.getText().toString(), answer2.getText().toString(), answer3.getText().toString());
                        try {
                            ApplicationState.getInstance().getAccountController().authenticate();
                        } catch (DAOException e) {
                            e.printStackTrace();
                        }
                      //Sørger for at oplyse brugeren om at noget ikke stemmer.
                    } else {
                        Toast.makeText(this, "Svarmuligheder må ikke være det samme. Tjek dine svarmuligheder og prøv igen", Toast.LENGTH_SHORT).show();
                    }
                //Tjekker for om answer 5 er synlig men tom, da dette kan konkludere at vi kun har 4 mulige svar at oprette et dilemma med
                } else if (answer5.getVisibility() == View.VISIBLE && answer5.getText().toString().matches("")) {
                    //Tjekker for dublikater og oprette herefter et dilemma med 4 svarmuligheder
                    if(!answer1.getText().toString().matches(answer2.getText().toString()) || !answer1.getText().toString().matches(answer3.getText().toString()) || answer1.getText().toString().matches(answer4.getText().toString()) || !answer2.getText().toString().matches(answer3.getText().toString()) || !answer2.getText().toString().matches(answer4.getText().toString()) || !answer3.getText().toString().matches(answer4.getText().toString())){
                        id = dilemmaController.createDilemma(dilemmaName.getText().toString(), dilemmaDesc.getText().toString(),
                                selectedGravity, answer1.getText().toString(), answer2.getText().toString(),
                                answer3.getText().toString(), answer4.getText().toString());
                        try {
                            ApplicationState.getInstance().getAccountController().authenticate();
                        } catch (DAOException e) {
                            e.printStackTrace();
                        }
                    //Sørger for at oplyse brugeren om at noget ikke stemmer.
                    } else {
                        Toast.makeText(this, "Svarmuligheder må ikke være det samme. Tjek dine svarmuligheder og prøv igen", Toast.LENGTH_SHORT).show();
                    }
                //tjekker nu for om svarmulighed 5 er synlig men ikke tom, da det betyder at vi har 5 svarmuligheder.
                } else if (answer5.getVisibility() == View.VISIBLE && !answer5.getText().toString().matches("")) {
                    //tjekker for dublikater og opretter herefter et dilemma med 5 svarmuligheder
                    if(!answer1.getText().toString().matches(answer2.getText().toString()) || !answer1.getText().toString().matches(answer3.getText().toString()) || answer1.getText().toString().matches(answer4.getText().toString()) || answer1.getText().toString().matches(answer5.getText().toString()) || !answer2.getText().toString().matches(answer3.getText().toString()) || !answer2.getText().toString().matches(answer4.getText().toString()) || answer2.getText().toString().matches(answer5.getText().toString()) || !answer3.getText().toString().matches(answer4.getText().toString()) || answer3.getText().toString().matches(answer5.getText().toString()) || answer4.getText().toString().matches(answer5.getText().toString())){
                        id = dilemmaController.createDilemma(dilemmaName.getText().toString(), dilemmaDesc.getText().toString(),
                                selectedGravity, answer1.getText().toString(), answer2.getText().toString(),
                                answer3.getText().toString(), answer4.getText().toString(), answer5.getText().toString());
                        try {
                            ApplicationState.getInstance().getAccountController().authenticate();
                        } catch (DAOException e) {
                            e.printStackTrace();
                        }
                    //Sørger for at oplyse brugeren om at noget ikke stemmer.
                    } else {
                        Toast.makeText(this, "Svarmuligheder må ikke være det samme. Tjek dine svarmuligheder og prøv igen", Toast.LENGTH_SHORT).show();
                    }
                //Sørger for at oplyse brugeren om at noget ikke stemmer.
                } else {
                    Toast.makeText(this, "Noget gik galt! Tjek alle felter og prøv igen.", Toast.LENGTH_SHORT).show();
                }
            //Sørger for at oplyse brugeren om at noget ikke stemmer.
            } else {
                Toast.makeText(this, "Noget gik galt! Tjek alle felter og prøv igen.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Initialisere alle vores ui elementer
    public void initializeUIElements(){
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
    }

    @Override
    public void ShowErrorMessage(Exception e) {
        Log.v("LOL", e.getMessage());
    }

    @Override
    public void showLoginToast(String msg) {

    }

    @Override
    public void accountAuthentication(Account acc) {
        if(!acc.getMyDilemmas().contains(id)) { // lidt cowboyder kode
            acc.getMyDilemmas().add(id);
            try {
                new AccountDAO().saveAccount(acc, acc.getId());
                new DilemmaFirebaseDAO().saveDilemma(dilemmaController.getDilemma(id));
            } catch (DAOException e) {
                e.printStackTrace();
            } catch (DilemmaException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, "Dilemma oprettet.", Toast.LENGTH_SHORT).show();
        }
        this.finish();
    }
}
