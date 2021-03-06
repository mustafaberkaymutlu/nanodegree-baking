package net.epictimes.nanodegreebaking.features.recipe_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.epictimes.nanodegreebaking.R;
import net.epictimes.nanodegreebaking.data.model.recipe.Recipe;
import net.epictimes.nanodegreebaking.util.ItemClickListener;

import io.reactivex.annotations.NonNull;

/**
 Created by Mustafa Berkay Mutlu on 23.04.18.
 */
class RecipeViewHolder extends RecyclerView.ViewHolder {

    static final int LAYOUT_ID = R.layout.item_recipe;

    private final TextView textViewRecipeName;

    RecipeViewHolder(final View itemView, final ItemClickListener itemClickListener) {
        super(itemView);

        textViewRecipeName = itemView.findViewById(R.id.textViewRecipeName);

        itemView.setOnClickListener(v -> itemClickListener.onItemClicked(getAdapterPosition()));
    }

    void bindTo(@NonNull Recipe recipe) {
        textViewRecipeName.setText(recipe.getName());
    }
}
