package example.powercode.us.redditclonesample.model.repository;

import android.support.annotation.Nullable;

import java.util.Comparator;
import java.util.List;

import example.powercode.us.redditclonesample.base.error.ErrorDataTyped;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.error.ErrorsTopics;
import io.reactivex.Single;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
public interface RepoTopics {
    Single<Resource<List<TopicEntity>, ErrorDataTyped<ErrorsTopics>>> fetchTopics(@Nullable Comparator<? super TopicEntity> sortCmp, int count);
}
