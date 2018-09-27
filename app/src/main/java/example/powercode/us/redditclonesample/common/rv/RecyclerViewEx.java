package example.powercode.us.redditclonesample.common.rv;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.utils.ViewUtils;
import timber.log.Timber;


/**
 * Created by jt on 21.01.2018.
 * Purpose: RecyclerView which supports empty view
 */
public class RecyclerViewEx extends RecyclerView {
    private @Nullable
    View emptyView_;
    private @IdRes
    int emptyViewId_;

    private final AdapterDataObserver itemsObserver_ = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            updateEmptyView(getItemsCountForEmptyView());
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            updateEmptyView(getItemsCountForEmptyView());
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            updateEmptyView(getItemsCountForEmptyView());
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            updateEmptyView(getItemsCountForEmptyView());
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            updateEmptyView(getItemsCountForEmptyView());
        }
    };

    public RecyclerViewEx(@NonNull Context context) {
        super(context);
        processAttributes(context, null);
    }

    public RecyclerViewEx(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        processAttributes(context, attrs);
    }

    public RecyclerViewEx(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        processAttributes(context, attrs);
    }

    public void setEmptyView(@Nullable View emptyView) {
        emptyView_ = emptyView;
        emptyViewId_ = (emptyView != null) ? emptyView.getId() : 0;

        updateEmptyView(getItemsCountForEmptyView());
    }

    public @Nullable View getEmptyView() {
        return emptyView_;
    }

    /**
     * Set a new adapter to provide child views on demand.
     * <p>
     * When adapter is changed, all existing views are recycled back to the pool. If the pool has
     * only one adapter, it will be cleared.
     *
     * @param adapter The new adapter to set, or null to set no adapter.
     * @see #swapAdapter(Adapter, boolean)
     */
    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        if (getAdapter() != null) {
            getAdapter().unregisterAdapterDataObserver(itemsObserver_);
        }

        if (adapter != null) {
            adapter.registerAdapterDataObserver(itemsObserver_);
        }

        super.setAdapter(adapter);

        updateEmptyView(getItemsCountForEmptyView());
    }

    private void updateEmptyView(int itemsCount) {
        Timber.d("::updateEmptyView( itemsCount: " + itemsCount + " )"
                + "\n\temptyView: " + emptyView_);
        if (emptyView_ != null) {
            final boolean emptyViewVisible = itemsCount == 0;
            emptyView_.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    private int getItemsCountForEmptyView() {
        return  (getAdapter() != null) ? getAdapter().getItemCount() : 0;
    }

    private void processAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final TypedArray attrsArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RecyclerViewEx, 0, 0);

        try {
            emptyViewId_ = attrsArray.getResourceId(R.styleable.RecyclerViewEx_emptyView, 0);
        }
        finally {
            attrsArray.recycle();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setEmptyView(ViewUtils.findViewById(this, emptyViewId_));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        setEmptyView(null);
    }
}
