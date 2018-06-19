package example.powercode.us.redditclonesample.base.ui.fragments;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.base.vm.ViewModelHelper;

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

        viewModel = ViewModelHelper.getViewModel(this, getViewModelClass(), factory);

//        ViewModelProvider.Factory viewModelFactory = ViewModelHelper.createFor(viewModel);
//        ViewModelProviders.of(this, viewModelFactory).get(viewModel.getClass());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        onDetachFromViewModel();
        viewModel = null;
    }

    @NonNull
    protected abstract Class<VM> getViewModelClass();

    protected abstract void onDetachFromViewModel();

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void setViewModel(@NonNull VM viewModel) {
        this.viewModel = viewModel;
    }
}
