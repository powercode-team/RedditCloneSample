package example.powercode.us.redditclonesample.base.ui;

import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Basic activity which supports dependency injection into its {@link Fragment}
 */
public abstract class BaseInjectableFragmentActivity extends BaseInjectableActivity
        implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector_;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector_;
    }
}
