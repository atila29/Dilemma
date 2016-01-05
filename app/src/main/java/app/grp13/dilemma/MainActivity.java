package app.grp13.dilemma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.grp13.dilemma.logic.controller.AccountController;
import app.grp13.dilemma.logic.controller.DilemmaController;
import app.grp13.dilemma.logic.dao.DilemmaFirebaseDAO;
import app.grp13.dilemma.logic.dao.IDilemmaDAO;
import app.grp13.dilemma.logic.dto.BasicDilemma;
import app.grp13.dilemma.logic.dto.IAnswer;
import app.grp13.dilemma.logic.dto.IDilemma;
import app.grp13.dilemma.logic.dto.IReply;
import app.grp13.dilemma.logic.exceptions.DAOException;

/*
Lavet af:
Sazvan Kasim Ali - S144884
Mathias Petersen - S144874
Bao Duy Nguyen - S144880
Christian Jappe - S144866
Magnus Nielsen - S141899
Nicolai Hansen - S133974
*/


public class MainActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView gravityText;
    private ListView dilemmaList;
    private DilemmaController dController;
    private AccountController aController;
    private ProgressBar prog;
    private String[] dilemmaTitles;
    private String[] dilemmaGravities;
    private RelativeLayout loadingView;

    private IDilemmaDAO dilemmaDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Aktive Dilemmaer");
        loadingView = (RelativeLayout) findViewById(R.id.loadingView);
        aController = new AccountController();
        dController = new DilemmaController();
        dilemmaDAO = new DilemmaFirebaseDAO();


        dilemmaList = (ListView) findViewById(R.id.dilemmaList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dController.saveDilemmasToDevice(getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

        //loading bar
        prog = (ProgressBar) findViewById(R.id.progressBar2);
        loadList();
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
            dController = new DilemmaController(dilemmaDAO.getDilemmas());
            updateList(dController.getAllDilemmasArray());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DAOException e) {
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

    public void errorToast(String str){
        Context context = getApplicationContext();
        CharSequence text =str;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Toast.makeText(this, "Klik på " + position, Toast.LENGTH_SHORT).show();
    }

    public void loadList(){
        //Loading bar
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!dilemmaDAO.isLoading() && dilemmaDAO.isConnected()){
                    errorToast("Loading complete!");
                    loadingView.setVisibility(View.GONE);
                    findViewById(R.id.fab).setVisibility(View.VISIBLE);
                    findViewById(R.id.fab).setVisibility(View.VISIBLE);
                    onResume();
                }
                if(!dilemmaDAO.isLoading() && !dilemmaDAO.isConnected()){
                    errorToast("Connection error. Check internet connection. If your internet connection is on, our servers might be down.");
                    loadingView.setVisibility(View.GONE);
                    findViewById(R.id.fab).setVisibility(View.VISIBLE);
                    findViewById(R.id.fab).setVisibility(View.VISIBLE);
                }
            }
        }, 4000); //Find smartere metode til at tjekke når isloading er færdig og isconnected er færdig?
    }
}


