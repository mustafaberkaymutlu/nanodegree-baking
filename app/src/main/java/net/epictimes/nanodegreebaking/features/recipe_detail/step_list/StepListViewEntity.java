package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import java.util.List;

/**
 Created by Mustafa Berkay Mutlu on 03.05.18.
 */
public class StepListViewEntity {

    private List<StepItemViewEntity> stepItemViewEntityList;

    public StepListViewEntity(final List<StepItemViewEntity> stepItemViewEntityList) {
        this.stepItemViewEntityList = stepItemViewEntityList;
    }

    public List<StepItemViewEntity> getStepItemViewEntityList() {
        return stepItemViewEntityList;
    }
}
