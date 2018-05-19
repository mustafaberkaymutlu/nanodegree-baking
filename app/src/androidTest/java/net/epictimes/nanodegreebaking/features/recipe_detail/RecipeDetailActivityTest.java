package net.epictimes.nanodegreebaking.features.recipe_detail;


import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import net.epictimes.nanodegreebaking.BakingApp;
import net.epictimes.nanodegreebaking.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
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
import static org.hamcrest.Matchers.is;

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
        onView(withId(R.id.toolbar)).check(matches(withToolbarTitle("Yellow Cake")));

        ViewInteraction recyclerView2 = onView(withId(R.id.recyclerViewSteps));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));

        onView(withId(R.id.toolbar)).check(matches(withToolbarTitle("Yellow Cake")));

        ViewInteraction frameLayout = onView(withId(R.id.playerView));
        frameLayout.check(matches(isDisplayed()));
    }


    public static Matcher<View> withToolbarTitle(CharSequence title) {
        return withToolbarTitle(is(title));
    }

    public static Matcher<View> withToolbarTitle(final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<View, Toolbar>(Toolbar.class) {
            @Override
            public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }
}
