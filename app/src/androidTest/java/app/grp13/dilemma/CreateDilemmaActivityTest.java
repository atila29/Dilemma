package app.grp13.dilemma;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import com.robotium.solo.Solo;

import static android.support.test.espresso.Espresso.onView;

/**
 * Created by champen on 20-01-2016.
 */
public class CreateDilemmaActivityTest extends ActivityInstrumentationTestCase2<CreateDilemma>{


    public CreateDilemmaActivityTest() {
        super(CreateDilemma.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    @SmallTest
    public void testInitialize() throws Exception {
        EditText answer1 = (EditText) getActivity().findViewById(R.id.answer1);
        EditText answer2 = (EditText) getActivity().findViewById(R.id.answer2);
        EditText answer3 = (EditText) getActivity().findViewById(R.id.answer3);
        EditText answer4 = (EditText) getActivity().findViewById(R.id.answer4);
        EditText answer5 = (EditText) getActivity().findViewById(R.id.answer5);
        Button gravity1Btn = (Button) getActivity().findViewById(R.id.gravity1Btn);
        Button gravity2Btn = (Button) getActivity().findViewById(R.id.gravity2Btn);
        Button gravity3Btn = (Button) getActivity().findViewById(R.id.gravity3Btn);
        Button gravity4Btn = (Button) getActivity().findViewById(R.id.gravity4Btn);
        Button gravity5Btn = (Button) getActivity().findViewById(R.id.gravity5Btn);
        Button createDilemma = (Button) getActivity().findViewById(R.id.createDilButton);
        EditText dilemmaName = (EditText) getActivity().findViewById(R.id.dilemmaName);
        EditText dilemmaDesc = (EditText) getActivity().findViewById(R.id.dilemmaDesc);

        assertNotNull(answer1);
        assertNotNull(answer2);
        assertNotNull(answer3);
        assertNotNull(answer4);
        assertNotNull(answer5);
        assertNotNull(gravity1Btn);
        assertNotNull(gravity2Btn);
        assertNotNull(gravity3Btn);
        assertNotNull(gravity4Btn);
        assertNotNull(gravity5Btn);
        assertNotNull(createDilemma);
        assertNotNull(dilemmaName);
        assertNotNull(dilemmaDesc);

        assertTrue(gravity1Btn.hasOnClickListeners());
        assertTrue(gravity2Btn.hasOnClickListeners());
        assertTrue(gravity3Btn.hasOnClickListeners());
        assertTrue(gravity4Btn.hasOnClickListeners());
        assertTrue(gravity5Btn.hasOnClickListeners());
        assertTrue(createDilemma.hasOnClickListeners());

    }

    @SmallTest
    public void testCreateDilemmaWith2Answers() throws Exception {
        String title = "Test Dilemma_2_ @ " + this.getClass().getSimpleName();
        String description = "Description @" + this.getClass().getSimpleName();

        String answer1 = "answer 1";
        String answer2 = "answer 2";

        onView(withId(R.id.dilemmaName)).perform(typeText(title), closeSoftKeyboard());
        onView(withId(R.id.dilemmaDesc)).perform(typeText(description), closeSoftKeyboard());
        onView(withId(R.id.answer1)).perform(typeText(answer1), closeSoftKeyboard());
        onView(withId(R.id.answer2)).perform(scrollTo(), typeText(answer2), closeSoftKeyboard());


        onView(withId(R.id.gravity5Btn)).perform(click());

        assertEquals(title, ((EditText) getActivity().findViewById(R.id.dilemmaName)).getText().toString());
        assertEquals(description, ((EditText) getActivity().findViewById(R.id.dilemmaDesc)).getText().toString());
        assertEquals(answer1, ((EditText) getActivity().findViewById(R.id.answer1)).getText().toString());
        assertEquals(answer2, ((EditText) getActivity().findViewById(R.id.answer2)).getText().toString());

        onView(withId(R.id.createDilButton)).perform(scrollTo(), click());

    }

    @SmallTest
    public void testCreateDilemmaWith5Answers() throws Exception {
        String title = "Test 5 Dilemma_0_ @ " + this.getClass().getSimpleName();
        String description = "Description 5 @ " + this.getClass().getSimpleName();

        String answer1 = "answer 1";
        String answer2 = "answer 2";
        String answer3 = "answer 3";
        String answer4 = "answer 4";
        String answer5 = "answer 5";

        onView(withId(R.id.dilemmaName)).perform(typeText(title), closeSoftKeyboard());
        onView(withId(R.id.dilemmaDesc)).perform(typeText(description), closeSoftKeyboard());
        onView(withId(R.id.answer1)).perform(typeText(answer1), closeSoftKeyboard());
        onView(withId(R.id.answer2)).perform(scrollTo(), typeText(answer2), closeSoftKeyboard());
        onView(withId(R.id.answer3)).perform(scrollTo(), typeText(answer3), closeSoftKeyboard());
        onView(withId(R.id.answer4)).perform(scrollTo(), typeText(answer4), closeSoftKeyboard());
        onView(withId(R.id.answer5)).perform(scrollTo(), typeText(answer5), closeSoftKeyboard());

        onView(withId(R.id.gravity5Btn)).perform(scrollTo(), click());

        assertEquals(title, ((EditText) getActivity().findViewById(R.id.dilemmaName)).getText().toString());
        assertEquals(description, ((EditText) getActivity().findViewById(R.id.dilemmaDesc)).getText().toString());
        assertEquals(answer1, ((EditText) getActivity().findViewById(R.id.answer1)).getText().toString());
        assertEquals(answer2, ((EditText) getActivity().findViewById(R.id.answer2)).getText().toString());
        assertEquals(answer3, ((EditText) getActivity().findViewById(R.id.answer3)).getText().toString());
        assertEquals(answer4, ((EditText) getActivity().findViewById(R.id.answer4)).getText().toString());
        assertEquals(answer5, ((EditText) getActivity().findViewById(R.id.answer5)).getText().toString());

        onView(withId(R.id.createDilButton)).perform(scrollTo(), click());
    }

}
