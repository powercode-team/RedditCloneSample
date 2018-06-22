package example.powercode.us.redditclonesample.main.ui;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.DataBindingUtil;
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

import junit.framework.Assert;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.app.di.qualifiers.ActivityContext;
import example.powercode.us.redditclonesample.base.error.ErrorDataTyped;
import example.powercode.us.redditclonesample.base.ui.common.DefaultTagGenerator;
import example.powercode.us.redditclonesample.base.ui.common.HasFragmentTag;
import example.powercode.us.redditclonesample.base.ui.fragments.BaseViewModelFragment;
import example.powercode.us.redditclonesample.databinding.FragmentTopicListBinding;
import example.powercode.us.redditclonesample.main.vm.TopicsViewModel;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.entity.VoteType;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.error.ErrorsTopics;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicListFragment extends BaseViewModelFragment<TopicsViewModel> implements HasFragmentTag,
        TopicsAdapter.InteractionListener {
    public static final String FRAGMENT_TAG = DefaultTagGenerator.generate(TopicListFragment.class);

    @NonNull
    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Inject @ActivityContext Context c;
    @Inject OnInteractionListener listener;

    @Inject TopicsAdapter adapter;

    private FragmentTopicListBinding binding;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.getTopicsLiveData().observe(this, this::onTopicsFetchedObserver);
    }

    private void onTopicsFetchedObserver(@NonNull Resource<List<TopicEntity>, ErrorDataTyped<ErrorsTopics>> resTopics) {
        switch (resTopics.status) {
            case SUCCESS: {
                adapter.submitItems(resTopics.data);
                break;
            }

            case ERROR: {
                adapter.submitItems(null);
                //TODO: display error

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
    protected void onDetachFromViewModel() {
        viewModel.getTopicsLiveData().removeObservers(this);
        viewModel.getApplyVoteLiveData().removeObservers(this);
    }

    @Override
    public void onVoteClick(@NonNull View v, int adapterPos, @NonNull VoteType vt) {
        final TopicEntity topic = adapter.getItem(adapterPos);
        viewModel.getApplyVoteLiveData().observe(this, voteTopicObserver);
        viewModel.voteTopic(topic.id, vt);
    }

    @NonNull
    private final Observer<Resource<Long, ErrorDataTyped<ErrorsTopics>>> voteTopicObserver = new Observer<Resource<Long, ErrorDataTyped<ErrorsTopics>>>() {
        @Override
        public void onChanged(@Nullable Resource<Long, ErrorDataTyped<ErrorsTopics>> votedTopicIdResource) {
            Objects.requireNonNull(votedTopicIdResource);
            switch (votedTopicIdResource.status) {
                case SUCCESS: {
                    viewModel.getApplyVoteLiveData().removeObserver(voteTopicObserver);

                    Objects.requireNonNull(votedTopicIdResource.data, "Status.SUCCESS implies data to be set");

                    final long updatedItemId = votedTopicIdResource.data;
                    int updatedItemPosition = adapter.findItemPosition(topicEntity -> updatedItemId == topicEntity.id);
                    if (updatedItemPosition != RecyclerView.NO_POSITION) {
                        adapter.notifyItemChanged(updatedItemPosition);
                    }

                    break;
                }
                case ERROR: {
                    viewModel.getApplyVoteLiveData().removeObserver(voteTopicObserver);
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
        public void onSwiped(TopicsAdapter.ItemViewHolder viewHolder, int direction, int position) {

        }

        @Override
        public boolean onMove(RecyclerView recyclerView, TopicsAdapter.ItemViewHolder viewHolder, TopicsAdapter.ItemViewHolder target) {
            return false;
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
}
