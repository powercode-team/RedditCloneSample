package example.powercode.us.redditclonesample.ui.activities.main;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.entity.VoteType;
import example.powercode.us.redditclonesample.ui.activities.base.common.DefaultTagGenerator;
import example.powercode.us.redditclonesample.ui.activities.base.common.HasFragmentTag;
import example.powercode.us.redditclonesample.ui.activities.base.fragments.BaseViewModelFragment;
import example.powercode.us.redditclonesample.ui.activities.base.vm.ViewModelAttachHelper;
import example.powercode.us.redditclonesample.ui.activities.main.binding.TopicListBinding;
import example.powercode.us.redditclonesample.ui.activities.main.vm.TopicsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicListFragment extends BaseViewModelFragment<TopicsViewModel, TopicListBinding>
        implements HasFragmentTag, TopicsAdapter.InteractionListener, TopicListBinding.OnDeleteTopicListener {

    public static final String FRAGMENT_TAG = DefaultTagGenerator.generate(TopicListFragment.class);

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TopicListFragment.
     */
    public static TopicListFragment newInstance() {
        return new TopicListFragment();
    }

    @NonNull
    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setupTopics(this);
        binding.setupListeners();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.getTopicsLiveData().observe(this, this::onTopicsFetchedObserver);
        ViewModelAttachHelper.attachObserverIfLoading(viewModel.getDeleteTopicLiveData(), this, deleteTopicObserver);
        ViewModelAttachHelper.attachObserverIfLoading(viewModel.getApplyVoteLiveData(), this, voteTopicObserver);
    }

    private void onTopicsFetchedObserver(@NonNull Resource<List<TopicEntity>> resTopics) {
        switch (resTopics.status) {
            case SUCCESS: {
                binding.showItems(resTopics.data);
                break;
            }

            case ERROR: {
                binding.showItems(null);
                break;
            }

            case LOADING:
                // TODO: display loading
                break;
        }
    }

//    @Override
//    protected void onDetachViewModel() {
//        // TODO We don't need to manually remove observers. When fragment (lifecycle owner) will be in DESTROYED state LiveData remove them automatically
//        // @see https://developer.android.com/reference/android/arch/lifecycle/LiveData.html#observe
//        viewModel.getTopicsLiveData().removeObservers(this);
//        viewModel.getApplyVoteLiveData().removeObservers(this);
//        viewModel.getDeleteTopicLiveData().removeObservers(this);
//    }

    @Override
    public void onVoteClick(@NonNull TopicEntity topic, @NonNull VoteType vt) {
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
                    binding.updateItemWithId(votedTopicIdResource.data);
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
                    binding.updateItemWithId(deleteTopicIdResource.data);
                    break;
                }
                case LOADING:
                    break;
            }
        }
    };

    @Override
    public void onDeleteTopic(final TopicEntity topic) {
        viewModel.getDeleteTopicLiveData()
                .observe(TopicListFragment.this, deleteTopicObserver);
        viewModel.deleteTopic(topic.id);
    }

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
