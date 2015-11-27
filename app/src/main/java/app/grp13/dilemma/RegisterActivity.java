package app.grp13.dilemma;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.grp13.dilemma.logic.controller.AccountController;

public class RegisterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    EditText usernameText, passwordText, repasswordText;
    Button registerBtn;
    AccountController accountController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Registrer");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        usernameText = (EditText) findViewById(R.id.regUsername);
        passwordText = (EditText) findViewById(R.id.regPassword);
        repasswordText = (EditText) findViewById(R.id.regRePassword);
        registerBtn = (Button) findViewById(R.id.registrerButton);
        registerBtn.setOnClickListener(this);
        accountController = new AccountController();

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
            startActivity(new Intent(RegisterActivity.this, DilemmaListActivity.class));
        } else if (id == R.id.nav_answers) {
            finish();
            startActivity(new Intent(RegisterActivity.this, DilemmaListActivity.class));
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_login) {
            finish();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        } else if (id == R.id.nav_register) {

        } else if (id == R.id.nav_editUser) {
            finish();
            startActivity(new Intent(RegisterActivity.this, EditUserActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        if (v == registerBtn) {
            if (!usernameText.getText().toString().matches("") || !passwordText.getText().toString().matches("") ||
                    !passwordText.getText().toString().matches(repasswordText.getText().toString())) {
                accountController.createAccount(usernameText.getText().toString(), passwordText.getText().toString(), 1);
                finish();

            } else {
                Toast.makeText(this, "Noget gik galt! Tjek alle felter og prøv igen.", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
