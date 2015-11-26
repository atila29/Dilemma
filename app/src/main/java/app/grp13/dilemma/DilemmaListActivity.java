package app.grp13.dilemma;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import app.grp13.dilemma.logic.dto.IDilemma;

public class DilemmaListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView gravityText;
    String[] tempGravity, tempQuestion;
    ListView dilemmaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dilemma_list);
        dilemmaList = (ListView) findViewById(R.id.specificDilemmaList);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Din liste");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        tempQuestion = new String[5];
        tempQuestion[0] = "Tester 1";
        tempQuestion[1] = "Tester 2";
        tempQuestion[2] = "Tester 3";
        tempQuestion[3] = "Tester 4";
        tempQuestion[4] = "Tester 5";
        tempGravity = new String[5];
        tempGravity[0] = "1";
        tempGravity[1] = "2";
        tempGravity[2] = "3";
        tempGravity[3] = "4";
        tempGravity[4] = "5";

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_dilemma_row, R.id.questionText, tempQuestion) {
            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {
                View view = super.getView(position, cachedView, parent);

                gravityText = (TextView) view.findViewById(R.id.gravityText);
                gravityText.setText(tempGravity[position]);
                if (tempGravity[position].equals("1")) {
                    gravityText.setBackgroundResource(R.drawable.gravity1_container);
                } else if (tempGravity[position].equals("2")) {
                    gravityText.setBackgroundResource(R.drawable.gravity2_container);
                } else if (tempGravity[position].equals("3")) {
                    gravityText.setBackgroundResource(R.drawable.gravity3_container);
                } else if (tempGravity[position].equals("4")) {
                    gravityText.setBackgroundResource(R.drawable.gravity4_container);
                } else {
                    gravityText.setBackgroundResource(R.drawable.gravity5_container);
                }
                return view;
            }
        };

        //dilemmaList.setOnItemClickListener();
        dilemmaList.setAdapter(adapter);
    }

    public void updateList(IDilemma[] array){
        String[] tempTitle = new String[array.length];
        final String[] tempGravity = new String[array.length];
        for(int i=0 ; i<array.length ; i++){
            tempTitle[i] = array[i].getTitle();
            tempGravity[i] = String.valueOf(array[i].getgravity());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_dilemma_row, R.id.questionText, tempTitle) {
            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {
                View view = super.getView(position, cachedView, parent);

                gravityText = (TextView) view.findViewById(R.id.gravityText);
                gravityText.setText(tempGravity[position]);
                if (tempGravity[position].equals("1")) {
                    gravityText.setBackgroundResource(R.drawable.gravity1_container);
                } else if (tempGravity[position].equals("2")) {
                    gravityText.setBackgroundResource(R.drawable.gravity2_container);
                } else if (tempGravity[position].equals("3")) {
                    gravityText.setBackgroundResource(R.drawable.gravity3_container);
                } else if (tempGravity[position].equals("4")) {
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
            finish();
        } else if (id == R.id.nav_myDilemmas) {
            finish();
            startActivity(new Intent(DilemmaListActivity.this, DilemmaListActivity.class));
        } else if (id == R.id.nav_answers) {
            finish();
            startActivity(new Intent(DilemmaListActivity.this, DilemmaListActivity.class));
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_login) {
            finish();
            startActivity(new Intent(DilemmaListActivity.this, LoginActivity.class));
        } else if (id == R.id.nav_register) {
            finish();
            startActivity(new Intent(DilemmaListActivity.this, RegisterActivity.class));
        } else if (id == R.id.nav_editUser) {
            finish();
            startActivity(new Intent(DilemmaListActivity.this, EditUserActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}