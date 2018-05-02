package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

/**
 Created by Mustafa Berkay Mutlu on 02.05.18.
 */
public class StepListItemViewEntity {

    private String id;

    private String shortDescription;

    private int position;

    public StepListItemViewEntity(final String id, final String shortDescription, final int position) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(final int position) {
        this.position = position;
    }
}
