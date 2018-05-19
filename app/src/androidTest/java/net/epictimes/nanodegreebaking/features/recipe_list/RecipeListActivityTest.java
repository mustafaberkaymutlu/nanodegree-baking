package net.epictimes.nanodegreebaking.features.recipe_list;


import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import net.epictimes.nanodegreebaking.BakingApp;
import net.epictimes.nanodegreebaking.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
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
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> activityTestRule = new ActivityTestRule<>(RecipeListActivity.class);

    private IdlingResource idlingResource;

    @Before
    public void setUp() {
        final BakingApp application = (BakingApp) activityTestRule.getActivity().getApplication();
        idlingResource = application.getSingletonComponent().simpleIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    @Test
    public void recyclerViewItemOpensStepListScreen() {
        ViewInteraction recyclerView = onView(withId(R.id.recyclerViewRecipes));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.toolbar)).check(matches(withToolbarTitle("Nutella Pie")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textViewIngredients),
                        childAtPosition(
                                allOf(withId(R.id.cardViewStep),
                                        childAtPosition(
                                                withId(R.id.recyclerViewSteps),
                                                0)),
                                0),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textViewStepShortDescription),
                        childAtPosition(
                                allOf(withId(R.id.cardViewStep),
                                        childAtPosition(
                                                withId(R.id.recyclerViewSteps),
                                                1)),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Recipe Introduction")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textViewStepShortDescription),
                        childAtPosition(
                                allOf(withId(R.id.cardViewStep),
                                        childAtPosition(
                                                withId(R.id.recyclerViewSteps),
                                                2)),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("1 Starting prep")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textViewStepShortDescription),
                        childAtPosition(
                                allOf(withId(R.id.cardViewStep),
                                        childAtPosition(
                                                withId(R.id.recyclerViewSteps),
                                                3)),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("2 Prep the cookie crust.")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.textViewStepShortDescription),
                        childAtPosition(
                                allOf(withId(R.id.cardViewStep),
                                        childAtPosition(
                                                withId(R.id.recyclerViewSteps),
                                                3)),
                                0),
                        isDisplayed()));
        textView6.check(matches(withText("2 Prep the cookie crust.")));
    }

    @Test
    public void opensAllTheWayToStepDetailScreen() {
        ViewInteraction recyclerView = onView(withId(R.id.recyclerViewRecipes));
        recyclerView.perform(actionOnItemAtPosition(2, click()));

        onView(withId(R.id.toolbar)).check(matches(withToolbarTitle("Yellow Cake")));

        ViewInteraction textView2 = onView(allOf(withId(R.id.textViewStepShortDescription),
                withText("Recipe Introduction"), isDisplayed()));
        textView2.check(matches(withText("Recipe Introduction")));
        textView2.check(matches(isDisplayed()));

        ViewInteraction recyclerView2 = onView(withId(R.id.recyclerViewSteps));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));

        onView(withId(R.id.toolbar)).check(matches(withToolbarTitle("Yellow Cake")));

        ViewInteraction textView5 = onView(withId(R.id.textViewStepDescription));
        textView5.check(matches(withText("Recipe Introduction")));
        textView5.check(matches(isDisplayed()));

        ViewInteraction frameLayout = onView(withId(R.id.playerView));
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
