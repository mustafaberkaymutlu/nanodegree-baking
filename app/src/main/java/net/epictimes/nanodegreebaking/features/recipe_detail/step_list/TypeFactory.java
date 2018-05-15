package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import android.view.View;

import net.epictimes.nanodegreebaking.util.ItemClickListener;

/**
 * Created by Mustafa Berkay Mutlu on 15.05.18.
 */
public interface TypeFactory {

    int type(IngredientsViewEntity ingredientsViewEntity);

    int type(StepItemViewEntity stepItemViewEntity);

    StepRecyclerViewAdapter.AbstractViewHolder createViewHolder(View rootView,
                                                                int viewType,
                                                                ItemClickListener onItemClicked);
}
