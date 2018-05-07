package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

/**
 Created by Mustafa Berkay Mutlu on 02.05.18.
 */
public class StepItemViewEntity {

    private String id;

    private String shortDescription;

    private int position;

    private boolean isIntroduction;

    private boolean isSelected;

    public String getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public int getPosition() {
        return position;
    }

    public boolean isIntroduction() {
        return isIntroduction;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public static final class Builder {

        private String id;
        private String shortDescription;
        private int position;
        private boolean isIntroduction;
        private boolean isSelected;

        private Builder() {}

        public static Builder aStepItemViewEntity() { return new Builder(); }

        public Builder withStepItemViewEntity(StepItemViewEntity stepItemViewEntity) {
            withId(stepItemViewEntity.getId());
            withShortDescription(stepItemViewEntity.getShortDescription());
            withPosition(stepItemViewEntity.getPosition());
            withIsIntroduction(stepItemViewEntity.isIntroduction());
            withIsSelected(stepItemViewEntity.isSelected());
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public Builder withPosition(int position) {
            this.position = position;
            return this;
        }

        public Builder withIsIntroduction(boolean isIntroduction) {
            this.isIntroduction = isIntroduction;
            return this;
        }

        public Builder withIsSelected(boolean isSelected) {
            this.isSelected = isSelected;
            return this;
        }

        public StepItemViewEntity build() {
            StepItemViewEntity stepItemViewEntity = new StepItemViewEntity();
            stepItemViewEntity.id = this.id;
            stepItemViewEntity.isIntroduction = this.isIntroduction;
            stepItemViewEntity.shortDescription = this.shortDescription;
            stepItemViewEntity.position = this.position;
            stepItemViewEntity.isSelected = this.isSelected;
            return stepItemViewEntity;
        }
    }
}
