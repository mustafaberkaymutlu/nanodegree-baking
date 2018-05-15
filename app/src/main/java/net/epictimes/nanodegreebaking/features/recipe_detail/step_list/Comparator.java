package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

/**
 * Created by Mustafa Berkay Mutlu on 15.05.18.
 */
public interface Comparator {

    boolean areItemsTheSame(Visitable item1, Visitable item2);

    boolean areContentsTheSame(Visitable item1, Visitable item2);

}
