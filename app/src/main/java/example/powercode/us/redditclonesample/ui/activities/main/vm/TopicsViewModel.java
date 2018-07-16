package example.powercode.us.redditclonesample.ui.activities.main.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.app.managers.AppAsyncManager;
import example.powercode.us.redditclonesample.common.arch.SingleLiveEvent;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.entity.EntityActionType;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.entity.VoteType;
import example.powercode.us.redditclonesample.model.repository.RepoTopics;
import example.powercode.us.redditclonesample.model.rules.BRulesTopics;
import timber.log.Timber;

/**
 * Created by dev for RedditCloneSample on 18-Jun-18.
 */
public class TopicsViewModel extends ViewModel implements RepoTopics.OnTopicChangeListener {
    @NonNull
    private final MutableLiveData<Resource<List<TopicEntity>>> topicsLiveData = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Resource<Long>> itemChangedLiveData = new SingleLiveEvent<>();
    @NonNull
    private final MutableLiveData<Resource<Long>> itemRemovedLiveData = new SingleLiveEvent<>();

    @NonNull
    private final RepoTopics repoTopics;
    private final AppAsyncManager asyncManager;

    @Inject
    TopicsViewModel(
            @NonNull RepoTopics repoTopics,
            @NonNull AppAsyncManager asyncManager) {
        this.repoTopics = repoTopics;
        this.asyncManager = asyncManager;
        Timber.d("VM of type [ %s ] constructor called \nid %s", TopicsViewModel.class.getSimpleName(), this);

        topicsLiveData.setValue(Resource.loading(null));

        repoTopics.setOnTopicChangeListener(this);
        refreshTopics();
    }

    @Override
    public void onTopicChange(
            final TopicEntity entity,
            final EntityActionType type) {
        refreshTopics();
    }

    private void refreshTopics(final int count) {
        List<TopicEntity> oldValue = topicsLiveData.getValue() != null
                ? topicsLiveData.getValue().data : null;
        topicsLiveData.postValue(Resource.loading(oldValue));

        asyncManager.async(new FetchTopicsCallable(repoTopics, count), topicsLiveData);
    }

    private void refreshTopics() {
        refreshTopics(BRulesTopics.TOPICS_COUNT_WORKING_SET);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repoTopics.setOnTopicChangeListener(null);

        Timber.d("VM of type [ %s ] was cleared \nid %s", TopicsViewModel.class.getSimpleName(), this);
    }

    @NonNull
    public LiveData<Resource<List<TopicEntity>>> getTopicsLiveData() {
        return topicsLiveData;
    }

    public void voteTopic(long id, @NonNull VoteType vt) {
        itemChangedLiveData.postValue(Resource.loading(id));
        asyncManager.async(new VoteTopicCallable(repoTopics, id, vt), itemChangedLiveData);
    }

    @NonNull
    public LiveData<Resource<Long>> getApplyVoteLiveData() {
        return itemChangedLiveData;
    }

    public void deleteTopic(long id) {
        itemRemovedLiveData.postValue(Resource.loading(id));
        asyncManager.async(new RemoveTopicCallable(repoTopics, id), itemRemovedLiveData);
    }

    @NonNull
    public LiveData<Resource<Long>> getDeleteTopicLiveData() {
        return itemRemovedLiveData;
    }

    private static class FetchTopicsCallable implements Callable<Resource<List<TopicEntity>>> {

        private final RepoTopics repoTopics;
        private final int count;

        FetchTopicsCallable(final RepoTopics repoTopics, final int count) {
            this.repoTopics = repoTopics;
            this.count = count;
        }

        @Override
        public Resource<List<TopicEntity>> call() throws Exception {
            return repoTopics.fetchTopics(BRulesTopics.TOPICS_LIST_COMPARATOR, count);
        }
    }

    private static class VoteTopicCallable implements Callable<Resource<Long>> {

        private final RepoTopics repoTopics;
        private final long id;
        private final VoteType voteType;

        VoteTopicCallable(
                final RepoTopics repoTopics,
                final long id,
                final VoteType voteType) {
            this.repoTopics = repoTopics;
            this.id = id;
            this.voteType = voteType;
        }

        @Override
        public Resource<Long> call() throws Exception {
            final Pair<Long, Boolean> pair = repoTopics.applyVoteToTopic(id, voteType);
            if (pair.second) {
                return Resource.success(pair.first);
            }
            throw new TopicNotFoundException(pair.first);
        }
    }

    private static class RemoveTopicCallable implements Callable<Resource<Long>> {

        private final RepoTopics repoTopics;
        private final long id;

        RemoveTopicCallable(
                final RepoTopics repoTopics,
                final long id) {
            this.repoTopics = repoTopics;
            this.id = id;
        }

        @Override
        public Resource<Long> call() throws Exception {
            final Pair<Long, Boolean> pair = repoTopics.removeTopic(id);
            if (pair.second) {
                return Resource.success(pair.first);
            }
            throw new TopicNotFoundException(pair.first);
        }
    }

}
