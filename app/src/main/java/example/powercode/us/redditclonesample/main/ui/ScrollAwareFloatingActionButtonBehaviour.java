package example.powercode.us.redditclonesample.main.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import timber.log.Timber;

/**
 * Created by dev for RedditCloneSample on 27-Jun-18.
 */
public class ScrollAwareFloatingActionButtonBehaviour extends FloatingActionButton.Behavior {
    private static final int SCROLL_CONSUMED_Y_THRESHOLD = 0;

    @NonNull
    private final FloatingActionButton.OnVisibilityChangedListener hideChangedListener
            = new FloatingActionButton.OnVisibilityChangedListener() {
        @Override
        public void onHidden(FloatingActionButton fab) {
            super.onHidden(fab);
            fab.setVisibility(View.INVISIBLE);
        }
    };

    public ScrollAwareFloatingActionButtonBehaviour() {
        super();
    }

    public ScrollAwareFloatingActionButtonBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull FloatingActionButton child,
                                       @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        // We want to be aware of the vertical scroll
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);

        if (dyConsumed > SCROLL_CONSUMED_Y_THRESHOLD && child.getVisibility() == View.VISIBLE) {
            child.hide(hideChangedListener);
        } else if (dyConsumed <= -SCROLL_CONSUMED_Y_THRESHOLD && child.getVisibility() != View.VISIBLE) {
            child.show();
        }
    }

    private static boolean isRecyclerView(@NonNull View view) {
        return view instanceof RecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        boolean behaviourChanged = super.onDependentViewChanged(parent, child, dependency);
        if (isRecyclerView(dependency)) {
            Timber.d("onDependantViewChanged %s", dependency);
        }

        return behaviourChanged;
    }

    private boolean shouldUpdateVisibility(View dependency, FloatingActionButton child) {
        final CoordinatorLayout.LayoutParams lp =
                (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (!isAutoHideEnabled()) {
            return false;
        }

        //noinspection RedundantIfStatement
        if (lp.getAnchorId() != dependency.getId()) {
            // The anchor ID doesn't match the dependency, so we won't automatically
            // show/hide the FAB
            return false;
        }

        //noinspection RedundantIfStatement
//        if (child.getVisibility() != View.VISIBLE) {
//            // The view isn't set to be visible so skip changing its visibility
//            return false;
//        }

        return true;
    }
}
