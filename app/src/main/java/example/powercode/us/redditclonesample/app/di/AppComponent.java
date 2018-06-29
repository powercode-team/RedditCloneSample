package example.powercode.us.redditclonesample.app.di;

import android.app.Application;
import android.support.annotation.NonNull;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import example.powercode.us.redditclonesample.app.TheApp;
import example.powercode.us.redditclonesample.app.di.modules.AppModule;
import example.powercode.us.redditclonesample.app.di.scopes.PerApplication;

/**
 * Created by dev for RedditCloneSample on 12-Jun-18.
 */
@PerApplication
@Component(modules = {AppModule.class})
public interface AppComponent extends AndroidInjector<TheApp> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<TheApp> {}
}
