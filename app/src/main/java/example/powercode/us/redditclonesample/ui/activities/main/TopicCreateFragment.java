package example.powercode.us.redditclonesample.ui.activities.main;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.rules.BRulesTopics;
import example.powercode.us.redditclonesample.ui.activities.base.common.DefaultTagGenerator;
import example.powercode.us.redditclonesample.ui.activities.base.common.HasFragmentTag;
import example.powercode.us.redditclonesample.ui.activities.base.fragments.BaseViewModelFragment;
import example.powercode.us.redditclonesample.ui.activities.base.vm.ViewModelAttachHelper;
import example.powercode.us.redditclonesample.ui.activities.main.binding.TopicCreateBinding;
import example.powercode.us.redditclonesample.ui.activities.main.vm.TopicCreateViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicCreateFragment extends BaseViewModelFragment<TopicCreateViewModel, TopicCreateBinding>
        implements HasFragmentTag, TopicCreateBinding.CreateTopicListeners {
    public static final String FRAGMENT_TAG = DefaultTagGenerator.generate(TopicCreateFragment.class);

    @NonNull
    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Inject
    OnInteractionListener listener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TopicListFragment.
     */
    public static TopicCreateFragment newInstance() {
        return new TopicCreateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setupListeners(this);
        binding.setupWatchers();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewModelAttachHelper.attachObserverIfLoading(viewModel.getCreateTopicLiveData(),
                this, createTopicObserver);
    }

    @NonNull
    private final Observer<Resource<Long>> createTopicObserver = new Observer<Resource<Long>>() {
        @Override
        public void onChanged(@Nullable Resource<Long> createdTopicIdResource) {
            Objects.requireNonNull(createdTopicIdResource);
            switch (createdTopicIdResource.status) {
                case SUCCESS: {
                    viewModel.getCreateTopicLiveData().removeObserver(this);

                    Objects.requireNonNull(createdTopicIdResource.data, "Status.SUCCESS implies data to be set");

                    listener.onTopicCreated(createdTopicIdResource.data);

                    break;
                }
                case ERROR: {
                    viewModel.getCreateTopicLiveData().removeObserver(this);
                    break;
                }
                case LOADING:
                    break;
            }
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

//    @Override
//    protected void onDetachViewModel() {
//        viewModel.getCreateTopicLiveData().removeObservers(this);
//    }

    @Override
    public void onTopicNewAbort() {
        listener.onTopicCreateCancelled();
    }

    @Override
    public void onActionBack() {
        listener.onTopicCreateCancelled();
    }

    @Override
    public void onTopicNewCreate() {
        final TopicCreateBinding.ParamsHolder paramsHolder = binding.validateTopicCreateParams(
                BRulesTopics::isTitleValid,
                new TopicCreateBinding.RatingPredicate(getResources().getInteger(R.integer.topic_rating_abs_limit)));
        if (paramsHolder != null) {
            viewModel.getCreateTopicLiveData().observe(this, createTopicObserver);
            viewModel.newTopic(paramsHolder.topicTitle, paramsHolder.topicRating);
        }
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
        void onTopicCreated(long newTopicId);

        void onTopicCreateCancelled();
    }
}
