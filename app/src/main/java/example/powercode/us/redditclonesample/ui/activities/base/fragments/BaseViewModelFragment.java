package example.powercode.us.redditclonesample.ui.activities.base.fragments;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.ui.activities.base.binding.Binding;

/**
 * Basic fragment which supports dependency injection
 * Inspired by https://proandroiddev.com/reducing-viewmodel-provision-boilerplate-in-googles-githubbrowsersample-549818ee72f0
 * Still not clear how to make it not to recreate ViewModel on device rotation
 */
public abstract class BaseViewModelFragment<VM extends ViewModel, B extends Binding>
        extends BaseInjectableFragment<B> {

    @Inject protected VM viewModel;

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void setViewModel(@NonNull VM viewModel) {
        this.viewModel = viewModel;
    }
}
