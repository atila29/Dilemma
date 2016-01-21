package app.grp13.dilemma;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import app.grp13.dilemma.application.ApplicationState;
import app.grp13.dilemma.logic.controller.AccountController;
import app.grp13.dilemma.logic.controller.IAccountControllerActivity;
import app.grp13.dilemma.logic.dao.DilemmaFirebaseDAO;
import app.grp13.dilemma.logic.dao.IDilemmaDAO;
import app.grp13.dilemma.logic.dto.Account;
import app.grp13.dilemma.logic.exceptions.DAOException;
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
public class LoginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, IAccountControllerActivity{

    private Button loginBtn;
    private EditText username;
    private EditText password;
    private TextView rT;
    private LinearLayout logoutView;
    private LinearLayout loginView;
    private Button logoutButton;
    private TextView logoutText;
    private LinearLayout loadingView;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ApplicationState.getInstance().setAccountActivityFocus(this);
        initializeUIElements();
        toolbar.setTitle("Log ind");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        rT.setText(Html.fromHtml("Har du ikke en bruger i forvejen? Klik <u><font color='#0000FF'>her</font></u> for at registrerer!"));
        rT.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
        navigationView.setCheckedItem(R.id.nav_login);
        navigationView.setSelected(true);
        //Til censor. Forindtastet login
        username.setText("censor@dtu.dk");
        password.setText("kode");
        //tjekker for om du er logget på i forvejen. Hvis ja, sættes viewet til "logOutView".
        try {
            ApplicationState.getInstance().getAccountController().authenticate();
        } catch (LoginException e) {
            e.printStackTrace();
            loginView.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Eksamens version. En virkende bruger er indtastet for dig. Tryk blot på login knappen for at logge ind.", Toast.LENGTH_LONG).show();
        }
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
            Intent intent = new Intent(LoginActivity.this, DilemmaListActivity.class);
            intent.setAction(DilemmaListActivity.ACTION_DILEMMAS);
            startActivity(intent);
            // Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_answers) {
            finish();
            Intent intent = new Intent(LoginActivity.this, DilemmaListActivity.class);
            intent.setAction(DilemmaListActivity.ACTION_REPLYS);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_login) {

        } else if (id == R.id.nav_register) {
            finish();
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        } else if (id == R.id.nav_editUser) {
            finish();
            startActivity(new Intent(LoginActivity.this, EditUserActivity.class));
            //Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == loginBtn) {
            //Sørger for at brugeren har internet for ikke at ende i en uendelig visning af loading.
            if(ApplicationState.getInstance().getDilemmaController().getDilemmaDAO().isConnected()){
                loginView.setVisibility(View.GONE);
                loadingView.setVisibility(View.VISIBLE);
                //sørger herefter for at brugernavn og adgangskode felterne ikke er tomme
                if(!username.getText().toString().matches("") && !password.getText().toString().matches("")){
                    //Prøver at logge ind med de ønskede indtastede oplysninger
                    try {
                        ApplicationState.getInstance().getAccountController().login(username.getText().toString(), password.getText().toString());
                    //Hvis indtastede oplysninger er forkerte fanges vores exception, som oplyser brugeren om at noget gik galt. Viewet bliver herefter sat til det normale logIn view
                    } catch (LoginException e) {
                        e.printStackTrace();
                        loadingView.setVisibility(View.GONE);
                        loginView.setVisibility(View.VISIBLE);
                    }
                }
                //Tjekker for om brugeren er connected til firebase (Databasen). Denne connection skulle ske i main. Oplyser brugeren hvis han/hun ikke er connected
                else if(ApplicationState.getInstance().getDilemmaController().getDilemmaDAO().isConnected()){
                    Toast.makeText(this, "Problemer med forbindelsen. Tjek venligst din internetadgang, eller prøv igen senere", Toast.LENGTH_LONG).show();
                    loadingView.setVisibility(View.GONE);
                    loginView.setVisibility(View.VISIBLE);
                }
                //Sørger for at oplyse brugeren om at noget gik galt.
                else{
                    Toast.makeText(this, "Noget gik galt! Tjek dit brugernavn og password og forsøg igen.", Toast.LENGTH_SHORT).show();
                    loadingView.setVisibility(View.GONE);
                    loginView.setVisibility(View.VISIBLE);
                }
            }
            //Sørger for at oplyse brugeren om at han/hun ikke har internet
            else{
                Toast.makeText(this, "Problemer med forbindelsen. Tjek venligst din internetadgang, eller prøv igen senere", Toast.LENGTH_LONG).show();
                loadingView.setVisibility(View.GONE);
                loginView.setVisibility(View.VISIBLE);
            }
        }

        //Hvis brugeren trykker på teksten "her" for at registrerer sig, åbnes register activity og login activity afsluttes.
        if(v == rT){
            finish();
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
        //logger brugeren ud hvis han i forvejen er logget ind, og trykker på log ud knappen.
        if(v == logoutButton){
            ApplicationState.getInstance().getAccountController().logout();
            Toast.makeText(getApplicationContext(), "Du er blevet logget ud", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    //Initialisere alle vores ui elementer
    public void initializeUIElements(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        rT = (TextView) findViewById(R.id.registerText);
        loginBtn = (Button) findViewById(R.id.login);
        loadingView = (LinearLayout) findViewById(R.id.loadingView);
        logoutText = (TextView) findViewById(R.id.emailTextView);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        loginView = (LinearLayout) findViewById(R.id.loginView);
        logoutView = (LinearLayout) findViewById(R.id.logoutView);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
    }

    @Override
    public void ShowErrorMessage(Exception e) {
        Toast.makeText(this, "Noget gik galt! Tjek dit brugernavn og password og forsøg igen.", Toast.LENGTH_SHORT).show();
        loadingView.setVisibility(View.GONE);
        loginView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoginToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void accountAuthentication(Account acc) {
        logoutText.setText(acc.getUserName());
        loginView.setVisibility(View.GONE);
        logoutView.setVisibility(View.VISIBLE);
    }
}
