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

public class RecipeDetailActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    private static final String KEY_ID = "id";

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @IsTablet
    @Inject
    boolean isTablet;

    public static Intent newIntent(@NonNull Context context, @NonNull String id) {
        final Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(KEY_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

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
                    .add(R.id.contentFrame_list, StepListFragment.newInstance())
                    .commit();
        }

        if (isContentFrameDetailEmpty) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentFrame_detail, StepDetailFragment.newInstance())
                    .commit();
        }
    }

    private void attachPhoneFragments() {
        final boolean isContentFrameEmpty = isFragmentDetached(R.id.contentFrame);

        if (isContentFrameEmpty) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentFrame, StepListFragment.newInstance())
                    .commit();
        }
    }

    private boolean isFragmentDetached(int fragmentId) {
        return getSupportFragmentManager().findFragmentById(fragmentId) == null;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
