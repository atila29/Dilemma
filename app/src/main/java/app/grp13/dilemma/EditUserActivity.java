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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import app.grp13.dilemma.application.ApplicationState;
import app.grp13.dilemma.logic.controller.AccountController;
import app.grp13.dilemma.logic.controller.IAccountControllerActivity;
import app.grp13.dilemma.logic.dto.Account;
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
public class EditUserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IAccountControllerActivity{

    private ScrollView normalEditUser;
    private ScrollView adminEditUser;
    private LinearLayout chooseEditUser;
    private LinearLayout specificEditUser;
    private TextView emailTextView;
    private EditText newEmailTextBox;
    private EditText currentPasswordTextBox;
    private EditText newPasswordTextBox;
    private EditText reNewPasswordTextBox;
    private Button updateUserButton;
    private Account account;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        initializeUIElements();
        ApplicationState.getInstance().setAccountActivityFocus(this);
        //Authenticater brugeren for at sikre han er logget på (Man må kun ændrer en bruger hvis man er logget på)
        try {
            ApplicationState.getInstance().getAccountController().authenticate();
        } catch (LoginException e) {
            e.printStackTrace();
            emailTextView.setText("Ikke logget ind");
            updateUserButton.setClickable(false);
            Toast.makeText(this, "Du er ikke logget ind", Toast.LENGTH_SHORT).show();
        }
        updateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tjekker for at brugeren kun vil skifte email (ny password boksen er tom). Opdaterer emailen hvis informationer stemmer
                if (!newEmailTextBox.getText().toString().matches("") && (newPasswordTextBox.getText().toString().matches(""))) {
                    Log.v("FHL", "kørt");
                    ApplicationState.getInstance().getAccountController().changeUserMail(account.getUserName(), newEmailTextBox.getText().toString(), currentPasswordTextBox.getText().toString());
                    //showUpdateToast();
                }
                //Tjekker for om brugeren kun vil skifte password (ny email boksen er tom).
                else if (!(newPasswordTextBox.getText().toString().matches("")) && newEmailTextBox.getText().toString().matches("")) {
                    //Tjekker for at de to indtastede kodeord "Nye kodeord" og "Gentag nye kodeord" stemmer. Opdaterer kodeordet hvis informationer stemmer
                    if (newPasswordTextBox.getText().toString().equals(reNewPasswordTextBox.getText().toString())) {
                        ApplicationState.getInstance().getAccountController().changeUserPass(account.getUserName(), currentPasswordTextBox.getText().toString(), newPasswordTextBox.getText().toString(), new Runnable() {
                            @Override
                            public void run() {
                                showUpdateToast();
                            }
                        });
                    }
                    //Fortæller brugeren at de to indtastede kodeord ikke stemmer
                    else {
                        Toast.makeText(getApplicationContext(), "passwords matcher ikke", Toast.LENGTH_SHORT).show();
                    }
                }
                //Tjekker for om brugeren vil ændre både email og kodeord.
                else if (!newEmailTextBox.getText().toString().matches("") && !(newPasswordTextBox.getText().toString().matches(""))) {
                    //tjekker for om de to indtastede nye passwords er de samme. Hvis ja, opdateres brugerens informationer
                    if (newPasswordTextBox.getText().toString().equals(reNewPasswordTextBox.getText().toString())) {
                        ApplicationState.getInstance().getAccountController().changeUserMail(account.getUserName(), newEmailTextBox.getText().toString(), currentPasswordTextBox.getText().toString());
                        ApplicationState.getInstance().getAccountController().changeUserPass(account.getUserName(), currentPasswordTextBox.getText().toString(), newPasswordTextBox.getText().toString(), new Runnable() {
                            @Override
                            public void run() {
                                showUpdateToast();
                            }
                        });
                    }
                    //Fortæller brugeren at de to indtastede kodeord ikke stemmer
                    else {
                        Toast.makeText(getApplicationContext(), "passwords matcher ikke", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        toolbar.setTitle("Rediger bruger");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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
//            finish();
            Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_answers) {
//            finish();
            Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_login) {
            finish();
            startActivity(new Intent(EditUserActivity.this, LoginActivity.class));
        } else if (id == R.id.nav_register) {
            finish();
            startActivity(new Intent(EditUserActivity.this, RegisterActivity.class));
        } else if (id == R.id.nav_editUser) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Initialisere alle vores ui elementer
    public void initializeUIElements(){
        normalEditUser = (ScrollView) findViewById(R.id.scrollView2);
        adminEditUser =(ScrollView) findViewById(R.id.adminEditUser);
        chooseEditUser = (LinearLayout) findViewById(R.id.chooseEditUser);
        specificEditUser = (LinearLayout) findViewById(R.id.specificEditUser);
        emailTextView = (TextView) findViewById(R.id.emailTW);
        newEmailTextBox = (EditText) findViewById(R.id.newEmailText);
        currentPasswordTextBox = (EditText) findViewById(R.id.currentPassword);
        newPasswordTextBox = (EditText) findViewById(R.id.newPassword);
        reNewPasswordTextBox = (EditText) findViewById(R.id.renewPassword);
        updateUserButton = (Button) findViewById(R.id.updateUserButton);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    @Override
    public void ShowErrorMessage(Exception e) {
        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginToast(String msg) {
        account.setUserName(msg);
        ApplicationState.getInstance().getAccountController().saveAccount(account);
        showUpdateToast();
    }

    private void showUpdateToast(){
        Toast.makeText(getApplicationContext(), "bruger opdateret succesfuldt", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void accountAuthentication(Account acc) {
        account = acc;
        emailTextView.setText(acc.getUserName());
    }
}