package example.powercode.us.redditclonesample.main.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.List;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.base.error.ErrorDataTyped;
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
public class TopicsViewModel extends ViewModel {
    @NonNull
    private final MutableLiveData<Resource<List<TopicEntity>, ErrorDataTyped<ErrorsTopics>>> topicsLiveData = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<Resource<Long, ErrorDataTyped<ErrorsTopics>>> itemChangedLiveData = new MutableLiveData<>();

    @NonNull
    private final RepoTopics repoTopics;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    TopicsViewModel(@NonNull RepoTopics repoTopics) {
        Timber.d("VM of type [ %s ] constructor called \nid %s", TopicsViewModel.class.getSimpleName(), this);

        this.repoTopics = repoTopics;

        topicsLiveData.setValue(Resource.loading(null));

        subscribeToTopicChanges(repoTopics.onTopicChangeObservable());

        refreshTopics();
    }

    private void subscribeToTopicChanges(@NonNull Observable<Pair<TopicEntity, EntityActionType>> topicChangesObservable) {
        compositeDisposable.add( topicChangesObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicChanges -> {
//                    if (topicChanges.second == EntityActionType.INSERTED
//                            || topicChanges.second == EntityActionType.DELETED) {
                        refreshTopics();
//                    }
                }, Timber::e));
    }

    @NonNull
    private Disposable fetchTopics(@IntRange(from = 0, to=Integer.MAX_VALUE) int count) {
        return repoTopics
                .fetchTopics(BRulesTopics.TOPICS_LIST_COMPARATOR, count)
                .doOnSubscribe(disposable -> {
                    List<TopicEntity> oldValue = topicsLiveData.getValue() != null ? topicsLiveData.getValue().data : null;
                    topicsLiveData.setValue(Resource.loading(oldValue));
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicsLiveData::setValue,
                        throwable -> Timber.e(throwable));
    }

    public void refreshTopics() {
        compositeDisposable.add(fetchTopics(BRulesTopics.TOPICS_COUNT_WORKING_SET));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();

        Timber.d("VM of type [ %s ] was cleared \nid %s", TopicsViewModel.class.getSimpleName(), this);
    }

    @NonNull
    public LiveData<Resource<List<TopicEntity>, ErrorDataTyped<ErrorsTopics>>> getTopicsLiveData() {
        return topicsLiveData;
    }

    public void voteTopic(long id, @NonNull VoteType vt) {
        compositeDisposable.add(applyVoteTopic(id, vt));
    }

    @NonNull
    private Disposable applyVoteTopic(long id, @NonNull VoteType vt) {
        return repoTopics.applyVoteToTopic(id, vt)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    itemChangedLiveData.setValue(Resource.loading(id));
                })
                .subscribe(isApplied -> {
//                    Timber.d("Vote applied with result: %b", isApplied);
                    if (isApplied) {
                        itemChangedLiveData.setValue(Resource.success(id, null));
                    }
                }, throwable -> Timber.e(throwable));
    }

    @NonNull
    public LiveData<Resource<Long, ErrorDataTyped<ErrorsTopics>>> getApplyVoteLiveData() {
        return itemChangedLiveData;
    }
}
