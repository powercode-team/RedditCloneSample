package example.powercode.us.redditclonesample.model.repository.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import example.powercode.us.redditclonesample.common.Algorithms;
import example.powercode.us.redditclonesample.common.functional.Function;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.app.di.scopes.PerApplication;
import example.powercode.us.redditclonesample.base.error.ErrorDataTyped;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.entity.EntityActionType;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.entity.VoteType;
import example.powercode.us.redditclonesample.model.error.ErrorsTopics;
import example.powercode.us.redditclonesample.model.repository.RepoTopics;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
@PerApplication
public class RepoTopicsImpl implements RepoTopics {
    private static final int TOPICS_COUNT = 300;
    // This is just a simulation of data source such as DB
    private List<TopicEntity> originalTopics;

    @NonNull
    private final PublishSubject<Pair<TopicEntity, EntityActionType>> topicChangeSubject = PublishSubject.create();

    private static List<TopicEntity> generateItems(int count, @NonNull Function<? super Integer, ? extends Integer> func) {
        if (count <= 0) {
            return Collections.emptyList();
        }

        List<TopicEntity> items = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            int rating = func.apply(i);
            items.add(new TopicEntity(1000 + i, "Very simple title " + i, rating));
        }

        return items;
    }

    private static boolean doVoteTopic(@NonNull TopicEntity topic, @NonNull VoteType vt) {
        switch (vt) {
            case UP: {
                if (topic.getRating() < Integer.MAX_VALUE) {
                    topic.setRating(topic.getRating() + 1);
                    return true;
                }

                break;
            }
            case DOWN: {
                if (topic.getRating() > Integer.MIN_VALUE) {
                    topic.setRating(topic.getRating() - 1);
                    return true;
                }
                break;
            }
            default: {
                throw new NoSuchElementException("Unknown VoteType " + vt);
            }
        }

        return false;
    }

    private Single<List<TopicEntity>> prepareOriginalTopics(int count) {
        return Single
                .fromCallable(() -> {
                    if (originalTopics == null) {
                        originalTopics = generateItems(count, index -> 2 * index ^ (index - 1));
                    }
                    return originalTopics;
                });
    }


    @Inject
    RepoTopicsImpl() {
    }

    @NonNull
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

    @NonNull
    @Override
    public Single<Pair<Long, Boolean>> applyVoteToTopic(final long id, @NonNull VoteType vt) {
        return Single
                .fromCallable(() -> {
                    TopicEntity targetTopic = Algorithms.findElement(originalTopics, topicEntity -> topicEntity.id == id);
                    if (targetTopic == null) {
                        return new Pair<>(id, false);
                    }

                    boolean isApplied = doVoteTopic(targetTopic, vt);
                    if (isApplied) {
                        topicChangeSubject.onNext(new Pair<>(new TopicEntity(targetTopic), EntityActionType.UPDATED));
                    }

                    return new Pair<>(targetTopic.getId(), isApplied);
                })
                .subscribeOn(Schedulers.computation());
    }

    @NonNull
    @Override
    public Single<Pair<Long, Boolean>> removeTopic(long id) {
        return Single
                .fromCallable(() -> {
                    TopicEntity targetTopic =
                            Algorithms.findElement(originalTopics, topicEntity -> topicEntity.id == id);

                    if (targetTopic == null) {
                        return new Pair<>(id, false);
                    }

                    boolean isRemoved = originalTopics.remove(targetTopic);
                    if (isRemoved) {
                        topicChangeSubject.onNext(new Pair<>(new TopicEntity(targetTopic), EntityActionType.DELETED));
                    }
                    return new Pair<>(id, isRemoved);
                })
                .subscribeOn(Schedulers.computation());
    }

    @NonNull
    @Override
    public Observable<Pair<TopicEntity, EntityActionType>> onTopicChangeObservable() {
        return topicChangeSubject.hide();
    }
}
