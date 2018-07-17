package example.powercode.us.redditclonesample.ui.activities.base.binding;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * @author meugen
 */
public class BaseBinding {

    private WeakReference<View> rootViewRef;
    private SparseArrayCompat<WeakReference<View>> childrenViewRefs;

    public void attachView(final View view) {
        rootViewRef = new WeakReference<>(view);
        childrenViewRefs = new SparseArrayCompat<>();
    }

    public void detachView() {
        rootViewRef = null;
        childrenViewRefs.clear();
        childrenViewRefs = null;
    }

    @Nullable
    private <V extends View> V getNullable(final int id) {
        if (childrenViewRefs == null || rootViewRef == null) {
            return null;
        }
        final WeakReference<View> childViewRef = childrenViewRefs.get(id);
        View childView = childViewRef == null ? null : childViewRef.get();
        if (childView == null) {
            final View rootView = rootViewRef.get();
            if (rootView != null) {
                childView = rootView.findViewById(id);
            }
            if (childView != null) {
                childrenViewRefs.put(id, new WeakReference<>(childView));
            }
        }
        return (V) childView;
    }

    @NonNull
    public <V extends View> V get(final int id) {
        final V view = getNullable(id);
        if (view == null) {
            throw new IllegalArgumentException("View with id " + id + " is not found");
        }
        return view;
    }

    public boolean has(final int id) {
        final View view = getNullable(id);
        return view != null;
    }
}
