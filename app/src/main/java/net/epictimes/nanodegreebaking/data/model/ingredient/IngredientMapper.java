package net.epictimes.nanodegreebaking.data.model.ingredient;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 Created by Mustafa Berkay Mutlu on 02.05.18.
 */
public class IngredientMapper implements Function<IngredientRaw, Ingredient> {

    @Inject
    public IngredientMapper() {
    }

    @Override
    public Ingredient apply(final IngredientRaw ingredientRaw) {
        validateFields(ingredientRaw);

        return Ingredient.IngredientBuilder.anIngredient()
                                           .withIngredientName(ingredientRaw.getIngredientName())
                                           .withMeasure(ingredientRaw.getMeasure())
                                           .withQuantity(ingredientRaw.getQuantity())
                                           .build();
    }

    private void validateFields(final IngredientRaw ingredientRaw) {
        // TODO validate fields
    }
}
