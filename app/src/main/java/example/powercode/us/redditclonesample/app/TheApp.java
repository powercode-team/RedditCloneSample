package example.powercode.us.redditclonesample.app;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import example.powercode.us.redditclonesample.BuildConfig;
import example.powercode.us.redditclonesample.app.di.DaggerAppComponent;
import timber.log.Timber;

/**
 * Created by dev for RedditCloneSample on 12-Jun-18.
 */
public final class TheApp extends DaggerApplication {

    @Inject
    RefWatcher appRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent
                .builder()
                .application(this)
                .create(this);
    }
}
