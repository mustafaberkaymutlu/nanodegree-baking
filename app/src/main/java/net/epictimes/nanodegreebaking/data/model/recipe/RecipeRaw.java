package net.epictimes.nanodegreebaking.data.model.recipe;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import net.epictimes.nanodegreebaking.data.model.ingredient.IngredientRaw;
import net.epictimes.nanodegreebaking.data.model.step.StepRaw;

import java.util.List;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
public class RecipeRaw {

    @SerializedName("id")
    private String id;

    @SerializedName("ingredients")
    private List<IngredientRaw> ingredients;

    @SerializedName("servings")
    private String servings;

    @SerializedName("name")
    private String name;

    @Nullable
    @SerializedName("image")
    private String image;

    @SerializedName("steps")
    private List<StepRaw> steps;

    public String getId() {
        return id;
    }

    public List<IngredientRaw> getIngredients() {
        return ingredients;
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

    public List<StepRaw> getSteps() {
        return steps;
    }

}
