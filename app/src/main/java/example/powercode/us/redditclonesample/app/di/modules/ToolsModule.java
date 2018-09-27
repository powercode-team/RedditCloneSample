package example.powercode.us.redditclonesample.app.di.modules;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import example.powercode.us.redditclonesample.app.di.scopes.PerApplication;

/**
 * Created by dev for RedditCloneSample on 06-Jul-18.
 * Tool classes instantiation, bindings etc.
 */
@Module
public interface ToolsModule {
    @PerApplication
    @Provides
    static RefWatcher provideLeakCanaryWatcher(@NonNull Application app) {
        return LeakCanary.install(app);
    }
}
