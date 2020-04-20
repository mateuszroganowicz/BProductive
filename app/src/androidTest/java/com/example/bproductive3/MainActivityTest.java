package com.example.bproductive3;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_mood), withContentDescription("Mood"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.button_cool),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        2),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_music), withContentDescription("Music"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.navigation_todolist), withContentDescription("To do list"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.addTaskButton),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.taskDesc),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.taskDesc),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("task1"), closeSoftKeyboard());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withText("Low"),
                        childAtPosition(
                                allOf(withId(R.id.priorityRadioGroup),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                5)),
                                0),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.add_button), withText("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.addTaskButton),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.taskDesc),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.taskDesc),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("task2"), closeSoftKeyboard());

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withText("Medium"),
                        childAtPosition(
                                allOf(withId(R.id.priorityRadioGroup),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                5)),
                                1),
                        isDisplayed()));
        appCompatRadioButton2.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.add_button), withText("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.addTaskButton),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatRadioButton3 = onView(
                allOf(withText("High"),
                        childAtPosition(
                                allOf(withId(R.id.priorityRadioGroup),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                5)),
                                2),
                        isDisplayed()));
        appCompatRadioButton3.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.taskDesc),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText5.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.taskDesc),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("task3"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.add_button), withText("ADD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction bottomNavigationItemView4 = onView(
                allOf(withId(R.id.navigation_appinfo), withContentDescription("AppInfo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                4),
                        isDisplayed()));
        bottomNavigationItemView4.perform(click());

        ViewInteraction bottomNavigationItemView5 = onView(
                allOf(withId(R.id.navigation_mood), withContentDescription("Mood"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView5.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.button_stress),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatImageButton5.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
