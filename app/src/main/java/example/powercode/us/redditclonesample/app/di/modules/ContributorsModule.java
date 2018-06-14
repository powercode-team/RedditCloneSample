package example.powercode.us.redditclonesample.app.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import example.powercode.us.redditclonesample.app.di.scopes.PerActivity;
import example.powercode.us.redditclonesample.main.di.MainActivityModule;
import example.powercode.us.redditclonesample.main.ui.MainActivity;

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
