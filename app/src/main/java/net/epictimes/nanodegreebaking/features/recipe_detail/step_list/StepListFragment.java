package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.epictimes.nanodegreebaking.R;
import net.epictimes.nanodegreebaking.features.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 Created by Mustafa Berkay Mutlu on 24.04.18.
 */
public class StepListFragment extends BaseFragment<StepListContract.View, StepListContract.Presenter>
        implements StepListContract.View {

    private static final String ARG_RECIPE_ID = "recipe_id";

    private Listener fragmentListener;
    private StepRecyclerViewAdapter adapter;

    public interface Listener {

        void openStepDetail(String stepId);

    }

    public static StepListFragment newInstance(final String recipeId) {
        final StepListFragment stepListFragment = new StepListFragment();
        final Bundle args = new Bundle();
        args.putString(ARG_RECIPE_ID, recipeId);
        stepListFragment.setArguments(args);
        return stepListFragment;
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

        try {
            fragmentListener = (Listener) context;
        } catch (ClassCastException ignored) {
            throw new ClassCastException(context.toString() + " must implement Listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step_list, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Bundle args = getArguments();
        final String recipeId = args.getString(ARG_RECIPE_ID);

        final RecyclerView recyclerViewSteps = view.findViewById(R.id.recyclerViewSteps);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new StepRecyclerViewAdapter();

        adapter.setStepClickListener(step -> presenter.userClickedRecipeStep(recipeId, step.getId()));

        recyclerViewSteps.setLayoutManager(linearLayoutManager);
        recyclerViewSteps.setAdapter(adapter);
        recyclerViewSteps.setHasFixedSize(true);

        presenter.getRecipeSteps(recipeId);
    }

    @Override
    public void displaySteps(final List<StepListItemViewEntity> steps) {
        adapter.addAll(steps);
    }

    @Override
    public void displayStepError() {
        Toast.makeText(getContext(), R.string.error_steps, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openStepDetail(final String stepId) {
        fragmentListener.openStepDetail(stepId);
    }
}
