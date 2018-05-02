package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.epictimes.nanodegreebaking.R;
import net.epictimes.nanodegreebaking.data.model.Step;
import net.epictimes.nanodegreebaking.util.ItemClickListener;

/**
 Created by Mustafa Berkay Mutlu on 28.04.18.
 */
class StepViewHolder extends RecyclerView.ViewHolder {

    static final int LAYOUT_ID = R.layout.item_step;

    private final TextView textViewStepName;

    StepViewHolder(final View itemView, final ItemClickListener itemClickListener) {
        super(itemView);

        textViewStepName = itemView.findViewById(R.id.textViewStepName);

        itemView.setOnClickListener(v -> itemClickListener.onItemClicked(getAdapterPosition()));
    }

    void bind(@NonNull Step step){
        textViewStepName.setText(step.getDescription());
    }

}
