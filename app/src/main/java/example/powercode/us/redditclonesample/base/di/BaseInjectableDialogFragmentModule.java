package example.powercode.us.redditclonesample.base.di;

import android.support.v7.app.AppCompatDialogFragment;

import dagger.Binds;
import dagger.Module;
import example.powercode.us.redditclonesample.app.di.scopes.PerFragment;
import example.powercode.us.redditclonesample.base.ui.fragments.BaseInjectableDialogFragment;

/**
 * Binds activity to context, and {@link BaseInjectableDialogFragment} to {@link AppCompatDialogFragment}
 */
@Module
public interface BaseInjectableDialogFragmentModule {

    @Binds @PerFragment
    AppCompatDialogFragment bindFragment(BaseInjectableDialogFragment fragment);
}
