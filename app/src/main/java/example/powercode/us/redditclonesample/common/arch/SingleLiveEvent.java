package example.powercode.us.redditclonesample.common.arch;

import android.arch.core.internal.SafeIterableMap;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import timber.log.Timber;

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 * <p>
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 * <p>
 * Note that only one observer is going to be notified of changes.
 */
public class SingleLiveEvent<T> extends MutableLiveData<T> {

    private final AtomicBoolean mPending = new AtomicBoolean(false);

    private Map<Integer, Observer<T>> internalObserverWrappers = new HashMap<>();

    @MainThread
    public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<T> observer) {
        if (hasActiveObservers()) {
            Timber.w("Multiple observers registered but only one will be notified of changes.");
        }

        if (!addObserverWrapper(observer)) {
            return;
        }

        // Observe the internal MutableLiveData
        final Observer<T> wrapper = internalObserverWrappers.get(observer.hashCode());
        super.observe(owner, wrapper);
    }

    private boolean addObserverWrapper(@NonNull Observer<T> observer) {
        if (internalObserverWrappers.containsKey(observer.hashCode())) {
            return false;
        }

        internalObserverWrappers.put(observer.hashCode(), t -> {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t);
            }
        });

        return true;
    }

    /**
     * Removes the given observer from the observers list.
     *
     * @param observer The Observer to receive events.
     */
    @MainThread
    @Override
    public void removeObserver(@NonNull Observer<T> observer) {
        Observer<T> wrapper = internalObserverWrappers.remove(observer.hashCode());
        if (wrapper == null) {
            return;
        }

        super.removeObserver(wrapper);
    }

    /**
     * Removes all observers that are tied to the given {@link LifecycleOwner}.
     *
     * @param owner The {@code LifecycleOwner} scope for the observers to be removed.
     */
    @MainThread
    @Override
    public void removeObservers(@NonNull LifecycleOwner owner) {
        internalObserverWrappers.clear();
        super.removeObservers(owner);
    }

    @MainThread
    public void setValue(@Nullable T t) {
        mPending.set(true);
        super.setValue(t);
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    public void call() {
        setValue(null);
    }
}
