package example.powercode.us.redditclonesample.model.repository.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import example.powercode.us.redditclonesample.common.java.Function;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.app.di.scopes.PerApplication;
import example.powercode.us.redditclonesample.base.error.ErrorDataTyped;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.entity.VoteType;
import example.powercode.us.redditclonesample.model.error.ErrorsTopics;
import example.powercode.us.redditclonesample.model.repository.RepoTopics;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
@PerApplication
public class RepoTopicsImpl implements RepoTopics {
    private static final int TOPICS_COUNT = 300;
    // This is just a simulation of data source such as DB
    private List<TopicEntity> originalTopics;

    private static List<TopicEntity> generateItems(int count, @NonNull Function<? super Integer, ? extends Integer> func) {
        if (count <= 0) {
            return Collections.emptyList();
        }

        List<TopicEntity> items = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            int rating = func.apply(i);
            items.add(new TopicEntity(i, "Very simple title " + i, rating));
        }

        return items;
    }

    private Single<List<TopicEntity>> prepareOriginalTopics(int count) {
        return Single
                .fromCallable(() -> {
                    if (originalTopics == null) {
                        originalTopics = generateItems(count, index -> 2*index ^ (index-1));
                    }
                    return originalTopics;
                });
    }


    @Inject
    RepoTopicsImpl() {
    }

    @Override
    public Single<Resource<List<TopicEntity>, ErrorDataTyped<ErrorsTopics>>> fetchTopics(@Nullable Comparator<? super TopicEntity> sortCmp, int count) {
        return prepareOriginalTopics(TOPICS_COUNT)
                .subscribeOn(Schedulers.computation())
                .map(topicEntities -> {
                    List<TopicEntity> sortedItems = new ArrayList<>(topicEntities);
                    if (sortCmp != null) {
                        Collections.sort(sortedItems, sortCmp);
                    }

                    return sortedItems;
                })
                .map(sortedTopicEntities -> {
                    if (count < sortedTopicEntities.size()) {
                        sortedTopicEntities.subList(count, sortedTopicEntities.size()).clear();
                    }

                    return Resource.success(sortedTopicEntities, (ErrorDataTyped<ErrorsTopics>) null);
                });
    }

    @Override
    public Single<Resource<TopicEntity, ErrorDataTyped<ErrorsTopics>>> applyVoteToTopic(long id, @NonNull VoteType vt) {
        return null;
    }
}
