package example.powercode.us.redditclonesample.ui.activities.main;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.util.Objects;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.common.ParamPredicate;
import example.powercode.us.redditclonesample.common.functional.Predicate;
import example.powercode.us.redditclonesample.databinding.FragmentTopicCreateBinding;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.error.ErrorsTopics;
import example.powercode.us.redditclonesample.model.rules.BRulesTopics;
import example.powercode.us.redditclonesample.ui.activities.base.common.DefaultTagGenerator;
import example.powercode.us.redditclonesample.ui.activities.base.common.HasFragmentTag;
import example.powercode.us.redditclonesample.ui.activities.base.error.ErrorDataTyped;
import example.powercode.us.redditclonesample.ui.activities.base.fragments.BaseViewModelFragment;
import example.powercode.us.redditclonesample.ui.activities.base.vm.ViewModelAttachHelper;
import example.powercode.us.redditclonesample.ui.activities.main.vm.TopicCreateViewModel;
import example.powercode.us.redditclonesample.ui.utils.AbstractOnClickListener;
import example.powercode.us.redditclonesample.ui.utils.UserInputUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicCreateFragment extends BaseViewModelFragment<TopicCreateViewModel> implements HasFragmentTag {
    public static final String FRAGMENT_TAG = DefaultTagGenerator.generate(TopicCreateFragment.class);

    @NonNull
    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Inject
    OnInteractionListener listener;

    private FragmentTopicCreateBinding binding;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TopicListFragment.
     */
    public static TopicCreateFragment newInstance() {
        TopicCreateFragment fragment = new TopicCreateFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_topic_create, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assignListeners();
    }

    private void assignListeners() {
        final OnClickListenerImpl impl = new OnClickListenerImpl(this);
        binding.rootTopicCreate.topicNewAbort.setOnClickListener(impl);
        binding.rootTopicCreate.topicNewCreate.setOnClickListener(impl);
        binding.toolbarContent.actionBack.setOnClickListener(impl);
    }

    private ParamsHolder validateTopicCreateParams(@NonNull Predicate<String> titleValidator,
                                                          @NonNull ParamPredicate<Integer> ratingValidator) {
        final ParamsHolder paramsHolder = formatInputParams(binding.rootTopicCreate.inputTitle,
                                            binding.rootTopicCreate.inputInitialRating);
        if (validateTopicTitle(paramsHolder.topicTitle, titleValidator, binding.rootTopicCreate.inputTitle)
                && validateTopicRating(paramsHolder.topicRating, ratingValidator, binding.rootTopicCreate.inputInitialRating)) {
            return paramsHolder;
        }
        return null;
    }

    @NonNull
    private static ParamsHolder formatInputParams(@NonNull EditText inputTitle, @NonNull EditText inputInitialRating) {
        final String titleCleaned = UserInputUtils.removeDuplicateSpaces(inputTitle.getText().toString().trim());
        int initialRating = Integer.MIN_VALUE;
        try {
            initialRating = Integer.parseInt(inputInitialRating.getText().toString());
        }
        catch (NumberFormatException ignored) {
        }

        return new ParamsHolder(titleCleaned, initialRating);
    }

    private boolean validateTopicTitle(String topicTitle, @NonNull Predicate<String> titleValidator, @NonNull EditText uiControl) {
        if (titleValidator.test(topicTitle)) {
            return true;
        }

        uiControl.setError(getText(R.string.error_topic_title_invalid));

        return false;
    }

    private boolean validateTopicRating(int topicRating, @NonNull ParamPredicate<Integer> ratingValidator, @NonNull EditText uiControl) {
        if (ratingValidator.test(topicRating)) {
            return true;
        }

        uiControl.setError(getString(R.string.error_topic_rating_is_out_bound, ratingValidator.param, ratingValidator.param));

        return false;
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

    @NonNull
    @Override
    protected Class<TopicCreateViewModel> getViewModelClass() {
        return TopicCreateViewModel.class;
    }

    @Override
    protected void onAttachViewModel() {
        ViewModelAttachHelper.attachObserverIfLoading(viewModel.getCreateTopicLiveData(), this, createTopicObserver);
    }

    @Override
    protected void onDetachViewModel() {
        viewModel.getCreateTopicLiveData().removeObservers(this);
    }

    private void onTopicNewAbort() {
        listener.onTopicCreateCancelled();
    }

    private void onActionBack() {
        listener.onTopicCreateCancelled();
    }

    private void onTopicNewCreate() {
        final ParamsHolder paramsHolder = validateTopicCreateParams(BRulesTopics::isTitleValid,
                new RatingPredicate(getResources().getInteger(R.integer.topic_rating_abs_limit)));
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

    private static final class ParamsHolder {
        final String topicTitle;
        final int topicRating;

        ParamsHolder(String topicTitle, int topicRating) {
            this.topicTitle = topicTitle;
            this.topicRating = topicRating;
        }
    }

    private static class RatingPredicate extends ParamPredicate<Integer> {
        RatingPredicate(@NonNull Integer limit) {
            super(limit);
        }

        @Override
        public boolean test(Integer rating) {
            return BRulesTopics.isRatingValid(rating, param);
        }
    }

    private static class OnClickListenerImpl extends AbstractOnClickListener {

        private final WeakReference<TopicCreateFragment> ref;

        public OnClickListenerImpl(final TopicCreateFragment fragment) {
            this.ref = new WeakReference<>(fragment);
        }

        @Override
        protected void _onClick(final View view) {
            final TopicCreateFragment fragment = ref.get();
            if (fragment == null) {
                return;
            }
            final int viewId = view.getId();
            if (viewId == R.id.topic_new_abort) {
                fragment.onTopicNewAbort();
            } else if (viewId == R.id.topic_new_create) {
                fragment.onTopicNewCreate();
            } else if (viewId == R.id.action_back) {
                fragment.onActionBack();
            }
        }
    }
}
