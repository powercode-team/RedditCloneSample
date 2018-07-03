package example.powercode.us.redditclonesample.model.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import java.util.Comparator;
import java.util.List;

import example.powercode.us.redditclonesample.base.error.ErrorDataTyped;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.entity.EntityActionType;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.entity.VoteType;
import example.powercode.us.redditclonesample.model.error.ErrorsTopics;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
public interface RepoTopics {
    @NonNull
    Single<Resource<List<TopicEntity>, ErrorDataTyped<ErrorsTopics>>> fetchTopics(@Nullable Comparator<? super TopicEntity> sortCmp, int count);
    @NonNull
    Single<Pair<Long, Boolean>> applyVoteToTopic(long id, @NonNull VoteType vt);
    @NonNull
    Single<Pair<Long, Boolean>> createTopic(@NonNull String title, int rating);
    @NonNull
    Single<Pair<Long, Boolean>> removeTopic(long id);

    @NonNull
    Single<Resource<TopicEntity, ErrorDataTyped<ErrorsTopics>>> getById(long topicId);

    @NonNull
    Observable<Pair<TopicEntity, EntityActionType>> onTopicChangeObservable();
}
