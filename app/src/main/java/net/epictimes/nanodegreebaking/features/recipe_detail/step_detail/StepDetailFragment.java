package net.epictimes.nanodegreebaking.features.recipe_detail.step_detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.epictimes.nanodegreebaking.R;
import net.epictimes.nanodegreebaking.features.BaseFragment;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
public class StepDetailFragment extends BaseFragment<StepDetailContract.View, StepDetailContract.Presenter> {

    public static StepDetailFragment newInstance() {
        return new StepDetailFragment();
    }

    @Inject
    StepDetailContract.Presenter stepDetailPresenter;

    @NonNull
    @Override
    public StepDetailContract.Presenter createPresenter() {
        return stepDetailPresenter;
    }

    @Override
    public void onAttach(final Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step_detail, container, false);
    }
}
