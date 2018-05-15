package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import net.epictimes.nanodegreebaking.R;
import net.epictimes.nanodegreebaking.util.ItemClickListener;
import net.epictimes.nanodegreebaking.util.SpannableStringUtil;

/**
 Created by Mustafa Berkay Mutlu on 28.04.18.
 */
class StepViewHolder extends StepRecyclerViewAdapter.AbstractViewHolder<StepItemViewEntity> {

    static final int LAYOUT_ID = R.layout.item_step;

    private final CardView cardViewStep;
    private final TextView textViewStepShortDescription;

    StepViewHolder(final View itemView, final ItemClickListener itemClickListener) {
        super(itemView);

        cardViewStep = itemView.findViewById(R.id.cardViewStep);
        textViewStepShortDescription = itemView.findViewById(R.id.textViewStepShortDescription);

        itemView.setOnClickListener(v -> itemClickListener.onItemClicked(getAdapterPosition()));
    }

    @Override
    public void bind(@NonNull StepItemViewEntity step) {
        final String wholeDesc;

        if (step.isIntroduction()) {
            wholeDesc = step.getShortDescription();
            textViewStepShortDescription.setGravity(Gravity.CENTER);
        } else {
            wholeDesc = step.getPosition() + " " + step.getShortDescription();
            textViewStepShortDescription.setGravity(Gravity.START);
        }

        final SpannableString spannedDesc = SpannableStringUtil.spanInternal(wholeDesc,
                                                                             String.valueOf(step.getPosition()),
                                                                             new StyleSpan(Typeface.BOLD));
        textViewStepShortDescription.setText(spannedDesc);

        final Context context = textViewStepShortDescription.getContext();
        if (step.isSelected()) {
            cardViewStep.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
            textViewStepShortDescription.setTextColor(ContextCompat.getColor(context, android.R.color.white));
        } else {
            cardViewStep.setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
            textViewStepShortDescription.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        }
    }

}
