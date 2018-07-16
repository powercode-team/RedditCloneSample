package example.powercode.us.redditclonesample.app.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import example.powercode.us.redditclonesample.app.di.scopes.PerActivity;
import example.powercode.us.redditclonesample.ui.activities.main.MainActivity;
import example.powercode.us.redditclonesample.ui.activities.main.di.MainActivityModule;

/**
 *  Place for application sub-components such as Activities, Services, BroadcastReceivers
 */
@Module
public interface ContributorsModule {
    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    @PerActivity
    MainActivity contributeSplashScreenActivity();

//    @ContributesAndroidInjector
//    @PerService
//    JobInstanceIdService contributeJobInstanceIdService();
}
