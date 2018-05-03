package net.epictimes.nanodegreebaking.data.model.step;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 Created by Mustafa Berkay Mutlu on 22.04.18.
 */
public class StepRaw {

    @SerializedName("id")
    private String id;

    @SerializedName("shortDescription")
    private String shortDescription;

    @SerializedName("description")
    private String description;

    @Nullable
    @SerializedName("videoURL")
    private String videoURL;

    @Nullable
    @SerializedName("thumbnailURL")
    private String thumbnailURL;

    public String getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    @Nullable
    public String getVideoURL() {
        return videoURL;
    }

    @Nullable
    public String getThumbnailURL() {
        return thumbnailURL;
    }

}
