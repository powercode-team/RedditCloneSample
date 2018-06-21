package example.powercode.us.redditclonesample.main.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.base.error.ErrorDataTyped;
import example.powercode.us.redditclonesample.model.entity.VoteType;
import example.powercode.us.redditclonesample.model.error.ErrorsTopics;
import example.powercode.us.redditclonesample.model.repository.RepoTopics;
import example.powercode.us.redditclonesample.model.rules.BRulesTopics;
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
    private final RepoTopics repoTopics;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    TopicsViewModel(@NonNull RepoTopics repoTopics) {
        Timber.d("VM of type [ %s ] constructor called \nid %s", TopicsViewModel.class.getSimpleName(), this);

        this.repoTopics = repoTopics;

        topicsLiveData.setValue(Resource.loading(null));

        compositeDisposable.add(fetchTopics(BRulesTopics.TOPICS_COUNT_WORKING_SET));
    }

    @NonNull
    private Disposable fetchTopics(@IntRange(from = 0, to=Integer.MAX_VALUE) int count) {
        return repoTopics
                .fetchTopics(BRulesTopics.TOPICS_LIST_COMPARATOR, count)
                .doOnSubscribe(disposable -> {
                    topicsLiveData.setValue(topicsLiveData.getValue());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicsLiveData::setValue,
                        throwable -> {
                            Timber.e(throwable);
                        });
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
//        compositeDisposable.add(applyVoteTopic(id, vt));
    }

    @NonNull
    private Disposable applyVoteTopic(long id, @NonNull VoteType vt) {
        return null;
    }
}
