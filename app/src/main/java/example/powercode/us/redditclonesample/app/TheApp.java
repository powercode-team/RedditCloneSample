package example.powercode.us.redditclonesample.app;

import android.app.Activity;
import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import example.powercode.us.redditclonesample.BuildConfig;
import example.powercode.us.redditclonesample.app.di.DaggerAppComponent;
import timber.log.Timber;

/**
 * Created by dev for RedditCloneSample on 12-Jun-18.
 */
public final class TheApp extends Application implements HasActivityInjector {

    private RefWatcher appRefWatcher;

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        appRefWatcher = LeakCanary.install(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        DaggerAppComponent
                .builder()
                .application(this)
                .create(this)
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }
}
