package net.epictimes.nanodegreebaking.features.recipe_detail.step_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 Created by Mustafa Berkay Mutlu on 28.04.18.
 */
class StepRecyclerViewAdapter extends RecyclerView.Adapter<StepRecyclerViewAdapter.AbstractViewHolder> {

    private final List<Visitable> elements = new ArrayList<>();

    private final TypeFactory typeFactory;
    private final Comparator comparator;

    @Nullable
    private VisitableClickListener visitableClickListener;

    StepRecyclerViewAdapter(TypeFactory typeFactory, Comparator comparator) {
        this.typeFactory = typeFactory;
        this.comparator = comparator;
    }

    interface VisitableClickListener {

        void onVisitableClicked(@NonNull Visitable visitable);

    }

    @NonNull
    @Override
    public StepRecyclerViewAdapter.AbstractViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View rootView = inflater.inflate(viewType, parent, false);
        return typeFactory.createViewHolder(rootView, viewType, this::onItemClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepRecyclerViewAdapter.AbstractViewHolder holder, final int position) {
        holder.bind(elements.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return elements.get(position).type(typeFactory);
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    private void onItemClicked(int adapterPosition) {
        if (visitableClickListener != null) {
            visitableClickListener.onVisitableClicked(elements.get(adapterPosition));
        }
    }

    void update(@NonNull final List<Visitable> newItems) {
        final StepListDiffUtilCallback diffUtilCallback = new StepListDiffUtilCallback(comparator, elements, newItems);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);

        this.elements.clear();
        this.elements.addAll(newItems);

        diffResult.dispatchUpdatesTo(this);
    }

    public void setVisitableClickListener(@Nullable VisitableClickListener visitableClickListener) {
        this.visitableClickListener = visitableClickListener;
    }

    static abstract class AbstractViewHolder<T extends Visitable> extends RecyclerView.ViewHolder {

        AbstractViewHolder(View view) {
            super(view);
        }

        public abstract void bind(T element);

    }
}
