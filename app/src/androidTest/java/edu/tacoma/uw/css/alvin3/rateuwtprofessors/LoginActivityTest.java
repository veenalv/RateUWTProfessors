/**
 *
 * TCSS 450 - Spring 2018 Team 8.
 * @author Alvin Nguyen
 * @author Maksim B Voznyarskiy
 * @author Hui Ting Cai
 */
package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Test of the LoginActivity
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    /** Test the LoginActivity with invalid password. */
    @Test
    public void testLoginInvalidPassword(){
        onView(withId(R.id.email))
                .perform(typeText("maksimv@uw.edu"));
        onView(withId(R.id.password))
                .perform(typeText(""));
        onView(withId(R.id.SignIn))
                .perform(click());
        onView(withText("Password must be 6 to 15 digits long"))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().
                        getWindow().getDecorView())))).check(matches(isDisplayed()));


    }

    /**
     * Test the LoginActivity with invalid email.
     */
    @Test
    public void testLoginInvalidEmail(){
        onView(withId(R.id.email))
                .perform(typeText("maksimv.edu"));
        onView(withId(R.id.password))
                .perform(typeText("skittles"));
        onView(withId(R.id.SignIn))
                .perform(click());
        onView(withText("must use uw.edu email"))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().
                        getWindow().getDecorView())))).check(matches(isDisplayed()));


    }

    /**
     * Test the login with valid email and password.
     */
    @Test
    public void testLoginvalid(){
        onView(withId(R.id.email))
                .perform(typeText("maksimv@uw.edu"));
        onView(withId(R.id.password))
                .perform(typeText("skittles"));
        onView(withId(R.id.SignIn))
                .perform(click());
        onView(withText("successful login"))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().
                        getWindow().getDecorView())))).check(matches(isDisplayed()));

    }

}
