package example.powercode.us.redditclonesample.ui.activities.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

/**
 * Performs dependency injection for the given Activity
 */
public abstract class BaseViewModelFragmentActivity<VM extends ViewModel> extends BaseInjectableFragmentActivity {
    @Inject
    ViewModelProvider.Factory factory;

    protected VM viewModel;

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this, factory).get(getViewModelClass());
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDetachFromViewModel();

        viewModel = null;
    }

    @NonNull
    protected abstract Class<VM> getViewModelClass();

    protected abstract void onDetachFromViewModel();
}
