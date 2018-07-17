package example.powercode.us.redditclonesample.ui.activities.main.binding;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.app.di.qualifiers.ActivityContext;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.ui.activities.base.binding.BaseBinding;
import example.powercode.us.redditclonesample.ui.activities.main.TopicListFragment;
import example.powercode.us.redditclonesample.ui.activities.main.TopicsAdapter;
import example.powercode.us.redditclonesample.ui.activities.main.TopicsTouchHelper;
import example.powercode.us.redditclonesample.ui.utils.AbstractOnClickListener;

/**
 * @author meugen
 */
public class TopicListBindingImpl extends BaseBinding
        implements TopicListBinding {

    @Inject @ActivityContext Context context;
    @Inject TopicListFragment.OnInteractionListener listener;
    @Inject TopicsAdapter adapter;

    @Inject
    TopicListBindingImpl() {}

    @Override
    public void setupTopics(final OnDeleteTopicListener listener) {
        final RecyclerView rvTopics = get(R.id.rv_topics);
        rvTopics.setItemAnimator(new DefaultItemAnimator());
        rvTopics.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.VERTICAL));

        rvTopics.setAdapter(adapter);
        final ItemTouchHelper.Callback callback = new TopicsTouchHelper(0,
                ItemTouchHelper.LEFT, new InteractionListenerImpl(listener, adapter));
        new ItemTouchHelper(callback).attachToRecyclerView(rvTopics);
        setupTopicsCount(0);
    }

    @Override
    public void setupListeners() {
        final View view = get(R.id.fab_topic_create);
        view.setOnClickListener(new OnClickListenerImpl(listener));
    }

    @Override
    public void showItems(final List<TopicEntity> items) {
        adapter.submitItems(items);
        setupTopicsCount(items == null ? 0 : items.size());
    }

    private void setupTopicsCount(final int count) {
        final TextView view = get(R.id.topics_count_label);
        view.setText(String.valueOf(count));
        view.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void updateItemWithId(final long id) {
        int updatedItemPosition = adapter.findItemPosition(topicEntity -> id == topicEntity.id);
        if (updatedItemPosition != RecyclerView.NO_POSITION) {
            adapter.notifyItemChanged(updatedItemPosition);
        }
    }

    private static class OnClickListenerImpl extends AbstractOnClickListener {

        private final WeakReference<TopicListFragment.OnInteractionListener> listenerRef;

        OnClickListenerImpl(final TopicListFragment.OnInteractionListener listener) {
            this.listenerRef = new WeakReference<>(listener);
        }

        @Override
        public void _onClick(final View v) {
            final TopicListFragment.OnInteractionListener listener = listenerRef.get();
            if (listener == null) {
                return;
            }
            final int id = v.getId();
            if (id == R.id.fab_topic_create) {
                listener.onCreateNewTopic();
            }
        }
    }

    private static class InteractionListenerImpl implements TopicsTouchHelper.InteractionListener<RecyclerView, TopicsAdapter.ItemViewHolder> {

        private final WeakReference<OnDeleteTopicListener> listenerRef;
        private final WeakReference<TopicsAdapter> adapterRef;

        InteractionListenerImpl(
                final OnDeleteTopicListener listener,
                final TopicsAdapter adapter) {
            this.listenerRef = new WeakReference<>(listener);
            this.adapterRef = new WeakReference<>(adapter);
        }

        @Override
        public void onSwiped(TopicsAdapter.ItemViewHolder viewHolder, int direction) {
            final TopicsAdapter adapter = adapterRef.get();
            final OnDeleteTopicListener listener = listenerRef.get();
            if (adapter == null || listener == null) {
                return;
            }
            TopicEntity topicToDelete = adapter.getItem(viewHolder.getAdapterPosition());
            listener.onDeleteTopic(topicToDelete);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, TopicsAdapter.ItemViewHolder viewHolder, TopicsAdapter.ItemViewHolder target) {
            return false;
        }
    }
}
