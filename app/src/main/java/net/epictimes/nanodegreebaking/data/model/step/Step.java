package net.epictimes.nanodegreebaking.data.model.step;

import android.support.annotation.Nullable;

/**
 Created by Mustafa Berkay Mutlu on 02.05.18.
 */
public class Step {

    private String id;

    private String shortDescription;

    private String description;

    @Nullable
    private String videoURL;

    @Nullable
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

    public static final class StepBuilder {

        private String id;
        private String shortDescription;
        private String description;

        @Nullable
        private String videoURL;

        @Nullable
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

        private StepBuilder() {}

        public static StepBuilder aStep() { return new StepBuilder(); }

        public StepBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public StepBuilder withShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public StepBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public StepBuilder withVideoURL(@Nullable String videoURL) {
            this.videoURL = videoURL;
            return this;
        }

        public StepBuilder withThumbnailURL(@Nullable String thumbnailURL) {
            this.thumbnailURL = thumbnailURL;
            return this;
        }

        public Step build() {
            Step step = new Step();
            step.thumbnailURL = this.thumbnailURL;
            step.shortDescription = this.shortDescription;
            step.description = this.description;
            step.videoURL = this.videoURL;
            step.id = this.id;
            return step;
        }
    }
}
