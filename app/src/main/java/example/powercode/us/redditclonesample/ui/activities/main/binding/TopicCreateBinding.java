package example.powercode.us.redditclonesample.ui.activities.main.binding;

import android.support.annotation.NonNull;

import example.powercode.us.redditclonesample.common.ParamPredicate;
import example.powercode.us.redditclonesample.common.functional.Predicate;
import example.powercode.us.redditclonesample.model.rules.BRulesTopics;
import example.powercode.us.redditclonesample.ui.activities.base.binding.Binding;

/**
 * @author meugen
 */
public interface TopicCreateBinding extends Binding {

    void setupListeners(CreateTopicListeners listeners);

    interface CreateTopicListeners {

        void onTopicNewAbort();

        void onTopicNewCreate();

        void onActionBack();
    }

    ParamsHolder validateTopicCreateParams(
            Predicate<String> titleValidator,
            ParamPredicate<Integer> ratingValidator);

    class ParamsHolder {
        public final String topicTitle;
        public final int topicRating;

        ParamsHolder(String topicTitle, int topicRating) {
            this.topicTitle = topicTitle;
            this.topicRating = topicRating;
        }
    }

    class RatingPredicate extends ParamPredicate<Integer> {

        public RatingPredicate(@NonNull Integer limit) {
            super(limit);
        }

        @Override
        public boolean test(Integer rating) {
            return BRulesTopics.isRatingValid(rating, param);
        }
    }
}
