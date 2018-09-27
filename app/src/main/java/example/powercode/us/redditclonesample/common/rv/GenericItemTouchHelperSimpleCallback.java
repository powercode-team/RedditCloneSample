package example.powercode.us.redditclonesample.common.rv;

import android.graphics.Canvas;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by dev for RedditCloneSample on 22-Jun-18.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
public abstract class GenericItemTouchHelperSimpleCallback<RV extends RecyclerView, VH extends RecyclerView.ViewHolder> extends ItemTouchHelper.SimpleCallback {
    public GenericItemTouchHelperSimpleCallback(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    //**********************************************************************************************
    // ItemTouchHelper.SimpleCallback implementation
    //----------------------------------------------------------------------------------------------
    @Override
    public final int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return onSwipeDirsCallback((RV)recyclerView, (VH)viewHolder);
    }

    @Override
    public int getDragDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return getDragDirsCallback((RV)recyclerView, (VH)viewHolder);
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return getMovementFlagsCallback((RV)recyclerView, (VH)viewHolder);
    }

    public int onSwipeDirsCallback(RV recyclerView, VH viewHolder) {
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    public int getDragDirsCallback(RV recyclerView, VH viewHolder) {
        return super.getDragDirs(recyclerView, viewHolder);
    }

    public int getMovementFlagsCallback(RV recyclerView, VH viewHolder) {
        return super.getMovementFlags(recyclerView, viewHolder);
    }

    //**********************************************************************************************
    // ItemTouchHelper.Callback implementation
    //----------------------------------------------------------------------------------------------
    @Override
    public final boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return onMoveCallback((RV)recyclerView, (VH)viewHolder, (VH)target);
    }

    @Override
    public final void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        onSwipedCallback((VH)viewHolder, direction);
    }

    public abstract boolean onMoveCallback(RV recyclerView, VH viewHolder, VH target);

    public abstract void onSwipedCallback(VH viewHolder, int direction);

    @Override
    public final boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
        return canDropOverCallback((RV)recyclerView, (VH)current, (VH)target);
    }

    public boolean canDropOverCallback(RV recyclerView, VH current, VH target) {
        return super.canDropOver(recyclerView, current, target);
    }

    @Override
    public final float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return getSwipeThresholdCallback((VH)viewHolder);
    }

    public float getSwipeThresholdCallback(VH viewHolder) {
        return super.getSwipeThreshold(viewHolder);
    }

    @Override
    public final float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
        return getMoveThresholdCallback((VH)viewHolder);
    }

    public float getMoveThresholdCallback(VH viewHolder) {
        return super.getMoveThreshold(viewHolder);
    }

    @Override
    public final void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        onSelectedChangedCallback((VH)viewHolder, actionState);
    }

    public void onSelectedChangedCallback(VH viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public final void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        onMovedCallback((RV)recyclerView, (VH)viewHolder, fromPos, (VH)target, toPos, x, y);
    }

    public void onMovedCallback(RV recyclerView, VH viewHolder, int fromPos, VH target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
    }

    @Override
    public final void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        clearViewCallback((RV)recyclerView, (VH)viewHolder);
    }

    public void clearViewCallback(RV recyclerView, VH viewHolder) {
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public final void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        onChildDrawCallback(c, (RV)recyclerView, (VH)viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public void onChildDrawCallback(Canvas c, RV recyclerView, VH viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public final void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        onChildDrawOverCallback(c, (RV)recyclerView, (VH)viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public void onChildDrawOverCallback(Canvas c, RV recyclerView, VH viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public final long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        return getAnimationDurationCallback((RV)recyclerView, animationType, animateDx, animateDy);
    }

    public long getAnimationDurationCallback(RV recyclerView, int animationType, float animateDx, float animateDy) {
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }

    @Override
    public final int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int viewSize, int viewSizeOutOfBounds, int totalSize, long msSinceStartScroll) {
        return interpolateOutOfBoundsScrollCallback((RV)recyclerView, viewSize, viewSizeOutOfBounds, totalSize, msSinceStartScroll);
    }

    public int interpolateOutOfBoundsScrollCallback(RV recyclerView, int viewSize, int viewSizeOutOfBounds, int totalSize, long msSinceStartScroll) {
        return super.interpolateOutOfBoundsScroll(recyclerView, viewSize, viewSizeOutOfBounds, totalSize, msSinceStartScroll);
    }
}
