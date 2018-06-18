package example.powercode.us.redditclonesample.main.vm;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by dev for RedditCloneSample on 18-Jun-18.
 */
public class TopicsViewModel extends ViewModel {
    @Inject
    public TopicsViewModel() {
        Timber.d("VM of type [ %s ] constructor called \nid %s", TopicsViewModel.class.getSimpleName(), this);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        Timber.d("VM of type [ %s ] was cleared \nid %s", TopicsViewModel.class.getSimpleName(), this);
    }
}
