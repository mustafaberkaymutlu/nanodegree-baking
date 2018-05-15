package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import android.view.View;
import android.widget.TextView;
import net.epictimes.nanodegreebaking.R;
import net.epictimes.nanodegreebaking.data.model.ingredient.Ingredient;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mustafa Berkay Mutlu on 13.05.18.
 */
class IngredientsViewHolder extends StepRecyclerViewAdapter.AbstractViewHolder<IngredientsViewEntity> {

    static final int LAYOUT_ID = R.layout.item_ingredients;

    private final TextView textViewIngredients;

    IngredientsViewHolder(View itemView) {
        super(itemView);

        textViewIngredients = itemView.findViewById(R.id.textViewIngredients);
    }

    @Override
    public void bind(IngredientsViewEntity ingredientsViewEntity) {
        textViewIngredients.setText(getIngredients(ingredientsViewEntity.getIngredientList()));
    }

    private String getIngredients(List<Ingredient> ingredientList) {
        final List<String> list = new ArrayList<>();

        for (Ingredient ingredient : ingredientList) {
            list.add(IngredientsViewEntity.getReadableIngredient(ingredient));
        }

        return StringUtils.join(list, "\n");
    }
}
