package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.epictimes.nanodegreebaking.data.model.Step;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 Created by Mustafa Berkay Mutlu on 28.04.18.
 */
public class StepRecyclerViewAdapter extends RecyclerView.Adapter<StepViewHolder> {

    private final List<Step> stepList = new ArrayList<>();

    @Nullable
    private StepClickListener stepClickListener;

    interface StepClickListener {

        void onStepClicked(@NonNull Step step);

    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(StepViewHolder.LAYOUT_ID, parent, false);
        return new StepViewHolder(view, adapterPosition -> {
            if (stepClickListener != null) {
                stepClickListener.onStepClicked(stepList.get(adapterPosition));
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull final StepViewHolder holder, final int position) {
        final Step step = stepList.get(position);
        holder.bind(step);
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    void addAll(Collection<Step> newItems) {
        final int previousSize = stepList.size();
        stepList.addAll(newItems);
        notifyItemRangeInserted(previousSize, newItems.size());
    }

    public void setStepClickListener(@Nullable final StepClickListener stepClickListener) {
        this.stepClickListener = stepClickListener;
    }
}
