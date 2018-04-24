package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

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
public class StepListFragment extends BaseFragment<StepListContract.View, StepListContract.Presenter> {

    public static StepListFragment newInstance() {
        return new StepListFragment();
    }

    @Inject
    StepListContract.Presenter stepListPresenter;

    @NonNull
    @Override
    public StepListContract.Presenter createPresenter() {
        return stepListPresenter;
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
        return inflater.inflate(R.layout.fragment_step_list, container, false);
    }
}
