package example.powercode.us.redditclonesample.model.repository.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.app.di.scopes.PerApplication;
import example.powercode.us.redditclonesample.common.Algorithms;
import example.powercode.us.redditclonesample.common.functional.Function;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.entity.EntityActionType;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.entity.VoteType;
import example.powercode.us.redditclonesample.model.repository.RepoTopics;
import example.powercode.us.redditclonesample.model.rules.BRulesTopics;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
@PerApplication
public class RepoTopicsImpl implements RepoTopics {

    private static final int TOPICS_COUNT = (int)(BRulesTopics.TOPICS_COUNT_WORKING_SET * 1.2f);
    // This is just a simulation of data source such as DB
    private volatile List<TopicEntity> originalTopics;

    // Is used to generate ids. In real app back-end sets id to entity
    @NonNull
    private static final AtomicLong idCounter = new AtomicLong(1000L);

    private static void generateItems(@NonNull Collection<TopicEntity> c, int count, @NonNull Function<? super Integer, ? extends Integer> func) {
        if (count <= 0) {
            return;
        }

        List<TopicEntity> items = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            int rating = func.apply(i);
            items.add(new TopicEntity(idCounter.getAndIncrement(), "Very simple title " + i, rating));
        }


        c.clear();
        c.addAll(items);
    }

    private OnTopicChangeListener listener;

    private List<TopicEntity> getOriginalTopics() {
        if (originalTopics == null) {
            synchronized (this) {
                if (originalTopics == null) {
                    // TODO Why originalTopics have to be synchronized list?
                    originalTopics = Collections.synchronizedList(new ArrayList<>(TOPICS_COUNT));
                    generateItems(originalTopics, TOPICS_COUNT, index -> 2 * index ^ (index - 1));
                }
            }
        }

        return originalTopics;
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


    @Inject
    RepoTopicsImpl() {}

    @Override
    public void setOnTopicChangeListener(final OnTopicChangeListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Resource<List<TopicEntity>> fetchTopics(@Nullable Comparator<? super TopicEntity> sortCmp, int count) {
        List<TopicEntity> topics = new ArrayList<>(getOriginalTopics());
        if (sortCmp != null) {
            Collections.sort(topics, sortCmp);
        }
        if (count < topics.size()) {
            topics.subList(count, topics.size()).clear();
        }
        return Resource.success(topics);
    }

    @NonNull
    @Override
    public Pair<Long, Boolean> applyVoteToTopic(final long id, @NonNull VoteType vt) {
        TopicEntity targetTopic = Algorithms.findElement(originalTopics, topicEntity -> topicEntity.id == id);
        if (targetTopic == null) {
            return new Pair<>(id, false);
        }

        boolean isApplied = doVoteTopic(targetTopic, vt);
        if (isApplied && listener != null) {
            listener.onTopicChange(new TopicEntity(targetTopic), EntityActionType.UPDATED);
        }
        return new Pair<>(targetTopic.getId(), isApplied);
    }

    @NonNull
    @Override
    public Pair<Long, Boolean> createTopic(@NonNull String title, int rating) {
        TopicEntity targetTopic = new TopicEntity(idCounter.incrementAndGet(), title, rating);
        boolean isAdded = getOriginalTopics().add(targetTopic);
        if (isAdded && listener != null) {
            listener.onTopicChange(new TopicEntity(targetTopic), EntityActionType.INSERTED);
        }
        return new Pair<>(Long.MIN_VALUE, isAdded);
    }

    @NonNull
    @Override
    public Pair<Long, Boolean> removeTopic(long id) {
        TopicEntity targetTopic =
                Algorithms.findElement(originalTopics, topicEntity -> topicEntity.id == id);

        if (targetTopic == null) {
            return new Pair<>(id, false);
        }

        boolean isRemoved = originalTopics.remove(targetTopic);
        if (isRemoved && listener != null) {
            listener.onTopicChange(new TopicEntity(targetTopic), EntityActionType.DELETED);
        }
        return new Pair<>(id, isRemoved);
    }
}
