package example.powercode.us.redditclonesample.main.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncListDiffer;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.base.ui.CanBind;
import example.powercode.us.redditclonesample.base.ui.DataBindingViewHolder;
import example.powercode.us.redditclonesample.common.Algorithms;
import example.powercode.us.redditclonesample.common.functional.Predicate;
import example.powercode.us.redditclonesample.databinding.ItemTopicBinding;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.entity.VoteType;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.ItemViewHolder> {
    @NonNull
    private final LayoutInflater inflater;

    private final AsyncListDiffer<TopicEntity> asyncDiffer;

    @Nullable
    private InteractionListener listener;

    public interface InteractionListener {
        void onVoteClick(@NonNull View v, int adapterPos, @NonNull VoteType vt);
    }

    @Inject
    TopicsAdapter(@NonNull LayoutInflater inflater, @Nullable InteractionListener listener) {
        this.inflater = inflater;
        this.listener = listener;

        this.asyncDiffer = new AsyncListDiffer<>(this, DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType) {
        return new ItemViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_topic, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private List<TopicEntity> getItems() {
        return asyncDiffer.getCurrentList();
    }

    @Override
    public int getItemCount() {
        return getItems().size();
    }

    public TopicEntity getItem(int position) {
        return getItems().get(position);
    }

    public void submitItems(@Nullable List<TopicEntity> items) {
        asyncDiffer.submitList(items);
    }

    public int findItemPosition(@NonNull Predicate<? super TopicEntity> condition) {
        return Algorithms.findIndex(getItems(), condition);
    }

    @NonNull
    private static final DiffUtil.ItemCallback<TopicEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<TopicEntity>() {
        @Override
        public boolean areItemsTheSame(TopicEntity oldItem, TopicEntity newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(TopicEntity oldItem, TopicEntity newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };

    static class ItemViewHolder extends DataBindingViewHolder<ItemTopicBinding> implements CanBind<TopicEntity> {
        @Nullable
        private InteractionListener listener;

        ItemViewHolder(@NonNull ItemTopicBinding binding, @Nullable InteractionListener l) {
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
