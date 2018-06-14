package example.powercode.us.redditclonesample.main.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * Created by dev for RedditCloneSample on 13-Jun-18.
 */
public final class MainViewModel extends AndroidViewModel {
    @Inject
    MainViewModel(@NonNull Application application) {
        super(application);
    }
}
