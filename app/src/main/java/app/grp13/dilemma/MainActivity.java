package app.grp13.dilemma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.grp13.dilemma.logic.controller.AccountController;
import app.grp13.dilemma.logic.controller.DilemmaController;
import app.grp13.dilemma.logic.dto.BasicDilemma;
import app.grp13.dilemma.logic.dto.IAnswer;
import app.grp13.dilemma.logic.dto.IDilemma;
import app.grp13.dilemma.logic.dto.IReply;

public class MainActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView gravityText;
    private ListView dilemmaList;
    private DilemmaController dController;
    private AccountController aController;

    private String[] dilemmaTitles;
    private String[] dilemmaGravities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Aktive Dilemmaer");

        aController = new AccountController();
        dController = new DilemmaController();
        try {
            dController.loadDilemmasFromDevice(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(dController.getAllDilemmas().isEmpty()) {
            dController.createDilemma("test1", "Dette er en test. hafhuiajaepødfiojka foøiuajh dfoøia ofiu haoødif hoadøif jhoadi fjoiadjkm fa fda fad fad fad fad fadf adf adfadf das d fa", 2, "svar et", "svar to", "svar tre", "svar fire");
            dController.createDilemma("test2", "Dette er en test2", 1, "svar et", "svar to");
            dController.createDilemma("test3", "Dette er en test3", 4, "svar et", "svar to");
            dController.createDilemma("test4", "Dette er en test4", 5, "svar et", "svar to");
        }

        dilemmaList = (ListView) findViewById(R.id.dilemmaList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateDilemma.class));

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        try {
            updateList(dController.getAllDilemmasArray());
        } catch (IOException e) {
            e.printStackTrace();
        }


        dilemmaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent openAnswerDilemma = new Intent(MainActivity.this, AnswerDilemma.class);
                //openAnswerDilemma.putExtra("dilemma", dilemmaBundle(dController.getAllDilemmasArray()[position]));
                Bundle bundle = new Bundle();
                bundle.putSerializable("test", (BasicDilemma) dController.getAllDilemmasArray()[position]);
                openAnswerDilemma.putExtra("dilemma", bundle);

                startActivity(openAnswerDilemma);
            }
        });

    }

    private Bundle dilemmaBundle(IDilemma dilemma) {
        Bundle bundle = new Bundle();
        bundle.putString("title",dilemma.getTitle());
        bundle.putString("description", dilemma.getDescription());
        bundle.putInt("gravity", dilemma.getgravity());
        bundle.putInt("id", dilemma.getID());
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> tempReplys = new ArrayList<>();
        for(IAnswer a : dilemma.getPossibleAnswers()){
            temp.add(a.getAnswer());
        }
        for(IReply r : dilemma.getReplys()) {
            tempReplys.add(r.getReply());
        }
        bundle.putStringArrayList("panswers", temp);
        bundle.putStringArrayList("replys", tempReplys);
        return bundle;

    }



    public void updateList(IDilemma[] array) throws IOException {
        try {
            dController.loadDilemmasFromDevice(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        dilemmaTitles = new String[array.length];
        dilemmaGravities = new String[array.length];
        for(int i=0 ; i<array.length ; i++){
            dilemmaTitles[i] = array[i].getTitle();
            dilemmaGravities[i] = String.valueOf(array[i].getgravity());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_dilemma_row, R.id.questionText, dilemmaTitles) {
            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {
                View view = super.getView(position, cachedView, parent);

                gravityText = (TextView) view.findViewById(R.id.gravityText);
                gravityText.setText(dilemmaGravities[position]);
                if (dilemmaGravities[position].equals("1")) {
                    gravityText.setBackgroundResource(R.drawable.gravity1_container);
                } else if (dilemmaGravities[position].equals("2")) {
                    gravityText.setBackgroundResource(R.drawable.gravity2_container);
                } else if (dilemmaGravities[position].equals("3")) {
                    gravityText.setBackgroundResource(R.drawable.gravity3_container);
                } else if (dilemmaGravities[position].equals("4")) {
                    gravityText.setBackgroundResource(R.drawable.gravity4_container);
                } else {
                    gravityText.setBackgroundResource(R.drawable.gravity5_container);
                }
                return view;
            }
        };
        dilemmaList.setAdapter(adapter);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();

        try {
            updateList(dController.getAllDilemmasArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_active_dilemmas) {

        } else if (id == R.id.nav_myDilemmas) {
            Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_answers) {
            Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_login) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else if (id == R.id.nav_register) {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        } else if (id == R.id.nav_editUser) {
            Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Toast.makeText(this, "Klik på " + position, Toast.LENGTH_SHORT).show();
    }
}


