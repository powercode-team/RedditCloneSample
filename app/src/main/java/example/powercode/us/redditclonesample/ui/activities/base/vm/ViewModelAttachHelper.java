package example.powercode.us.redditclonesample.ui.activities.base.vm;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import example.powercode.us.redditclonesample.common.functional.Predicate;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.common.Status;

/**
 * Created by dev for RedditCloneSample on 26-Jun-18.
 */
public interface ViewModelAttachHelper {
    static <R extends Resource<?, ?>, LD extends LiveData<R>> boolean attachObserverIf(@NonNull LD observable,
                                                                                       @NonNull Predicate<R> condition,
                                                                                       @NonNull LifecycleOwner owner,
                                                                                       Observer<R> observer) {
        if (condition.test(observable.getValue())) {
            observable.observe(owner, observer);
            return true;
        }

        return false;
    }

    static <T extends Resource<?, ?>, LD extends LiveData<T>> boolean attachObserverIfLoading(@NonNull LD observable,
                                                                                             @NonNull LifecycleOwner owner,
                                                                                             Observer<T> observer) {
        return attachObserverIf(observable,
                res -> res != null && res.status == Status.LOADING,
                owner, observer);
    }
}