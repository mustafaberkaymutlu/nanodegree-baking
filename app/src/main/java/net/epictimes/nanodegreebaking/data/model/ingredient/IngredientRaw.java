package net.epictimes.nanodegreebaking.data.model.ingredient;

import com.google.gson.annotations.SerializedName;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
public class IngredientRaw {

    @SerializedName("measure")
    private String measure;

    @SerializedName("ingredient")
    private String ingredientName;

    @SerializedName("quantity")
    private String quantity;

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(final String measure) {
        this.measure = measure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(final String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(final String quantity) {
        this.quantity = quantity;
    }
}
