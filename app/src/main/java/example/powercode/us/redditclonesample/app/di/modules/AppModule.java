package example.powercode.us.redditclonesample.app.di.modules;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import dagger.Binds;
import dagger.Module;
import dagger.android.support.AndroidSupportInjectionModule;
import example.powercode.us.redditclonesample.app.di.qualifiers.AppContext;
import example.powercode.us.redditclonesample.app.di.scopes.PerApplication;
import example.powercode.us.redditclonesample.model.di.ReposModule;

/**
 * Created by dev for RedditCloneSample on 12-Jun-18.
 */
@Module (includes = {AndroidSupportInjectionModule.class, ContributorsModule.class, ViewModelModule.class, ReposModule.class})
public interface AppModule {
    // Bind in AppComponent
//    @PerApplication
//    @Binds
//    Application bindApplication(@NonNull TheApp app);

    @PerApplication
    @AppContext
    @Binds
    Context bindAppContext(@NonNull Application app);
}
