package example.powercode.us.redditclonesample.model.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import java.util.Comparator;
import java.util.List;

import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.entity.EntityActionType;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.entity.VoteType;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
public interface RepoTopics {
    @NonNull
    Resource<List<TopicEntity>> fetchTopics(@Nullable Comparator<? super TopicEntity> sortCmp, int count);
    @NonNull
    Pair<Long, Boolean> applyVoteToTopic(long id, @NonNull VoteType vt);
    @NonNull
    Pair<Long, Boolean> createTopic(@NonNull String title, int rating);
    @NonNull
    Pair<Long, Boolean> removeTopic(long id);

    void setOnTopicChangeListener(OnTopicChangeListener listener);

    interface OnTopicChangeListener {

        void onTopicChange(TopicEntity entity, EntityActionType type);
    }
}
