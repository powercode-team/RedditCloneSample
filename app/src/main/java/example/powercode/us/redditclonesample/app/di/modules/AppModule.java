package example.powercode.us.redditclonesample.app.di.modules;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.squareup.leakcanary.RefWatcher;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.support.AndroidSupportInjectionModule;
import example.powercode.us.redditclonesample.app.TheApp;
import example.powercode.us.redditclonesample.app.di.qualifiers.AppContext;
import example.powercode.us.redditclonesample.app.di.scopes.PerApplication;

/**
 * Created by dev for RedditCloneSample on 12-Jun-18.
 */
@Module (includes = {AndroidSupportInjectionModule.class, ContributorsModule.class, ViewModelModule.class, ReposModule.class})
public interface AppModule {
    @PerApplication
    @Provides
    static @NonNull RefWatcher provideRefWatcher(@NonNull TheApp app) {
        return app.getAppRefWatcher();
    }

    @PerApplication
    @Binds
    Application bindApplication(@NonNull TheApp app);

    @PerApplication
    @AppContext
    @Binds
    Context bindAppContext(@NonNull Application app);
}
