package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 Created by Mustafa Berkay Mutlu on 07.05.18.
 */
public class StepListDiffUtilCallback extends DiffUtil.Callback {
    private final Comparator comparator;
    private final List<Visitable> oldItems;
    private final List<Visitable> newItems;

    StepListDiffUtilCallback(final Comparator comparator,
                             final List<Visitable> oldItems,
                             final List<Visitable> newItems) {
        this.comparator = comparator;
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
        return comparator.areItemsTheSame(oldItems.get(oldItemPosition),
                newItems.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
        return comparator.areContentsTheSame(oldItems.get(oldItemPosition),
                newItems.get(newItemPosition));
    }
}
