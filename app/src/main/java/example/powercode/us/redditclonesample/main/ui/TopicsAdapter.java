package example.powercode.us.redditclonesample.main.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.base.ui.CanBind;
import example.powercode.us.redditclonesample.base.ui.DataBindingViewHolder;
import example.powercode.us.redditclonesample.databinding.ItemTopicBinding;
import example.powercode.us.redditclonesample.model.TopicEntity;
import example.powercode.us.redditclonesample.model.VoteType;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.ItemViewHolder> {
    @NonNull
    private final LayoutInflater inflater;

    private final List<TopicEntity> topics;

    @Nullable
    private InteractionListener listener;

    public interface InteractionListener {
        void onVoteClick(@NonNull View v, int adapterPos, @NonNull VoteType vt);
    }

    @Inject
    TopicsAdapter(@NonNull LayoutInflater inflater, @Nullable InteractionListener listener) {
        this.inflater = inflater;
        this.topics = Collections.emptyList();
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType) {
//        DataBindingUtil.inflate(inflater, R.layout.item_topic, parent, false);
        return new ItemViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_topic, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public void onViewRecycled(ItemViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public TopicEntity getItem(int position) {
        return topics.get(position);
    }

    static class ItemViewHolder extends DataBindingViewHolder<ItemTopicBinding> implements CanBind<TopicEntity> {
        @Nullable
        private InteractionListener listener;

        public ItemViewHolder(@NonNull ItemTopicBinding binding, @Nullable InteractionListener l) {
            super(binding);

            assignListener(l);
        }

        private void assignListener(@Nullable InteractionListener l) {
            if (listener != l) {
                listener = l;

                bindComponent.topicRateUp.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onVoteClick(bindComponent.topicRateUp, getAdapterPosition(), VoteType.UP);
                    }
                });

                bindComponent.topicRateDown.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onVoteClick(bindComponent.topicRateDown, getAdapterPosition(), VoteType.DOWN);
                    }
                });
            }
        }

        @Override
        public void bind(@NonNull TopicEntity t) {
            bindComponent.setTopic(t);
        }
    }
}
