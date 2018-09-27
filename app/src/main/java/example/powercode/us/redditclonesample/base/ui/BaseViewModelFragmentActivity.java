package example.powercode.us.redditclonesample.base.ui;

import android.os.Bundle;


import javax.inject.Inject;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

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
