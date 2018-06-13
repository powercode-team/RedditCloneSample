package example.powercode.us.redditclonesample.base.ui.fragments;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;

import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Basic fragment which supports dependency injection
 */
public abstract class BaseInjectableFragment extends Fragment {
    @Inject
    RefWatcher refWatcher;

    @CallSuper
    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();
//        Assert.assertNotNull(getContext());
        refWatcher.watch(this/*, this.getClass().getSimpleName()*/);
    }
}
