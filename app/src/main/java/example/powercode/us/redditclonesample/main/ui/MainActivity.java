package example.powercode.us.redditclonesample.main.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.base.ui.BaseInjectableFragmentActivity;
import example.powercode.us.redditclonesample.base.vm.ViewModelHelper;
import example.powercode.us.redditclonesample.databinding.ActivityMainBinding;
import example.powercode.us.redditclonesample.main.vm.MainViewModel;

public class MainActivity extends BaseInjectableFragmentActivity implements
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
