package net.epictimes.nanodegreebaking.features.recipe_detail;


import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import net.epictimes.nanodegreebaking.BakingApp;
import net.epictimes.nanodegreebaking.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    public ActivityTestRule<RecipeDetailActivity> activityTestRule =
            new ActivityTestRule<RecipeDetailActivity>(RecipeDetailActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    final Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();

                    return RecipeDetailActivity.newIntent(targetContext, "3", "Yellow Cake");
                }
            };

    private IdlingResource idlingResource;

    @Before
    public void setUp() {
        activityTestRule.getActivity()
                .getFragmentManager().beginTransaction();

        final BakingApp application = (BakingApp) activityTestRule.getActivity().getApplication();
        idlingResource = application.getSingletonComponent().simpleIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    @Test
    public void stepListOpensStepDetail() {
        ViewInteraction textView = onView(
                allOf(withText("Yellow Cake"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                IsInstanceOf.instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Yellow Cake")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textViewStepShortDescription), withText("Recipe Introduction"),
                        childAtPosition(
                                allOf(withId(R.id.cardViewStep),
                                        childAtPosition(
                                                withId(R.id.recyclerViewSteps),
                                                1)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Recipe Introduction")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textViewStepShortDescription), withText("Recipe Introduction"),
                        childAtPosition(
                                allOf(withId(R.id.cardViewStep),
                                        childAtPosition(
                                                withId(R.id.recyclerViewSteps),
                                                1)),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Recipe Introduction")));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recyclerViewSteps),
                        childAtPosition(
                                withId(R.id.contentFrame),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction textView4 = onView(
                allOf(withText("Yellow Cake"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                IsInstanceOf.instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("Yellow Cake")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textViewStepDescription), withText("Recipe Introduction"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.contentFrame),
                                        0),
                                1),
                        isDisplayed()));
        textView5.check(matches(withText("Recipe Introduction")));

        ViewInteraction frameLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.playerView),
                                childAtPosition(
                                        IsInstanceOf.instanceOf(ViewGroup.class),
                                        0)),
                        0),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(final Matcher<View> parentMatcher,
                                                 final int position) {
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
