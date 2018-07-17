package example.powercode.us.redditclonesample.ui.activities.base.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import example.powercode.us.redditclonesample.ui.activities.base.binding.Binding;

/**
 * Basic fragment which supports dependency injection
 */
public abstract class BaseInjectableFragment<B extends Binding> extends Fragment {

    @Inject RefWatcher refWatcher;
    @Inject protected B binding;

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

    @Override
    public void onViewCreated(
            @NonNull final View view,
            @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.attachView(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.detachView();
    }
}
