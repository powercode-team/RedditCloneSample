package example.powercode.us.redditclonesample.app.managers;

import android.arch.lifecycle.MutableLiveData;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.app.di.scopes.PerApplication;
import example.powercode.us.redditclonesample.model.common.Resource;
import timber.log.Timber;

/**
 * @author meugen
 */
@PerApplication
public class AppAsyncManager {

    private final ScheduledExecutorService executor;

    @Inject
    AppAsyncManager() {
        this.executor = Executors.newScheduledThreadPool(2);
    }

    public <T> void async(
            final Callable<Resource<T>> callable,
            final MutableLiveData<Resource<T>> liveData) {
        executor.execute(new CallableToLiveDataRunnable<>(callable, liveData));
    }

    public static class CallableToLiveDataRunnable<T> implements Runnable {

        private final Callable<Resource<T>> callable;
        private final WeakReference<MutableLiveData<Resource<T>>> liveDataRef;

        public CallableToLiveDataRunnable(
                final Callable<Resource<T>> callable,
                final MutableLiveData<Resource<T>> liveData) {
            this.callable = callable;
            this.liveDataRef = new WeakReference<>(liveData);
        }

        @Override
        public void run() {
            try {
                final Resource<T> resource = callable.call();
                postToLiveData(resource);
            } catch (Exception e) {
                Timber.d(e);
                postToLiveData(Resource.error(e));
            }
        }

        private void postToLiveData(final Resource<T> resource) {
            final MutableLiveData<Resource<T>> liveData = liveDataRef.get();
            if (liveData != null) {
                liveData.postValue(resource);
            }
        }
    }
}
