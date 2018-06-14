package example.powercode.us.redditclonesample.main.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.app.di.qualifiers.ActivityContext;
import example.powercode.us.redditclonesample.app.di.qualifiers.AppContext;
import example.powercode.us.redditclonesample.base.ui.BaseInjectableFragmentActivity;
import example.powercode.us.redditclonesample.base.ui.common.HasActionBar;
import example.powercode.us.redditclonesample.base.vm.ViewModelHelper;
import example.powercode.us.redditclonesample.databinding.ActivityMainBinding;
import example.powercode.us.redditclonesample.main.vm.MainViewModel;

public class MainActivity extends BaseInjectableFragmentActivity implements HasActionBar<ActionBar, Toolbar>,
        TopicListFragment.OnInteractionListener {

    @Inject
    ViewModelProvider.Factory vmFactory;

    private MainViewModel viewModel;

    private ActivityMainBinding binding;

    @Inject
    LocalNavigator localNavigator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = ViewModelHelper.getViewModel(this, MainViewModel.class, vmFactory);

        if (savedInstanceState == null) {
            localNavigator.putTopicsFragment();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        viewModel = null;
        binding = null;
    }

    @Override
    public ActionBar getAppBar() {
        return getSupportActionBar();
    }

    @Override
    public void setAppBar(@Nullable Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    /*/
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        dumpContexts();
    }

    private void dumpContexts() {
        Timber.d("This is an App context %s", appContext);
        Timber.d("This is an Activity context %s", thisContext);
    }
    //*/


    /**
     * Implementation of {@link TopicListFragment.OnInteractionListener}
     */
    @Override
    public void onCreateNewTopic() {

    }
}
