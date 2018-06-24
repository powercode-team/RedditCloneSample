package example.powercode.us.redditclonesample.main.ui;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import example.powercode.us.redditclonesample.common.rv.GenericItemTouchHelperSimpleCallback;

/**
 * Created by dev for RedditCloneSample on 22-Jun-18.
 */
public final class TopicsTouchHelper extends GenericItemTouchHelperSimpleCallback<RecyclerView, TopicsAdapter.ItemViewHolder> {

    public interface InteractionListener<RV extends RecyclerView, VH extends RecyclerView.ViewHolder> {
        void onSwiped(VH viewHolder, int direction);
        boolean onMove(RV recyclerView, VH viewHolder, VH target);
    }

    @NonNull
    private final InteractionListener<RecyclerView, TopicsAdapter.ItemViewHolder> listener;

    TopicsTouchHelper(int dragDirs, int swipeDirs, @NonNull InteractionListener<RecyclerView, TopicsAdapter.ItemViewHolder> listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMoveCallback(RecyclerView recyclerView, TopicsAdapter.ItemViewHolder viewHolder, TopicsAdapter.ItemViewHolder target) {
        return listener.onMove(recyclerView, viewHolder, target);
    }

    @Override
    public void onSwipedCallback(TopicsAdapter.ItemViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction);
    }

    @Override
    public void onSelectedChangedCallback(TopicsAdapter.ItemViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            getDefaultUIUtil().onSelected(viewHolder.getForeground().getRoot());
        }
    }

    @Override
    public void clearViewCallback(RecyclerView recyclerView, TopicsAdapter.ItemViewHolder viewHolder) {
        getDefaultUIUtil().clearView(viewHolder.getForeground().getRoot());
    }

    @Override
    public void onChildDrawCallback(Canvas c, RecyclerView recyclerView, TopicsAdapter.ItemViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        getDefaultUIUtil().onDraw(c, recyclerView, viewHolder.getForeground().getRoot(), dX, dY, actionState, isCurrentlyActive);
    }
}
