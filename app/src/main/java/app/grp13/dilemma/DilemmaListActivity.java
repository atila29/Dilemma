package app.grp13.dilemma;

import android.app.Fragment;
import android.app.FragmentTransaction;
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

import java.util.List;

import app.grp13.dilemma.application.ApplicationState;
import app.grp13.dilemma.logic.controller.IAccountControllerActivity;
import app.grp13.dilemma.logic.dto.Account;
import app.grp13.dilemma.logic.dto.IDilemma;
import app.grp13.dilemma.logic.dto.ListContainerSerializer;
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
public class DilemmaListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IAccountControllerActivity {

    TextView gravityText;
    String[] tempGravity, tempQuestion;
    ListView dilemmaList;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dilemma_list);
        ApplicationState.getInstance().setAccountActivityFocus(this); // nødvendigt for at authenticate brugere
        try {
            ApplicationState.getInstance().getAccountController().authenticate();
        } catch (LoginException e) {
            e.printStackTrace();
            // evt gå til login activity
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Mine dilemmaer");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

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

            // Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_answers) {
            Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
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
            //Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void ShowErrorMessage(Exception e) {

    }

    @Override
    public void showLoginToast(String msg) {

    }

    @Override
    public void accountAuthentication(Account acc) {
        Fragment fragment = new DilemmaListFragment();
        try {
            List<IDilemma> dilemmas = ApplicationState.getInstance().getDilemmaController().getDilemmaDAO().getSpecificDilemmas(acc.getMyDilemmas());
            ListContainerSerializer<IDilemma> list = new ListContainerSerializer(dilemmas);
            Bundle bundle = new Bundle();
            bundle.putSerializable("dilemmas", list);
            fragment.setArguments(bundle);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.nogetSjovt, fragment);
            ft.commit();
        } catch (DAOException e) {
            e.printStackTrace();
            // smid fejlmeddelelse
        } catch (IllegalStateException e){
            e.printStackTrace();
            // dette er ikke den rigtige måde at håndtere fejlen på.
            // den bør rettes
        }

    }
}