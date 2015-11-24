package app.grp13.dilemma;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView gravityText;
    String[] tempGravity, tempQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Aktive Dilemmaer");

        ListView dilemmaList = (ListView) findViewById(R.id.dilemmaList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateDilemma.class));
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
*/
            }
        });

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        } else if (id == R.id.nav_placeholder1) {

        } else if (id == R.id.nav_placeholder2) {

        } else if (id == R.id.nav_placeholder3) {

        } else if (id == R.id.nav_login) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else if (id == R.id.nav_register) {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Toast.makeText(this, "Klik på " + position, Toast.LENGTH_SHORT).show();
    }
}


