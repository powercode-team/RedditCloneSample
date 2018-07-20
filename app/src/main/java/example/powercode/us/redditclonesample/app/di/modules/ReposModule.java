package example.powercode.us.redditclonesample.app.di.modules;

import android.support.annotation.NonNull;

import dagger.Binds;
import dagger.Module;
import example.powercode.us.redditclonesample.app.di.scopes.PerApplication;
import example.powercode.us.redditclonesample.model.repository.RepoTopics;
import example.powercode.us.redditclonesample.model.repository.impl.RepoTopicsImpl;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
@Module
public interface ReposModule {

    @PerApplication
    @Binds
    RepoTopics bindRepoTopics(@NonNull RepoTopicsImpl impl);
}
