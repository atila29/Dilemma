package app.grp13.dilemma;

import android.app.ActionBar;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import app.grp13.dilemma.logic.controller.AccountController;
import app.grp13.dilemma.logic.exceptions.LoginException;

public class LoginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    Button loginBtn;
    AccountController ac = new AccountController();
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Log ind");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        try {
            ac.loadUsersFromDevice(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loginBtn = (Button) findViewById(R.id.login);
        loginBtn.setOnClickListener(this);
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
            startActivity(new Intent(LoginActivity.this, DilemmaListActivity.class));
        } else if (id == R.id.nav_answers) {
            finish();
            startActivity(new Intent(LoginActivity.this, DilemmaListActivity.class));
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_login) {

        } else if (id == R.id.nav_register) {
            finish();
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        } else if (id == R.id.nav_editUser) {
            finish();
            startActivity(new Intent(LoginActivity.this, EditUserActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == loginBtn) {
            if(!username.getText().toString().matches("") && !password.getText().toString().matches("")){
                try{
                    ac.login(username.getText().toString(), password.getText().toString());
                    finish();
                } catch (LoginException e) {
                    Toast.makeText(this, "Noget gik galt! Tjek dit brugernavn og password og forsøg igen.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(this, "Noget gik galt! Tjek dit brugernavn og password og forsøg igen.", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
