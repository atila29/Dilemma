package app.grp13.dilemma;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.grp13.dilemma.application.ApplicationState;
import app.grp13.dilemma.logic.controller.AccountController;
import app.grp13.dilemma.logic.controller.IAccountControllerActivity;
import app.grp13.dilemma.logic.dto.Account;
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
public class RegisterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, IAccountControllerActivity {

    private EditText usernameText, passwordText, repasswordText;
    private Button registerBtn;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeUIElements();
        toolbar.setTitle("Registrer");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        registerBtn.setOnClickListener(this);
        ApplicationState.getInstance().setAccountActivityFocus(this);
        navigationView.setCheckedItem(R.id.nav_register);
        navigationView.setSelected(true);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //tjekker for om hamburgermenuen er åben. Hvis den er lukkes denne.
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
         //Sørger for at hvis hamburgermenuen er lukket, og man trykker tilbage, lukkes denne activity
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
            Intent intent = new Intent(RegisterActivity.this, DilemmaListActivity.class);
            intent.setAction(DilemmaListActivity.ACTION_DILEMMAS);
            startActivity(intent);
            // Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_answers) {
            finish();
            Intent intent = new Intent(RegisterActivity.this, DilemmaListActivity.class);
            intent.setAction(DilemmaListActivity.ACTION_REPLYS);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_login) {
            finish();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        } else if (id == R.id.nav_register) {

        } else if (id == R.id.nav_editUser) {
            finish();
            startActivity(new Intent(RegisterActivity.this, EditUserActivity.class));
            //Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        Matcher m = p.matcher(usernameText.getText().toString());
        if (v == registerBtn) {
            //sørger for at username og password boxene ikke er tomme, og password matcher gentag password.
            if (!usernameText.getText().toString().matches("") && !passwordText.getText().toString().matches("") &&
                    passwordText.getText().toString().matches(repasswordText.getText().toString())) {
                if(m.matches() && usernameText.getText().length()>7){
                    //forsøger at oprette den ønskede bruger
                    try {
                        ApplicationState.getInstance().getAccountController().createAccount(usernameText.getText().toString(), passwordText.getText().toString(), new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ApplicationState.getAppContext(), "Registrering fuldført!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                        //Oplyser brugeren om at noget gik galt.
                    } catch (DAOException e) {
                        Toast.makeText(this, "Noget gik galt! Tjek alle felter og prøv igen.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else{
                    Toast.makeText(this, "Email adresse ikke valid.", Toast.LENGTH_SHORT).show();
                }



            //Oplyser brugeren om at noget gik galt.
            } else {
                Toast.makeText(this, "Noget gik galt! Tjek alle felter og prøv igen.", Toast.LENGTH_SHORT).show();
            }


        }
    }

    //Initialisere alle vores ui elementer
    public void initializeUIElements(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        usernameText = (EditText) findViewById(R.id.regUsername);
        passwordText = (EditText) findViewById(R.id.regPassword);
        repasswordText = (EditText) findViewById(R.id.regRePassword);
        registerBtn = (Button) findViewById(R.id.registrerButton);
    }

    @Override
    public void ShowErrorMessage(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void accountAuthentication(Account acc) {

    }
}
