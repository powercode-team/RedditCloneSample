package example.powercode.us.redditclonesample.ui.activities.main.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.app.managers.AppAsyncManager;
import example.powercode.us.redditclonesample.common.arch.SingleLiveEvent;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.repository.RepoTopics;
import timber.log.Timber;

/**
 * Created by dev for RedditCloneSample on 18-Jun-18.
 */
public class TopicCreateViewModel extends ViewModel {
    @NonNull
    private final MutableLiveData<Resource<Long>> itemChangedLiveData = new SingleLiveEvent<>();

    @NonNull
    private final RepoTopics repoTopics;
    @NonNull
    private final AppAsyncManager asyncManager;

    @Inject
    TopicCreateViewModel(
            @NonNull RepoTopics repoTopics,
            @NonNull AppAsyncManager asyncManager) {
        this.repoTopics = repoTopics;
        this.asyncManager = asyncManager;
        Timber.d("VM of type [ %s ] constructor called \nid %s", TopicCreateViewModel.class.getSimpleName(), this);
    }

    public void newTopic(@NonNull String title, int rating) {
        itemChangedLiveData.setValue(Resource.loading(null));
        asyncManager.async(new CreateTopicCallable(repoTopics, title, rating), itemChangedLiveData);
    }

    @NonNull
    public LiveData<Resource<Long>> getCreateTopicLiveData() {
        return itemChangedLiveData;
    }

    private static class CreateTopicCallable implements Callable<Resource<Long>> {

        private final RepoTopics repoTopics;
        private final String title;
        private final int rating;

        CreateTopicCallable(
                final RepoTopics repoTopics,
                final String title,
                final int rating) {
            this.repoTopics = repoTopics;
            this.title = title;
            this.rating = rating;
        }

        @Override
        public Resource<Long> call() throws Exception {
            final Pair<Long, Boolean> pair = repoTopics.createTopic(title, rating);
            if (pair.second) {
                return Resource.success(pair.first);
            }
            throw new TopicNotFoundException(pair.first);
        }
    }
}
