package app.grp13.dilemma;

import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.grp13.dilemma.logic.dto.BasicDilemma;
import app.grp13.dilemma.logic.dto.IDilemma;

public class AnswerDilemma extends AppCompatActivity implements View.OnClickListener {

    private Button vote1Btn, vote2Btn, vote3Btn, vote4Btn, vote5Btn;
    private TextView vote1Text, vote2Text, vote3Text, vote4Text, vote5Text;
    private TextView vote1Frame, vote2Frame, vote3Frame, vote4Frame, vote5Frame;
    int totalCount, vote1Count, vote2Count, vote3Count, vote4Count, vote5Count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_dilemma);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extra = getIntent().getBundleExtra("dilemma");
        BasicDilemma dilemma = (BasicDilemma)extra.getSerializable("test");

        vote1Btn = (Button) findViewById(R.id.vote1Btn);
        vote2Btn = (Button) findViewById(R.id.vote2Btn);
        vote3Btn = (Button) findViewById(R.id.vote3Btn);
        vote4Btn = (Button) findViewById(R.id.vote4Btn);
        vote5Btn = (Button) findViewById(R.id.vote5Btn);
        vote1Btn.setOnClickListener(this);
        vote2Btn.setOnClickListener(this);
        vote3Btn.setOnClickListener(this);
        vote4Btn.setOnClickListener(this);
        vote5Btn.setOnClickListener(this);
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
        vote1Frame.setWidth(0);


        vote1Count = 0;
        vote2Count = 0;
        vote3Count = 0;
        vote4Count = 0;
        vote5Count = 0;
        totalCount = 0;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == vote1Btn) {
            vote1Count++;
            totalCount++;
            updateFrames();
            updateText();
        } else if (v == vote2Btn) {
            vote2Count++;
            totalCount++;
            updateFrames();
            updateText();
        } else if (v == vote3Btn) {
            vote3Count++;
            totalCount++;
            updateFrames();
            updateText();
        } else if (v == vote4Btn) {
            vote4Count++;
            totalCount++;
            updateFrames();
            updateText();
        } else if (v == vote5Btn) {
            vote5Count++;
            totalCount++;
            updateFrames();
            updateText();
        }
    }

    public void updateText() {
        vote1Text.setText("Yes (" + (double)Math.round((double)vote1Count/totalCount*10000)/100 + "%)");
        vote2Text.setText("No (" + (double)Math.round((double)vote2Count/totalCount*10000)/100 + "%)");
        vote3Text.setText("No (" + (double) Math.round((double) vote3Count / totalCount * 10000) / 100 + "%)");
        vote4Text.setText("No (" + (double) Math.round((double) vote4Count / totalCount * 10000) / 100 + "%)");
        vote5Text.setText("No (" + (double) Math.round((double) vote5Count / totalCount * 10000) / 100 + "%)");

    }

    public void updateFrames() {

        float px = findViewById(R.id.scrollView3).getWidth();

        //Resources r = getResources();
        //float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, r.getDisplayMetrics());

        vote1Frame.setWidth((int)px * vote1Count / totalCount);
        vote2Frame.setWidth((int)px * vote2Count/totalCount);
        vote3Frame.setWidth((int)px * vote3Count/totalCount);
        vote4Frame.setWidth((int)px * vote4Count/totalCount);
        vote5Frame.setWidth((int)px * vote5Count/totalCount);
    }
}


