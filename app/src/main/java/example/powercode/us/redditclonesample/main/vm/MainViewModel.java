package example.powercode.us.redditclonesample.main.vm;

import android.app.Application;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * Created by dev for RedditCloneSample on 13-Jun-18.
 */
public final class MainViewModel extends AndroidViewModel {
    @Inject
    MainViewModel(@NonNull Application application) {
        super(application);
    }
}
