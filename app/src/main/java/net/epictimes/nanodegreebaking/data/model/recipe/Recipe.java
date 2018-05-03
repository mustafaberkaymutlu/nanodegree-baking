package net.epictimes.nanodegreebaking.data.model.recipe;

import android.support.annotation.Nullable;

import net.epictimes.nanodegreebaking.data.model.ingredient.Ingredient;
import net.epictimes.nanodegreebaking.data.model.step.Step;

import java.util.List;

/**
 Created by Mustafa Berkay Mutlu on 02.05.18.
 */
public class Recipe {

    private List<Ingredient> ingredients;

    private String id;

    private String servings;

    private String name;

    @Nullable
    private String image;

    private List<Step> steps;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getId() {
        return id;
    }

    public String getServings() {
        return servings;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getImage() {
        return image;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public static final class RecipeBuilder {

        private List<Ingredient> ingredients;
        private String id;
        private String servings;
        private String name;

        @Nullable
        private String image;

        private List<Step> steps;

        private RecipeBuilder() {}

        public static RecipeBuilder aRecipe() { return new RecipeBuilder(); }

        public RecipeBuilder withIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public RecipeBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public RecipeBuilder withServings(String servings) {
            this.servings = servings;
            return this;
        }

        public RecipeBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public RecipeBuilder withImage(@Nullable String image) {
            this.image = image;
            return this;
        }

        public RecipeBuilder withSteps(List<Step> steps) {
            this.steps = steps;
            return this;
        }

        public Recipe build() {
            Recipe recipe = new Recipe();
            recipe.name = this.name;
            recipe.ingredients = this.ingredients;
            recipe.servings = this.servings;
            recipe.id = this.id;
            recipe.steps = this.steps;
            recipe.image = this.image;
            return recipe;
        }
    }
}
