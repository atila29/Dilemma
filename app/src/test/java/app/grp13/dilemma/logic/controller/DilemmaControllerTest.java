package app.grp13.dilemma.logic.controller;

import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Created by LuxMiz & Mathias on 1/19/2016.
 */

public class DilemmaControllerTest{

    private DilemmaController testObject;

    @Before
    public void setUp() throws Exception {
        testObject = new DilemmaController();
    }

    @After
    public void tearDown() throws Exception {
        testObject = null;
    }

    @Test
    public void testSetup() throws Exception {
        assertNotNull(testObject);
        assertTrue(testObject instanceof DilemmaController);
    }

    @Test
    public void testCreateDilemma() throws Exception {

    }

    @Test
    public void testGetAllDilemmas() throws Exception {

    }

    @Test
    public void testGetAllDilemmasArray() throws Exception {

    }

    @Test
    public void testDeleteDilemma() throws Exception {

    }

    @Test
    public void testGetDilemma() throws Exception {

    }

    @Test
    public void testAnswerDilemma() throws Exception {

    }

    @Test
    public void testGetDilemmaKey() throws Exception {

    }

    @Test
    public void testSaveDilemmasToDevice() throws Exception {

    }

    @Test
    public void testLoadDilemmasFromDevice() throws Exception {

    }

    @Test
    public void testGetDilemmaDAO() throws Exception {

    }
}