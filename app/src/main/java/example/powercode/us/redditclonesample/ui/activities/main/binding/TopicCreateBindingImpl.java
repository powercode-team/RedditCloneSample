package example.powercode.us.redditclonesample.ui.activities.main.binding;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.app.di.qualifiers.ActivityContext;
import example.powercode.us.redditclonesample.common.ParamPredicate;
import example.powercode.us.redditclonesample.common.functional.Predicate;
import example.powercode.us.redditclonesample.ui.activities.base.binding.BaseBinding;
import example.powercode.us.redditclonesample.ui.utils.AbstractOnClickListener;
import example.powercode.us.redditclonesample.ui.utils.UserInputUtils;

/**
 * @author meugen
 */
public class TopicCreateBindingImpl extends BaseBinding implements TopicCreateBinding {

    @Inject @ActivityContext Context context;

    @Inject
    TopicCreateBindingImpl() {}

    @Override
    public void setupListeners(final CreateTopicListeners listeners) {
        final OnClickListenerImpl impl = new OnClickListenerImpl(listeners);
        get(R.id.topic_new_abort).setOnClickListener(impl);
        get(R.id.topic_new_create).setOnClickListener(impl);
        get(R.id.action_back).setOnClickListener(impl);
    }

    @Override
    public ParamsHolder validateTopicCreateParams(
            @NonNull final Predicate<String> titleValidator,
            @NonNull final ParamPredicate<Integer> ratingValidator) {
        final EditText inputTitle = get(R.id.input_title);
        final EditText inputInitialRating = get(R.id.input_initial_rating);

        final ParamsHolder paramsHolder = formatInputParams(inputTitle, inputInitialRating);
        if (validateTopicTitle(paramsHolder.topicTitle, titleValidator, inputTitle)
                && validateTopicRating(paramsHolder.topicRating, ratingValidator, inputInitialRating)) {
            return paramsHolder;
        }
        return null;
    }

    @NonNull
    private ParamsHolder formatInputParams(@NonNull EditText inputTitle, @NonNull EditText inputInitialRating) {
        final String titleCleaned = UserInputUtils.removeDuplicateSpaces(inputTitle.getText().toString().trim());
        int initialRating = Integer.MIN_VALUE;
        try {
            initialRating = Integer.parseInt(inputInitialRating.getText().toString());
        } catch (NumberFormatException ignored) {
        }

        return new ParamsHolder(titleCleaned, initialRating);
    }

    private boolean validateTopicTitle(
            final String topicTitle,
            @NonNull final Predicate<String> titleValidator,
            @NonNull final EditText uiControl) {
        if (titleValidator.test(topicTitle)) {
            return true;
        }
        uiControl.setError(context.getText(R.string.error_topic_title_invalid));
        return false;
    }

    private boolean validateTopicRating(
            final int topicRating,
            @NonNull final ParamPredicate<Integer> ratingValidator,
            @NonNull final EditText uiControl) {
        if (ratingValidator.test(topicRating)) {
            return true;
        }
        uiControl.setError(context.getString(
                R.string.error_topic_rating_is_out_bound,
                ratingValidator.param, ratingValidator.param));
        return false;
    }

    private static class OnClickListenerImpl extends AbstractOnClickListener {

        private final WeakReference<CreateTopicListeners> ref;

        OnClickListenerImpl(final CreateTopicListeners listener) {
            this.ref = new WeakReference<>(listener);
        }

        @Override
        protected void _onClick(final View view) {
            final CreateTopicListeners listeners = ref.get();
            if (listeners == null) {
                return;
            }
            final int viewId = view.getId();
            if (viewId == R.id.topic_new_abort) {
                listeners.onTopicNewAbort();
            } else if (viewId == R.id.topic_new_create) {
                listeners.onTopicNewCreate();
            } else if (viewId == R.id.action_back) {
                listeners.onActionBack();
            }
        }
    }
}
