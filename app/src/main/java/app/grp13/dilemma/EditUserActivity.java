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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
/*
Lavet af:
Sazvan Kasim Ali - S144884
Mathias Petersen - S144874
Bao Duy Nguyen - S144880
Christian Jappe - S144866
Magnus Nielsen - S141899
Nicolai Hansen - S133974
*/
public class EditUserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Rediger bruger");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
}