package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import java.util.List;

/**
 Created by Mustafa Berkay Mutlu on 03.05.18.
 */
public class StepListViewEntity {

    private List<StepItemViewEntity> stepItemViewEntityList;

    private int selectedItemPosition;

    public StepListViewEntity(final List<StepItemViewEntity> stepItemViewEntityList, final int selectedItemPosition) {
        this.stepItemViewEntityList = stepItemViewEntityList;
        this.selectedItemPosition = selectedItemPosition;
    }

    public List<StepItemViewEntity> getStepItemViewEntityList() {
        return stepItemViewEntityList;
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }
}
