package example.powercode.us.redditclonesample.base.ui;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
public abstract class DataBindingViewHolder<DataBinding extends ViewDataBinding> extends RecyclerView.ViewHolder {
    @NonNull
    protected DataBinding bindComponent;

    public DataBindingViewHolder(@NonNull DataBinding b) {
        super(b.getRoot());

        bindComponent = b;
    }
}
