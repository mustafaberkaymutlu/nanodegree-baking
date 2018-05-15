package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import java.util.List;

/**
 Created by Mustafa Berkay Mutlu on 03.05.18.
 */
class StepListViewEntity {

    private List<Visitable> items;

    private int selectedItemPosition;

    StepListViewEntity(final List<Visitable> items, final int selectedItemPosition) {
        this.items = items;
        this.selectedItemPosition = selectedItemPosition;
    }

    public List<Visitable> getItems() {
        return items;
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    public Visitable getSelectedItem() {
        return items.get(selectedItemPosition);
    }
}
