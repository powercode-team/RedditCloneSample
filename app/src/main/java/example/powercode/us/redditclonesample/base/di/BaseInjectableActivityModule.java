package example.powercode.us.redditclonesample.base.di;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import example.powercode.us.redditclonesample.app.di.qualifiers.ActivityContext;
import example.powercode.us.redditclonesample.app.di.scopes.PerActivity;
import example.powercode.us.redditclonesample.base.ui.BaseInjectableActivity;

/**
 * Binds activity to context, and {@link BaseInjectableActivity} to {@link AppCompatActivity}
 */
@Module
public interface BaseInjectableActivityModule {
    @PerActivity
    @ActivityContext
    @Binds
    Context bindActivityContext(final @NonNull AppCompatActivity activity);

    @PerActivity
    @Provides
    static LayoutInflater provideInflater(@NonNull @ActivityContext Context c) {
        return LayoutInflater.from(c);
    }

    @PerActivity
    @Binds
    AppCompatActivity bindActivity(final @NonNull BaseInjectableActivity activity);

    @Provides
    @PerActivity
    static PackageManager providePackageManager(final @NonNull BaseInjectableActivity activity) {
        return activity.getPackageManager();
    }
}
