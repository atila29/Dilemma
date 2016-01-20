package app.grp13.dilemma.logic.controller;

import android.app.Instrumentation;
import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.ServiceTestCase;
import android.util.Log;

import com.firebase.client.Firebase;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Method;

import app.grp13.dilemma.application.ApplicationState;
import app.grp13.dilemma.logic.dto.Account;

import static org.junit.Assert.*;

/**
 * Created by LuxMiz on 1/19/2016.
 */
public class AccountControllerTest extends InstrumentationTestCase{

    private Context getTestContext()
    {
        try
        {
            Method getTestContext = ServiceTestCase.class.getMethod("getTestContext");
            return (Context) getTestContext.invoke(this);
        }
        catch (final Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
    }


    @Before
    public void setUp() throws Exception {
        Firebase.setAndroidContext(ApplicationState.getAppContext());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSaveAccount() throws Exception {

    }

    @Test
    public void testCreateAccount() throws Exception {

    }

    @Test
    public void testChangeUserPass() throws Exception {

    }

    @Test
    public void testLogin() throws Exception {

        AccountController ac = new AccountController(new IAccountControllerActivity() {
            @Override
            public void ShowErrorMessage(Exception e) {
            }

            @Override
            public void showLoginToast(String msg) {

            }

            @Override
            public void accountAuthentication(Account acc) {
            }
        });

        ac.createAccount("hejsa123@dtu.dk", "kode", new Runnable() {
            @Override
            public void run() {

            }
        });

        ac.login("hejsa123@dtu.dk", "kode");
        ac.authenticate();

    }

    @Test
    public void testChangeUserMail() throws Exception {

    }

    @Test
    public void testAuthenticate() throws Exception {

    }

    @Test
    public void testDeleteAccount() throws Exception {

    }

    @Test
    public void testLogout() throws Exception {

    }
}