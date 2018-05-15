package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import javax.inject.Inject;

/**
 * Created by Mustafa Berkay Mutlu on 15.05.18.
 */
class VisitableComparator implements Comparator {

    @Inject
    VisitableComparator() {
    }

    @Override
    public boolean areItemsTheSame(Visitable item1, Visitable item2) {
        if (item1 instanceof StepItemViewEntity && item2 instanceof StepItemViewEntity) {
            final StepItemViewEntity i1 = (StepItemViewEntity) item1;
            final StepItemViewEntity i2 = (StepItemViewEntity) item2;

            return i1.getId().equals(i2.getId());
        } else if (item1 instanceof IngredientsViewEntity && item2 instanceof IngredientsViewEntity) {
            // we are not updating ingredients
            return true;
        }

        return false;
    }

    @Override
    public boolean areContentsTheSame(Visitable item1, Visitable item2) {
        if (item1 instanceof StepItemViewEntity && item2 instanceof StepItemViewEntity) {
            final StepItemViewEntity i1 = (StepItemViewEntity) item1;
            final StepItemViewEntity i2 = (StepItemViewEntity) item2;

            return i1.getId().equals(i2.getId())
                    && i1.isSelected() == i2.isSelected()
                    && i1.isIntroduction() == i2.isIntroduction()
                    && i1.getPosition() == i2.getPosition()
                    && i1.getShortDescription().equals(i2.getShortDescription());
        } else if (item1 instanceof IngredientsViewEntity && item2 instanceof IngredientsViewEntity) {
            // we are not updating ingredients
            return true;
        }

        return false;
    }
}
