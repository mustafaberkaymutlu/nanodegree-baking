package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 Created by Mustafa Berkay Mutlu on 07.05.18.
 */
public class StepListDiffUtilCallback extends DiffUtil.Callback {

    private final List<StepItemViewEntity> oldItems;
    private final List<StepItemViewEntity> newItems;

    StepListDiffUtilCallback(final List<StepItemViewEntity> oldItems,
                             final List<StepItemViewEntity> newItems) {
        this.oldItems = oldItems;
        this.newItems = newItems;
    }

    @Override
    public int getOldListSize() {
        return oldItems.size();
    }

    @Override
    public int getNewListSize() {
        return newItems.size();
    }

    @Override
    public boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
        return oldItems.get(oldItemPosition).getId().equals(newItems.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
        final StepItemViewEntity oldItem = oldItems.get(oldItemPosition);
        final StepItemViewEntity newItem = newItems.get(newItemPosition);

        return oldItem.getId().equals(newItem.getId())
                && oldItem.isSelected() == newItem.isSelected()
                && oldItem.isIntroduction() == newItem.isIntroduction()
                && oldItem.getPosition() == newItem.getPosition()
                && oldItem.getShortDescription().equals(newItem.getShortDescription());

    }
}
