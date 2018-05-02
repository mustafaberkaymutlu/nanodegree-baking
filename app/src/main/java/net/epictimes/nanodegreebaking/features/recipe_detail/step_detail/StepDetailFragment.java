package net.epictimes.nanodegreebaking.features.recipe_detail.step_detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.epictimes.nanodegreebaking.R;
import net.epictimes.nanodegreebaking.data.model.step.Step;
import net.epictimes.nanodegreebaking.features.BaseFragment;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
public class StepDetailFragment extends BaseFragment<StepDetailContract.View, StepDetailContract.Presenter>
        implements StepDetailContract.View {

    private static final String ARG_RECIPE_ID = "recipe_id";
    private static final String ARG_STEP_ID = "step_id";

    private TextView textViewStepDescription;

    public static StepDetailFragment newInstance(@Nullable final String recipeId, @Nullable String stepId) {
        final StepDetailFragment stepDetailFragment = new StepDetailFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_RECIPE_ID, recipeId);
        args.putString(ARG_STEP_ID, stepId);
        stepDetailFragment.setArguments(args);
        return stepDetailFragment;
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

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewStepDescription = view.findViewById(R.id.textViewStepDescription);

        final Bundle args = getArguments();
        final String recipeId = args.getString(ARG_RECIPE_ID);
        final String stepId = args.getString(ARG_STEP_ID);

        presenter.displayStep(recipeId, stepId);
    }

    @Override
    public void displayStepDetail(Step step) {
        textViewStepDescription.setText(step.getDescription());
    }

    @Override
    public void displayStepError() {
        Toast.makeText(getContext(), R.string.error_step, Toast.LENGTH_SHORT).show();
    }
}
