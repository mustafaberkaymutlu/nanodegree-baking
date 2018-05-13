package net.epictimes.nanodegreebaking.features.recipe_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.epictimes.nanodegreebaking.R;
import net.epictimes.nanodegreebaking.di.qualifier.IsTablet;
import net.epictimes.nanodegreebaking.features.recipe_detail.step_detail.StepDetailFragment;
import net.epictimes.nanodegreebaking.features.recipe_detail.step_list.StepListFragment;
import net.epictimes.nanodegreebaking.util.Preconditions;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RecipeDetailActivity extends AppCompatActivity implements
        HasSupportFragmentInjector,
        StepListFragment.Listener,
        StepDetailFragment.Listener {

    private static final String KEY_RECIPE_ID = "recipe_id";
    private static final String KEY_RECIPE_NAME = "recipe_name";

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private Toolbar toolbar;

    @IsTablet
    @Inject
    boolean isTablet;

    private String recipeId;

    public static Intent newIntent(@NonNull Context context, @NonNull String recipeId, @NonNull String recipeName) {
        final Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(KEY_RECIPE_ID, recipeId);
        intent.putExtra(KEY_RECIPE_NAME, recipeName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        toolbar = findViewById(R.id.toolbar);

        final Bundle extras = Preconditions.checkNotNull(getIntent().getExtras(), "Extras must not be null. ");
        recipeId = extras.getString(KEY_RECIPE_ID);
        final String recipeName = extras.getString(KEY_RECIPE_NAME);

        toolbar.setTitle(recipeName);

        if (isTablet) {
            attachTabletFragments();
        } else {
            attachPhoneFragments();
        }
    }

    private void attachTabletFragments() {
        final boolean isContentFrameListEmpty = isFragmentDetached(R.id.contentFrame_list);
        final boolean isContentFrameDetailEmpty = isFragmentDetached(R.id.contentFrame_detail);

        if (isContentFrameListEmpty) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentFrame_list, StepListFragment.newInstance(recipeId))
                    .commit();
        }

        if (isContentFrameDetailEmpty) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentFrame_detail, StepDetailFragment.newInstance(recipeId, null))
                    .commit();
        }
    }

    private void attachPhoneFragments() {
        final boolean isContentFrameEmpty = isFragmentDetached(R.id.contentFrame);

        if (isContentFrameEmpty) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentFrame, StepListFragment.newInstance(recipeId))
                    .commit();
        }
    }

    @Override
    public void openStepDetail(final String stepId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentFrame, StepDetailFragment.newInstance(recipeId, stepId))
                .addToBackStack(null)
                .commit();
    }

    private boolean isFragmentDetached(int fragmentId) {
        return getSupportFragmentManager().findFragmentById(fragmentId) == null;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void goFullScreen() {
        toolbar.setVisibility(View.GONE);
    }
}
