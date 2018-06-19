package example.powercode.us.redditclonesample.model.repository.impl;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.app.di.scopes.PerActivity;
import example.powercode.us.redditclonesample.app.di.scopes.PerApplication;
import example.powercode.us.redditclonesample.base.error.ErrorDataTyped;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.common.Status;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.error.ErrorsTopics;
import example.powercode.us.redditclonesample.model.repository.RepoTopics;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
@PerApplication
public class RepoTopicsImpl implements RepoTopics {
    @Inject
    RepoTopicsImpl() {

    }

    @Override
    public Single<Resource<List<TopicEntity>, ErrorDataTyped<ErrorsTopics>>> fetchTopics(@Nullable Comparator<? super TopicEntity> sortCmp, int count) {
        return Single
                .fromCallable(() -> Resource.success(generateItems(count), (ErrorDataTyped<ErrorsTopics>)null))
                .subscribeOn(Schedulers.computation())
                .map(unsortedItemsResource -> {
                    if (unsortedItemsResource.status != Status.SUCCESS
                            || sortCmp == null) {
                        return unsortedItemsResource;
                    }

                    List<TopicEntity> sortedItems = new ArrayList<>(unsortedItemsResource.data);
                    Collections.sort(sortedItems, sortCmp);

                    return Resource.success(sortedItems, (ErrorDataTyped<ErrorsTopics>)null);
                });
    }


    private static List<TopicEntity> generateItems(int count) {
        if (count <= 0) {
            return Collections.emptyList();
        }

        List<TopicEntity> items = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            items.add(new TopicEntity(i, "Very simple title " + i, 0));
        }

        return items;
    }
}
