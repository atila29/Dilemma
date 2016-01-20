package app.grp13.dilemma;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ScrollView;

import org.junit.BeforeClass;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by champen on 19-01-2016.
 */
public class RegisterActivityTest extends ActivityInstrumentationTestCase2<RegisterActivity> {
    public RegisterActivityTest() {
        super(RegisterActivity.class);
    }

    @BeforeClass
    protected void setUp() throws Exception{
        super.setUp();
        getActivity();
    }

    @SmallTest
    public void testInitialize()throws Exception{
        ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.login_form);
        AutoCompleteTextView username = (AutoCompleteTextView)getActivity().findViewById(R.id.regUsername);
        EditText password = (EditText)getActivity().findViewById(R.id.regPassword);
        EditText repassword = (EditText)getActivity().findViewById(R.id.regRePassword);
        assertNotNull(password);
        assertNotNull(username);
        assertNotNull(scrollView);
        assertNotNull(repassword);
    }

    @SmallTest
    public void testRegister() throws Exception {
        // dog skal der tages højde for at man kun kan registreres EN gang pr. mail
        String mail = "testlogin_1_@testclass.dk";
        String pass = "kode";

        onView(withId(R.id.regUsername)).perform(typeText(mail), closeSoftKeyboard());
        onView(withId(R.id.regPassword)).perform(typeText(pass), closeSoftKeyboard());
        onView(withId(R.id.regRePassword)).perform(typeText(pass), closeSoftKeyboard());

        AutoCompleteTextView regUname = (AutoCompleteTextView)getActivity().findViewById(R.id.regUsername);
        assertEquals(mail, regUname.getText().toString());
        assertEquals(pass, ((EditText)getActivity().findViewById(R.id.regPassword)).getText().toString());
        assertEquals(pass, ((EditText)getActivity().findViewById(R.id.regRePassword)).getText().toString());

        onView(withId(R.id.registrerButton)).perform(click());
    }

}
