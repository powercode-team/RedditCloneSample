package example.powercode.us.redditclonesample.app.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import example.powercode.us.redditclonesample.app.di.mapkey.ViewModelKey;
import example.powercode.us.redditclonesample.base.ui.MainViewModelFactory;
import example.powercode.us.redditclonesample.main.MainViewModel;

/**
 * Module to provide view models
 */
@Module
public interface ViewModelModule {
    @Binds
    ViewModelProvider.Factory bindViewModelFactory(@NonNull MainViewModelFactory factory);

    @Binds @IntoMap @ViewModelKey(MainViewModel.class)
    ViewModel bindMainViewModel(@NonNull MainViewModel mainViewModel);
}
