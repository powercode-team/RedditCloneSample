package example.powercode.us.redditclonesample.base.ui.fragments;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

/**
 * Basic fragment which supports dependency injection
 * Inspired by https://proandroiddev.com/reducing-viewmodel-provision-boilerplate-in-googles-githubbrowsersample-549818ee72f0
 * Still not clear how to make it not to recreate ViewModel on device rotation
 */
public abstract class BaseViewModelFragment<VM extends ViewModel> extends BaseInjectableFragment {

    @Inject
    ViewModelProvider.Factory factory;

    protected VM viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this, factory).get(getViewModelClass());
        onAttachViewModel();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        onDetachViewModel();
        viewModel = null;
    }

    @NonNull
    protected abstract Class<VM> getViewModelClass();

    /**
     * Called when viewModel has been attached to Fragment see {@link Fragment#onActivityCreated(Bundle)}
     */
    protected abstract void onAttachViewModel();

    /**
     * Called when viewModel is about to be detached from the Fragment {@link Fragment#onDestroyView()}
     */
    protected abstract void onDetachViewModel();

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void setViewModel(@NonNull VM viewModel) {
        this.viewModel = viewModel;
    }
}
