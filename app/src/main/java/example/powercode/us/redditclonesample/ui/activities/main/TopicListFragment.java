package example.powercode.us.redditclonesample.ui.activities.main;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.app.di.qualifiers.ActivityContext;
import example.powercode.us.redditclonesample.databinding.FragmentTopicListBinding;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.entity.VoteType;
import example.powercode.us.redditclonesample.ui.activities.base.common.DefaultTagGenerator;
import example.powercode.us.redditclonesample.ui.activities.base.common.HasFragmentTag;
import example.powercode.us.redditclonesample.ui.activities.base.fragments.BaseViewModelFragment;
import example.powercode.us.redditclonesample.ui.activities.base.vm.ViewModelAttachHelper;
import example.powercode.us.redditclonesample.ui.activities.main.vm.TopicsViewModel;
import example.powercode.us.redditclonesample.ui.utils.AbstractOnClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicListFragment extends BaseViewModelFragment<TopicsViewModel>
        implements HasFragmentTag, TopicsAdapter.InteractionListener {
    public static final String FRAGMENT_TAG = DefaultTagGenerator.generate(TopicListFragment.class);

    @NonNull
    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Inject
    @ActivityContext
    Context c;
    @Inject
    OnInteractionListener listener;

    @Inject
    TopicsAdapter adapter;

    private FragmentTopicListBinding binding;

    @NonNull
    private final ObservableInt topicsCount = new ObservableInt(0);

    public TopicListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TopicListFragment.
     */
    public static TopicListFragment newInstance() {
        TopicListFragment fragment = new TopicListFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_topic_list, container, false);
        binding.setItemsCount(topicsCount);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupTopicsRecyclerView(adapter,
                new DividerItemDecoration(c, DividerItemDecoration.VERTICAL),
                new DefaultItemAnimator(),
                new TopicsTouchHelper(0, ItemTouchHelper.LEFT, swipeInteractionListener)
        );

        binding.fabTopicCreate.setOnClickListener(new OnClickListenerImpl(listener));
    }

    private void setupTopicsRecyclerView(@Nullable RecyclerView.Adapter adapter,
                                         @NonNull DividerItemDecoration dividerDecoration,
                                         @NonNull RecyclerView.ItemAnimator itemAnimator,
                                         @NonNull ItemTouchHelper.Callback callback) {
        binding.rvTopics.setItemAnimator(itemAnimator);
        binding.rvTopics.addItemDecoration(dividerDecoration);

        binding.rvTopics.setAdapter(adapter);
        new ItemTouchHelper(callback).attachToRecyclerView(binding.rvTopics);
    }

    private void onTopicsFetchedObserver(@NonNull Resource<List<TopicEntity>> resTopics) {
        switch (resTopics.status) {
            case SUCCESS: {
                adapter.submitItems(resTopics.data);
                final int safeItemsCount = resTopics.data != null ? resTopics.data.size() : 0;
                topicsCount.set(safeItemsCount);

                break;
            }

            case ERROR: {
                adapter.submitItems(null);
                topicsCount.set(-1);

                break;
            }

            case LOADING:
                // TODO: display loading
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @NonNull
    @Override
    protected Class<TopicsViewModel> getViewModelClass() {
        return TopicsViewModel.class;
    }

    @Override
    protected void onAttachViewModel() {
        viewModel.getTopicsLiveData().observe(this, this::onTopicsFetchedObserver);
        ViewModelAttachHelper.attachObserverIfLoading(viewModel.getDeleteTopicLiveData(), this, deleteTopicObserver);
        ViewModelAttachHelper.attachObserverIfLoading(viewModel.getApplyVoteLiveData(), this, voteTopicObserver);
    }

    @Override
    protected void onDetachViewModel() {
        viewModel.getTopicsLiveData().removeObservers(this);
        viewModel.getApplyVoteLiveData().removeObservers(this);
        viewModel.getDeleteTopicLiveData().removeObservers(this);
    }

    @Override
    public void onVoteClick(@NonNull View v, int adapterPos, @NonNull VoteType vt) {
        final TopicEntity topic = adapter.getItem(adapterPos);
        viewModel.getApplyVoteLiveData().observe(this, voteTopicObserver);
        viewModel.voteTopic(topic.id, vt);
    }

    @NonNull
    private final Observer<Resource<Long>> voteTopicObserver = new Observer<Resource<Long>>() {
        @Override
        public void onChanged(@Nullable Resource<Long> votedTopicIdResource) {
            Objects.requireNonNull(votedTopicIdResource);
            switch (votedTopicIdResource.status) {
                case SUCCESS: {
                    viewModel.getApplyVoteLiveData().removeObserver(this);

                    Objects.requireNonNull(votedTopicIdResource.data, "Status.SUCCESS implies data to be set");

                    final long updatedItemId = votedTopicIdResource.data;
                    int updatedItemPosition = adapter.findItemPosition(topicEntity -> updatedItemId == topicEntity.id);
                    if (updatedItemPosition != RecyclerView.NO_POSITION) {
                        adapter.notifyItemChanged(updatedItemPosition);
                    }

                    break;
                }
                case ERROR: {
                    viewModel.getApplyVoteLiveData().removeObserver(this);
                    break;
                }
                case LOADING:
                    break;
            }
        }
    };

    @NonNull
    private final TopicsTouchHelper.InteractionListener<RecyclerView, TopicsAdapter.ItemViewHolder> swipeInteractionListener = new TopicsTouchHelper.InteractionListener<RecyclerView, TopicsAdapter.ItemViewHolder>() {
        @Override
        public void onSwiped(TopicsAdapter.ItemViewHolder viewHolder, int direction) {
            TopicEntity topicToDelete = adapter.getItem(viewHolder.getAdapterPosition());
            viewModel.getDeleteTopicLiveData().observe(TopicListFragment.this, deleteTopicObserver);
            viewModel.deleteTopic(topicToDelete.id);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, TopicsAdapter.ItemViewHolder viewHolder, TopicsAdapter.ItemViewHolder target) {
            return false;
        }
    };

    @NonNull
    private final Observer<Resource<Long>> deleteTopicObserver = new Observer<Resource<Long>>() {
        @Override
        public void onChanged(@Nullable Resource<Long> deleteTopicIdResource) {
            Objects.requireNonNull(deleteTopicIdResource);
            switch (deleteTopicIdResource.status) {
                case SUCCESS: {
                    viewModel.getDeleteTopicLiveData().removeObserver(this);
                    // Do nothing since notify changes will trigger update

                    break;
                }
                case ERROR: {
                    viewModel.getDeleteTopicLiveData().removeObserver(this);
                    Objects.requireNonNull(deleteTopicIdResource.data, "Status.SUCCESS implies data to be set");
                    final long failedDeleteItemId = deleteTopicIdResource.data;
                    int failedDeleteItemPosition = adapter.findItemPosition(topicEntity -> failedDeleteItemId == topicEntity.id);
                    adapter.notifyItemChanged(failedDeleteItemPosition);

                    break;
                }
                case LOADING:
                    break;
            }
        }
    };

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnInteractionListener {
        void onCreateNewTopic();
    }

    private static class OnClickListenerImpl extends AbstractOnClickListener {

        private final WeakReference<OnInteractionListener> listenerRef;

        OnClickListenerImpl(final OnInteractionListener listener) {
            this.listenerRef = new WeakReference<>(listener);
        }

        @Override
        public void _onClick(final View v) {
            final OnInteractionListener listener = listenerRef.get();
            if (listener == null) {
                return;
            }
            final int id = v.getId();
            if (id == R.id.fab_topic_create) {
                listener.onCreateNewTopic();
            }
        }
    }
}
