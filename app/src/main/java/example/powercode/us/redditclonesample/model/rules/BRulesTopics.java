package example.powercode.us.redditclonesample.model.rules;

import java.util.Comparator;

import androidx.annotation.NonNull;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;

/**
 * Created by dev for RedditCloneSample on 21-Jun-18.
 * Business rules
 */
public class BRulesTopics {
    public static final int TOPICS_COUNT_WORKING_SET = 20;

    @NonNull
    public static final Comparator<? super TopicEntity> TOPICS_LIST_COMPARATOR = (topicEntity1, topicEntity2) ->
            (topicEntity1.getRating() == topicEntity2.getRating()) ? topicEntity1.title.compareTo(topicEntity2.title)
                    : Integer.compare(topicEntity2.getRating(), topicEntity1.getRating());

    public static boolean isTitleValid(@NonNull CharSequence title) {
        return title.length() > 0;
    }

    public static boolean isRatingValid(int rating, final int ratingLimitAbs) {
        return (-ratingLimitAbs <= rating) && (rating <= ratingLimitAbs);
    }
}
