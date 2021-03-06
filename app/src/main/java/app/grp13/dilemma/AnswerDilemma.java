package app.grp13.dilemma;

import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

import app.grp13.dilemma.application.ApplicationState;
import app.grp13.dilemma.logic.controller.AccountController;
import app.grp13.dilemma.logic.controller.DilemmaController;
import app.grp13.dilemma.logic.controller.IAccountControllerActivity;
import app.grp13.dilemma.logic.dao.AccountDAO;
import app.grp13.dilemma.logic.dao.DilemmaFirebaseDAO;
import app.grp13.dilemma.logic.dao.IAccountParser;
import app.grp13.dilemma.logic.dao.IDilemmaDAO;
import app.grp13.dilemma.logic.dto.Account;
import app.grp13.dilemma.logic.dto.BasicDilemma;
import app.grp13.dilemma.logic.dto.BasicReply;
import app.grp13.dilemma.logic.dto.IDilemma;
import app.grp13.dilemma.logic.dto.IReply;
import app.grp13.dilemma.logic.exceptions.DAOException;
import app.grp13.dilemma.logic.exceptions.DilemmaException;
import app.grp13.dilemma.logic.exceptions.LoginException;

/*
Lavet af:
Sazvan Kasim Ali - S144884
Mathias Petersen - S144874
Bao Duy Nguyen - S144880
Christian Jappe - S144866
Magnus Nielsen - S141899
Nicolai Hansen - S133974
*/
public class AnswerDilemma extends AppCompatActivity implements View.OnClickListener, IAccountControllerActivity {

