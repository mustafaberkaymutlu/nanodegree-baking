package net.epictimes.nanodegreebaking.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
public class Recipe {

    @SerializedName("ingredients")
    private List<Ingredient> ingredients;

    @SerializedName("id")
    private String id;

    @SerializedName("servings")
    private String servings;

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    @SerializedName("steps")
    private List<Step> steps;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(final List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(final String servings) {
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(final List<Step> steps) {
        this.steps = steps;
    }
}
