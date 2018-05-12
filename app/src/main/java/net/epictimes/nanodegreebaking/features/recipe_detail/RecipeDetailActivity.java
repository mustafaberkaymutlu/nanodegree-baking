package net.epictimes.nanodegreebaking.features.recipe_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import net.epictimes.nanodegreebaking.R;
import net.epictimes.nanodegreebaking.di.qualifier.IsTablet;
import net.epictimes.nanodegreebaking.features.recipe_detail.step_detail.StepDetailFragment;
import net.epictimes.nanodegreebaking.features.recipe_detail.step_list.StepListFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RecipeDetailActivity extends AppCompatActivity
        implements HasSupportFragmentInjector, StepListFragment.Listener {

    private static final String KEY_RECIPE_ID = "recipe_id";

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @IsTablet
    @Inject
    boolean isTablet;

    private String recipeId;

    public static Intent newIntent(@NonNull Context context, @NonNull String recipeId) {
        final Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(KEY_RECIPE_ID, recipeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipeId = getIntent().getStringExtra(KEY_RECIPE_ID);

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
}
