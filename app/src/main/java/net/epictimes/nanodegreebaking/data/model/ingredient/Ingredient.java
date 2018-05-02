package net.epictimes.nanodegreebaking.data.model.ingredient;

/**
 Created by Mustafa Berkay Mutlu on 02.05.18.
 */
public class Ingredient {

    private String measure;

    private String ingredientName;

    private String quantity;

    public String getMeasure() {
        return measure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getQuantity() {
        return quantity;
    }

    public static final class IngredientBuilder {

        private String measure;
        private String ingredientName;
        private String quantity;

        private IngredientBuilder() {}

        public static IngredientBuilder anIngredient() { return new IngredientBuilder(); }

        public IngredientBuilder withMeasure(String measure) {
            this.measure = measure;
            return this;
        }

        public IngredientBuilder withIngredientName(String ingredientName) {
            this.ingredientName = ingredientName;
            return this;
        }

        public IngredientBuilder withQuantity(String quantity) {
            this.quantity = quantity;
            return this;
        }

        public Ingredient build() {
            Ingredient ingredient = new Ingredient();
            ingredient.measure = this.measure;
            ingredient.ingredientName = this.ingredientName;
            ingredient.quantity = this.quantity;
            return ingredient;
        }
    }
}
