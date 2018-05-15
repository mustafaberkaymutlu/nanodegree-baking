package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import net.epictimes.nanodegreebaking.data.model.ingredient.Ingredient;

import java.util.List;

/**
 * Created by Mustafa Berkay Mutlu on 13.05.18.
 */
class IngredientsViewEntity implements Visitable {

    private List<Ingredient> ingredientList;

    IngredientsViewEntity(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    public static String getReadableIngredient(Ingredient ingredient) {
        return ingredient.getIngredientName() + ", " + ingredient.getQuantity() + " " + ingredient.getMeasure();
    }
}
