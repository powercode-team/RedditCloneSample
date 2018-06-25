package example.powercode.us.redditclonesample.main.vm;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.base.error.ErrorDataTyped;
import example.powercode.us.redditclonesample.common.arch.SingleLiveEvent;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.entity.EntityActionType;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.entity.VoteType;
import example.powercode.us.redditclonesample.model.error.ErrorsTopics;
import example.powercode.us.redditclonesample.model.repository.RepoTopics;
import example.powercode.us.redditclonesample.model.rules.BRulesTopics;
import io.reactivex.Observable;
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
        clearDisposable(compositeDisposable);

        Timber.d("VM of type [ %s ] was cleared \nid %s", TopicCreateViewModel.class.getSimpleName(), this);
    }

    private void clearDisposable(@Nullable Disposable d) {
        if (d != null && !d.isDisposed()) {
            d.dispose();
        }
    }

    public void newTopic(long id) {
        clearDisposable(disposableCreateTopic);
        disposableCreateTopic = createTopic(id);
    }

    private Disposable createTopic(long id) {
        return repoTopics
                .removeTopic(id)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    itemChangedLiveData.setValue(Resource.loading(id));
                })
                .subscribe(resultRemovedTopic -> {
                    Objects.requireNonNull(resultRemovedTopic.second);
                    if (resultRemovedTopic.second) {
                        itemChangedLiveData.setValue(Resource.success(resultRemovedTopic.first, null));
                    } else {
                        itemChangedLiveData.setValue(
                                Resource.error(
                                        new ErrorDataTyped<>(app
                                                .getResources()
                                                .getString(R.string.error_topic_with_id_not_found, resultRemovedTopic.first),
                                                ErrorsTopics.NO_ITEM),
                                        resultRemovedTopic.first
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
