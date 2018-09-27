package example.powercode.us.redditclonesample.main.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.app.di.qualifiers.ActivityContext;
import example.powercode.us.redditclonesample.base.error.ErrorDataTyped;
import example.powercode.us.redditclonesample.base.ui.common.DefaultTagGenerator;
import example.powercode.us.redditclonesample.base.ui.common.HasFragmentTag;
import example.powercode.us.redditclonesample.base.ui.fragments.BaseViewModelFragment;
import example.powercode.us.redditclonesample.base.vm.ViewModelAttachHelper;
import example.powercode.us.redditclonesample.common.ParamPredicate;
import example.powercode.us.redditclonesample.common.functional.Predicate;
import example.powercode.us.redditclonesample.databinding.FragmentTopicCreateBinding;
import example.powercode.us.redditclonesample.main.vm.TopicCreateViewModel;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.error.ErrorsTopics;
import example.powercode.us.redditclonesample.model.rules.BRulesTopics;
import example.powercode.us.redditclonesample.utils.KeyboardUtils;
import example.powercode.us.redditclonesample.utils.UserInputUtils;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

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

    @Inject
    @ActivityContext
    Context activityContext;

    private FragmentTopicCreateBinding binding;

    private CompositeDisposable uiInputDisposable;

    public TopicCreateFragment() {
        // Required empty public constructor
    }

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

        uiInputDisposable = new CompositeDisposable();
        assignListeners(uiInputDisposable);
    }

    private void assignListeners(@NonNull CompositeDisposable uiInputDisposable) {
        uiInputDisposable.add(
                RxView
                        .clicks(binding.rootTopicCreate.topicNewAbort)
                        .takeUntil(RxView.detaches(binding.rootTopicCreate.topicNewAbort))
                        .subscribe(o -> doHandleCancel())
        );

        uiInputDisposable.add(
                RxView
                        .clicks(binding.rootTopicCreate.topicNewCreate)
                        .takeUntil(RxView.detaches(binding.rootTopicCreate.topicNewCreate))
                        .flatMapMaybe(o ->
                                validateTopicCreateParams(BRulesTopics::isTitleValid,
                                        new RatingPredicate(getResources().getInteger(R.integer.topic_rating_abs_limit))))
                        .subscribe(paramsHolder -> {
                            KeyboardUtils.hideKeyboard(activityContext, TopicCreateFragment.this);

                            viewModel.getCreateTopicLiveData().observe(this, createTopicObserver);
                            viewModel.newTopic(paramsHolder.topicTitle, paramsHolder.topicRating);
                        })
        );

        uiInputDisposable.add(
                RxView.clicks(binding.toolbarContent.actionBack)
                .takeUntil(RxView.detaches(binding.toolbarContent.actionBack))
                .subscribe(o -> doHandleCancel())
        );
    }

    private void doHandleCancel() {
        Objects.requireNonNull(activityContext);

        KeyboardUtils.hideKeyboard(activityContext, this);
        listener.onTopicCreateCancelled();
    }

    private Maybe<ParamsHolder> validateTopicCreateParams(@NonNull Predicate<String> titleValidator,
                                                          @NonNull ParamPredicate<Integer> ratingValidator) {
        return formatInputParams(binding.rootTopicCreate.inputTitle,
                                            binding.rootTopicCreate.inputInitialRating)
        .filter(paramsHolder -> validateTopicTitle(paramsHolder.topicTitle, titleValidator, binding.rootTopicCreate.inputTitle)
                && validateTopicRating(paramsHolder.topicRating, ratingValidator, binding.rootTopicCreate.inputInitialRating));
    }

    @NonNull
    private static Single<ParamsHolder> formatInputParams(@NonNull EditText inputTitle, @NonNull EditText inputInitialRating) {
        return Single.fromCallable(() -> {
            final String titleCleaned = UserInputUtils.removeDuplicateSpaces(inputTitle.getText().toString().trim());
            int initialRating = Integer.MIN_VALUE;
            try {
                initialRating = Integer.parseInt(inputInitialRating.getText().toString());
            }
            catch (NumberFormatException ignored) {
            }

            return new ParamsHolder(titleCleaned, initialRating);
        });
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
    private final Observer<Resource<Long, ErrorDataTyped<ErrorsTopics>>> createTopicObserver = new Observer<Resource<Long, ErrorDataTyped<ErrorsTopics>>>() {
        @Override
        public void onChanged(@Nullable Resource<Long, ErrorDataTyped<ErrorsTopics>> createdTopicIdResource) {
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
    public void onDestroyView() {
        super.onDestroyView();
        uiInputDisposable.dispose();
    }

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
}
