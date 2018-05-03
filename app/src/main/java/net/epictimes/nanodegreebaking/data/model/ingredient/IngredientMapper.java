package net.epictimes.nanodegreebaking.data.model.ingredient;

import org.apache.commons.lang3.StringUtils;

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
        final StringBuilder stringBuilder = new StringBuilder();

        if (StringUtils.isBlank(ingredientRaw.getMeasure())) {
            stringBuilder.append("measure cannot be empty, ");
        }

        if (StringUtils.isBlank(ingredientRaw.getIngredientName())) {
            stringBuilder.append("ingredient cannot be empty, ");
        }

        if (StringUtils.isBlank(ingredientRaw.getQuantity())) {
            stringBuilder.append("quantity cannot be empty. ");
        }

        final String message = stringBuilder.toString();
        if (StringUtils.isNotBlank(message)) {
            throw new IllegalStateException(message);
        }
    }
}
