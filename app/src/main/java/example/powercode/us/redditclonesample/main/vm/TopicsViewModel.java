package example.powercode.us.redditclonesample.main.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.model.TopicEntity;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.base.error.ErrorDataTyped;
import example.powercode.us.redditclonesample.model.error.ErrorsTopics;
import timber.log.Timber;

/**
 * Created by dev for RedditCloneSample on 18-Jun-18.
 */
public class TopicsViewModel extends ViewModel {
    @NonNull
    private final MutableLiveData<Resource<List<TopicEntity>, ErrorDataTyped<ErrorsTopics>>> topicsLiveData = new MutableLiveData<>();

    @Inject
    TopicsViewModel() {
        Timber.d("VM of type [ %s ] constructor called \nid %s", TopicsViewModel.class.getSimpleName(), this);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        Timber.d("VM of type [ %s ] was cleared \nid %s", TopicsViewModel.class.getSimpleName(), this);
    }

    @NonNull
    public LiveData<Resource<List<TopicEntity>, ErrorDataTyped<ErrorsTopics>>> getTopicsLiveData() {
        return topicsLiveData;
    }
}
