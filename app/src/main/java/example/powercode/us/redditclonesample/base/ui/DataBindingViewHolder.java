package example.powercode.us.redditclonesample.base.ui;


import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

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
