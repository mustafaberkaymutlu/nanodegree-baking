package net.epictimes.nanodegreebaking.features.recipe_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.epictimes.nanodegreebaking.data.model.recipe.Recipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 Created by Mustafa Berkay Mutlu on 23.04.18.
 */
public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private final List<Recipe> recipeList = new ArrayList<>();

    @Nullable
    private RecipeClickListener recipeClickListener;

    public interface RecipeClickListener {

        void onRecipeClicked(@NonNull Recipe recipe);

    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(RecipeViewHolder.LAYOUT_ID, parent, false);
        return new RecipeViewHolder(view, adapterPosition -> {
            if (recipeClickListener != null) {
                recipeClickListener.onRecipeClicked(recipeList.get(adapterPosition));
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, final int position) {
        final Recipe recipe = recipeList.get(position);
        holder.bindTo(recipe);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    void addAll(Collection<Recipe> newItems) {
        final int previousSize = recipeList.size();
        recipeList.addAll(newItems);
        notifyItemRangeInserted(previousSize, newItems.size());
    }

    public void setRecipeClickListener(@Nullable final RecipeClickListener recipeClickListener) {
        this.recipeClickListener = recipeClickListener;
    }
}
