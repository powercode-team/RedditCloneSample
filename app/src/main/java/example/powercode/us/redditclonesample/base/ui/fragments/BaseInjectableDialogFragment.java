package example.powercode.us.redditclonesample.base.ui.fragments;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatDialogFragment;

import dagger.android.support.AndroidSupportInjection;

/**
 * Brings support of Dependency Injection into classes which extend {@link AppCompatDialogFragment}
 */
public abstract class BaseInjectableDialogFragment extends AppCompatDialogFragment {

    @CallSuper
    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
}
