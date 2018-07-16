package example.powercode.us.redditclonesample.common.arch;

import android.arch.lifecycle.MutableLiveData;

import example.powercode.us.redditclonesample.common.functional.Consumer;

/**
 * @author meugen
 */
public class LiveConsumer<T> implements Consumer<T> {

    private final MutableLiveData<T> liveData;

    public LiveConsumer(final MutableLiveData<T> liveData) {
        this.liveData = liveData;
    }

    @Override
    public void accept(final T t) {
        liveData.postValue(t);
    }
}
