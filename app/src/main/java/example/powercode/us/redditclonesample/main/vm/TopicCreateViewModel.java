package example.powercode.us.redditclonesample.main.vm;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.base.error.ErrorDataTyped;
import example.powercode.us.redditclonesample.common.arch.SingleLiveEvent;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.error.ErrorsTopics;
import example.powercode.us.redditclonesample.model.repository.RepoTopics;
import example.powercode.us.redditclonesample.utils.RxUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by dev for RedditCloneSample on 18-Jun-18.
 */
public class TopicCreateViewModel extends ViewModel {
    @NonNull
    private final MutableLiveData<Resource<Long, ErrorDataTyped<ErrorsTopics>>> itemChangedLiveData = new SingleLiveEvent<>();

    @NonNull
    private final Application app;

    @NonNull
    private final RepoTopics repoTopics;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nullable
    private Disposable disposableCreateTopic = null;

    @Inject
    TopicCreateViewModel(@NonNull Application app, @NonNull RepoTopics repoTopics) {
        this.app = app;
        this.repoTopics = repoTopics;
        Timber.d("VM of type [ %s ] constructor called \nid %s", TopicCreateViewModel.class.getSimpleName(), this);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        RxUtils.clearDisposableSafe(compositeDisposable);
        RxUtils.clearDisposableSafe(disposableCreateTopic);

        Timber.d("VM of type [ %s ] was cleared \nid %s", TopicCreateViewModel.class.getSimpleName(), this);
    }

    public void newTopic(@NonNull String title, int rating) {
        RxUtils.clearDisposableSafe(disposableCreateTopic);
        disposableCreateTopic = createTopic(title, rating);
    }

    private Disposable createTopic(@NonNull String title, int rating) {
        return repoTopics
                .createTopic(title, rating)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    itemChangedLiveData.setValue(Resource.loading(null));
                })
                .subscribe(resultCreateTopic -> {
                    Objects.requireNonNull(resultCreateTopic.second);
                    if (resultCreateTopic.second) {
                        itemChangedLiveData.setValue(Resource.success(resultCreateTopic.first, null));
                    } else {
                        itemChangedLiveData.setValue(
                                Resource.error(
                                        new ErrorDataTyped<>(app
                                                .getResources().getString(R.string.error_topic_create),
                                                ErrorsTopics.NO_ITEM),
                                        resultCreateTopic.first
                                )
                        );
                    }
                }, throwable -> Timber.e(throwable));
    }

    @NonNull
    public LiveData<Resource<Long, ErrorDataTyped<ErrorsTopics>>> getCreateTopicLiveData() {
        return itemChangedLiveData;
    }
}
