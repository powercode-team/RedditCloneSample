package example.powercode.us.redditclonesample.app.di.modules;

import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import dagger.Binds;
import dagger.Module;
import example.powercode.us.redditclonesample.base.ui.MainViewModelFactory;

/**
 * Module to provide view models
 */
@Module
public interface ViewModelModule {
    @Binds
    ViewModelProvider.Factory bindViewModelFactory(@NonNull MainViewModelFactory factory);

//    @Binds @IntoMap @ViewModelKey(SplashScreenViewModel.class)
//    ViewModel bindSplashViewModel(@NonNull SplashScreenViewModel splashScreenViewModel);
}
