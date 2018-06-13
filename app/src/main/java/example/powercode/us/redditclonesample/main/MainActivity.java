package example.powercode.us.redditclonesample.main;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.app.di.qualifiers.ActivityContext;
import example.powercode.us.redditclonesample.app.di.qualifiers.AppContext;
import example.powercode.us.redditclonesample.base.ui.BaseInjectableFragmentActivity;
import example.powercode.us.redditclonesample.base.vm.ViewModelHelper;
import example.powercode.us.redditclonesample.databinding.ActivityMainBinding;
import timber.log.Timber;

public class MainActivity extends BaseInjectableFragmentActivity {

    @Inject
    ViewModelProvider.Factory vmFactory;

    private MainViewModel viewModel;

    private ActivityMainBinding binding;

    @Inject
    @AppContext
    Context appContext;

    @Inject
    @ActivityContext
    Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = ViewModelHelper.getViewModel(this, MainViewModel.class, vmFactory);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        viewModel = null;
        binding = null;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        dumpContexts();
    }

    private void dumpContexts() {
        Timber.d("This is an App context %s", appContext);
        Timber.d("This is an Activity context %s", thisContext);
    }
}
