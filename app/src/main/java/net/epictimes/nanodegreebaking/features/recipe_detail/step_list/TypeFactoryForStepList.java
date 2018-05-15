package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import android.view.View;
import net.epictimes.nanodegreebaking.util.ItemClickListener;

import javax.inject.Inject;

/**
 * Created by Mustafa Berkay Mutlu on 15.05.18.
 */
class TypeFactoryForStepList implements TypeFactory {

    @Inject
    TypeFactoryForStepList() {
    }

    @Override
    public int type(IngredientsViewEntity ingredientsViewEntity) {
        return IngredientsViewHolder.LAYOUT_ID;
    }

    @Override
    public int type(StepItemViewEntity stepItemViewEntity) {
        return StepViewHolder.LAYOUT_ID;
    }

    @Override
    public StepRecyclerViewAdapter.AbstractViewHolder createViewHolder(View rootView,
                                                                       int viewType,
                                                                       ItemClickListener itemClickListener) {
        switch (viewType) {
            case IngredientsViewHolder.LAYOUT_ID:
                return new IngredientsViewHolder(rootView);
            case StepViewHolder.LAYOUT_ID:
                return new StepViewHolder(rootView, itemClickListener);
        }

        throw new IllegalArgumentException("Unknown view type: " + viewType);
    }
}
