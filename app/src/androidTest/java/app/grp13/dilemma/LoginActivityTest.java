package app.grp13.dilemma;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.junit.BeforeClass;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by champen on 20-01-2016.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception{
        super.setUp();
        getActivity();
    }

    @SmallTest
    public void testInitialize() throws Exception {
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        TextView rT = (TextView) getActivity().findViewById(R.id.registerText);
        Button loginBtn = (Button) getActivity().findViewById(R.id.login);
        LinearLayout loadingView = (LinearLayout) getActivity().findViewById(R.id.loadingView);
        TextView logoutText = (TextView) getActivity().findViewById(R.id.emailTextView);
        Button logoutButton = (Button) getActivity().findViewById(R.id.logoutButton);
        LinearLayout loginView = (LinearLayout) getActivity().findViewById(R.id.loginView);
        LinearLayout logoutView = (LinearLayout) getActivity().findViewById(R.id.logoutView);
        EditText username = (EditText) getActivity().findViewById(R.id.username);
        EditText password = (EditText) getActivity().findViewById(R.id.password);

        assertNotNull(drawer);
        assertNotNull(navigationView);
        assertNotNull(rT);
        assertNotNull(loginBtn);
        assertNotNull(loadingView);
        assertNotNull(logoutText);
        assertNotNull(logoutButton);
        assertNotNull(loginView);
        assertNotNull(logoutView);
        assertNotNull(username);
        assertNotNull(password);
    }


    // kræver man ikke er logget ind, derfor uninstall app før test bliver kørt.
    @SmallTest
    public void testLogin() throws Exception{
        String mail = "testlogin_0_@testclass.dk";
        String pass = "kode";

        onView(withId(R.id.username)).perform(typeText(mail), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(pass), closeSoftKeyboard());

        assertEquals(mail, ((EditText) getActivity().findViewById(R.id.username)).getText().toString());
        assertEquals(pass, ((EditText)getActivity().findViewById(R.id.password)).getText().toString());

        onView(withId(R.id.login)).perform(click());
    }


}