    private Button vote1Btn, vote2Btn, vote3Btn, vote4Btn, vote5Btn;
    private TextView vote1Text, vote2Text, vote3Text, vote4Text, vote5Text, statusText;
    private TextView vote1Frame, vote2Frame, vote3Frame, vote4Frame, vote5Frame;
    private int totalCount, vote1Count, vote2Count, vote3Count, vote4Count, vote5Count;
    private IDilemma dilemma;
    private TextView gravityTxt;
    private int answer;
    private boolean checkAnswered;
    private TextView questionTxt;
    private TextView descriptionTxt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_dilemma);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extra = getIntent().getBundleExtra("dilemma");
        dilemma = (BasicDilemma)extra.getSerializable("test");
        checkAnswered = false;
        ApplicationState.getInstance().setAccountActivityFocus(this);
        initializeUIElements();
        questionTxt.setText(dilemma.getTitle());
        descriptionTxt.setText(dilemma.getDescription());
        vote1Btn.setOnClickListener(this);
        vote2Btn.setOnClickListener(this);
        vote3Btn.setOnClickListener(this);
        vote4Btn.setOnClickListener(this);
        vote5Btn.setOnClickListener(this);
        vote1Frame.setWidth(0);

        //Authenticater for at sikre at brugeren er logget ind, da "gæster" på nuværende tidspunkt ikke må stemme.
        try {
            ApplicationState.getInstance().getAccountController().authenticate();
        } catch (LoginException e) {
            e.printStackTrace();
            updateVotes();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideButtons();
                    updateText();
                    statusText.setText("Du skal være logget ind for at stemme på dilemmaer");
                    statusText.setVisibility(View.VISIBLE);
                }
            }, 1);
        }

        //Håndtere seriøsiteten af dilemmaet ved at sætte det rette tal samt rette farve i en cirkel
        gravityTxt.setText(String.valueOf(dilemma.getgravity()));
        if (gravityTxt.getText().equals("1")) {
            gravityTxt.setBackgroundResource(R.drawable.gravity1_container);
        } else if (gravityTxt.getText().equals("2")) {
            gravityTxt.setBackgroundResource(R.drawable.gravity2_container);
        } else if (gravityTxt.getText().equals("3")) {
            gravityTxt.setBackgroundResource(R.drawable.gravity3_container);
        } else if (gravityTxt.getText().equals("4")) {
            gravityTxt.setBackgroundResource(R.drawable.gravity4_container);
        } else {
            gravityTxt.setBackgroundResource(R.drawable.gravity5_container);
        }

        //Sætter knapper og statistik tekster til de svar de repræsenterer
        vote1Btn.setText(dilemma.getPossibleAnswers().get(0).getAnswer());
        vote1Text.setText(dilemma.getPossibleAnswers().get(0).getAnswer());
        vote2Btn.setText(dilemma.getPossibleAnswers().get(1).getAnswer());
        vote2Text.setText(dilemma.getPossibleAnswers().get(1).getAnswer());
        if(dilemma.getPossibleAnswers().size()>2) {
            vote3Btn.setText(dilemma.getPossibleAnswers().get(2).getAnswer());
            vote3Btn.setVisibility(View.VISIBLE);
            vote3Text.setVisibility(View.VISIBLE);
            vote3Frame.setVisibility(View.VISIBLE);
            vote3Text.setText(dilemma.getPossibleAnswers().get(2).getAnswer());
        }
        if(dilemma.getPossibleAnswers().size()>3) {
            vote4Btn.setText(dilemma.getPossibleAnswers().get(3).getAnswer());
            vote4Btn.setVisibility(View.VISIBLE);
            vote4Text.setVisibility(View.VISIBLE);
            vote4Frame.setVisibility(View.VISIBLE);
            vote4Text.setText(dilemma.getPossibleAnswers().get(3).getAnswer());
        }
        if(dilemma.getPossibleAnswers().size()>4) {
            vote5Btn.setText(dilemma.getPossibleAnswers().get(4).getAnswer());
            vote5Btn.setVisibility(View.VISIBLE);
            vote5Text.setVisibility(View.VISIBLE);
            vote5Frame.setVisibility(View.VISIBLE);
            vote5Text.setText(dilemma.getPossibleAnswers().get(4).getAnswer());
        }

        //controller.addDilemma(dilemma);




    }

    public void updateVotes() {
        try {
            dilemma = ApplicationState.getInstance().getDilemmaController().getDilemma(ApplicationState.getInstance().getDilemmaController().getDilemmaKey(dilemma));
        } catch (DilemmaException e) {
            e.printStackTrace();
        }

        // Skal refactores væk, hele denne klasse skal optimeres
        vote1Count = 0;
        vote2Count = 0;
        vote3Count = 0;
        vote4Count = 0;
        vote5Count = 0;
        totalCount = 0;

        // Tæller antal stemmer i det pågældende dilemma, og tæller antal for hver af svarene.
        for(IReply rep : dilemma.getReplys()){
            totalCount++;
            Log.v("SHIT", rep.getReply());
            if(rep.getReply().equals(dilemma.getPossibleAnswers().get(0).getAnswer()))
                vote1Count++;
            else if(rep.getReply().equals(dilemma.getPossibleAnswers().get(1).getAnswer()))
                vote2Count++;
            else if(rep.getReply().equals(dilemma.getPossibleAnswers().get(2).getAnswer()))
                vote3Count++;
            else if(rep.getReply().equals(dilemma.getPossibleAnswers().get(3).getAnswer()))
                vote4Count++;
            else if(rep.getReply().equals(dilemma.getPossibleAnswers().get(4).getAnswer()))
                vote5Count++;
        }

        // Opdatere statistikken efter 100 ms for at sørge for at stemmerne er talte og vores view ikke er "for hurtigt"
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateFrames();
            }
        }, 100);

        updateText();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        try {
            if (v == vote1Btn) {
                answer = 0;
                /*Log.v("TEXZ", String.valueOf(controller.getDilemmaKey(dilemma)));
                Log.v("TEXZ", String.valueOf(dilemma.getID()));
                controller.answerDilemma(controller.getDilemmaKey(dilemma), 0);
                Log.v("TEXZ", String.valueOf(dilemma.getReplys().size()));*/
                ApplicationState.getInstance().getAccountController().authenticate();
            } else if (v == vote2Btn) {
                answer = 1;
                ApplicationState.getInstance().getAccountController().authenticate();
            } else if (v == vote3Btn) {
                answer = 2;
                ApplicationState.getInstance().getAccountController().authenticate();
            } else if (v == vote4Btn) {
                answer = 3;
                ApplicationState.getInstance().getAccountController().authenticate();
            } else if (v == vote5Btn) {
                answer = 4;
                ApplicationState.getInstance().getAccountController().authenticate();
            }
        } catch (LoginException e) {
            e.printStackTrace();
            Toast.makeText(this, "Du skal være logget på for at stemme på et dilemma", Toast.LENGTH_SHORT).show();
        }

    }

    public void updateText() {
        vote1Text.setText(vote1Btn.getText() + " (" + (double)Math.round((double)vote1Count/totalCount*10000)/100 + "%)");
        vote2Text.setText(vote2Btn.getText() + " (" + (double)Math.round((double)vote2Count/totalCount*10000)/100 + "%)");
        vote3Text.setText(vote3Btn.getText() + " (" + (double) Math.round((double) vote3Count / totalCount * 10000) / 100 + "%)");
        vote4Text.setText(vote4Btn.getText() + " (" + (double) Math.round((double) vote4Count / totalCount * 10000) / 100 + "%)");
        vote5Text.setText(vote5Btn.getText() + " (" + (double) Math.round((double) vote5Count / totalCount * 10000) / 100 + "%)");

    }

    public void hideButtons(){
        vote1Btn.setVisibility(View.GONE);
        vote2Btn.setVisibility(View.GONE);
        vote3Btn.setVisibility(View.GONE);
        vote4Btn.setVisibility(View.GONE);
        vote5Btn.setVisibility(View.GONE);
    }

    //Opdatere den illustrerede statistik
    public void updateFrames() {

        float px = findViewById(R.id.scrollView3).getWidth();

        //Resources r = getResources();
        //float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, r.getDisplayMetrics());
        try {
            vote1Frame.setWidth((int)px * vote1Count / totalCount);
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
        try {
            vote2Frame.setWidth((int)px * vote2Count/totalCount);
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
        try {
            vote3Frame.setWidth((int)px * vote3Count/totalCount);
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
        try {
            vote4Frame.setWidth((int) px * vote4Count / totalCount);
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
        try {
            vote5Frame.setWidth((int)px * vote5Count/totalCount);
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }

    }

    //Initialisere alle vores ui elementer
    public void initializeUIElements(){
        vote1Btn = (Button) findViewById(R.id.vote1Btn);
        vote2Btn = (Button) findViewById(R.id.vote2Btn);
        vote3Btn = (Button) findViewById(R.id.vote3Btn);
        vote4Btn = (Button) findViewById(R.id.vote4Btn);
        vote5Btn = (Button) findViewById(R.id.vote5Btn);
        gravityTxt = (TextView) findViewById(R.id.gravityTxt);
        vote1Text = (TextView) findViewById(R.id.vote1Text);
        vote2Text = (TextView) findViewById(R.id.vote2Text);
        vote3Text = (TextView) findViewById(R.id.vote3Text);
        vote4Text = (TextView) findViewById(R.id.vote4Text);
        vote5Text = (TextView) findViewById(R.id.vote5Text);
        vote1Frame = (TextView) findViewById(R.id.vote1Frame);
        vote2Frame = (TextView) findViewById(R.id.vote2Frame);
        vote3Frame = (TextView) findViewById(R.id.vote3Frame);
        vote4Frame = (TextView) findViewById(R.id.vote4Frame);
        vote5Frame = (TextView) findViewById(R.id.vote5Frame);
        questionTxt = (TextView) findViewById(R.id.QuestionTxt);
        descriptionTxt = (TextView) findViewById(R.id.DescriptionTxt);
        statusText = (TextView) findViewById(R.id.statusTextView);
    }

    @Override
    public void ShowErrorMessage(Exception e) {

    }

    @Override
    public void showLoginToast(String msg) {

    }

    @Override
    public void accountAuthentication(Account acc) {
        try {
            IReply r = new BasicReply();
            r.setID(ApplicationState.getInstance().getDilemmaController().getDilemma(ApplicationState.getInstance().getDilemmaController().getDilemmaKey(dilemma)).getID());
            r.setReply(ApplicationState.getInstance().getDilemmaController().getDilemma(ApplicationState.getInstance().getDilemmaController().getDilemmaKey(dilemma)).getPossibleAnswers().get(answer).getAnswer());
            //Tjekker om man allerede har svaret. Hvis ja, sættes viewet til at vise statistik og stem knapperne gemmes.
            if(!checkAnswered && (acc.getMyReplys().contains(r) || acc.getMyDilemmas().contains(dilemma.getID()))){
                updateVotes();
                hideButtons();
            }
            //Hvis man ikke har stemt stemmes der og viewet sættes til at vise statistik og stem knapperne gemmes.
            if(checkAnswered && !acc.getMyReplys().contains(r)){
                ApplicationState.getInstance().getDilemmaController().answerDilemma(ApplicationState.getInstance().getDilemmaController().getDilemmaKey(dilemma), answer);
                acc.getMyReplys().add(r);
                new DilemmaFirebaseDAO().saveDilemma(ApplicationState.getInstance().getDilemmaController().getDilemma(ApplicationState.getInstance().getDilemmaController().getDilemmaKey(dilemma)));
                new AccountDAO().saveAccount(acc, acc.getId());
                updateVotes();
                hideButtons();
            }
            checkAnswered = true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (DilemmaException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}


