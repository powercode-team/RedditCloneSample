package example.powercode.us.redditclonesample.base.ui.fragments;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;

import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import example.powercode.us.redditclonesample.base.vm.ViewModelHelper;

/**
 * Basic fragment which supports dependency injection
 */
public abstract class BaseViewModelFragment<VM extends ViewModel> extends BaseInjectableFragment {
    @Inject
    protected VM viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory viewModelFactory = ViewModelHelper.createFor(viewModel);
        ViewModelProviders.of(this, viewModelFactory).get(viewModel.getClass());
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void setViewModel(@NonNull VM viewModel) {
        this.viewModel = viewModel;
    }
}
