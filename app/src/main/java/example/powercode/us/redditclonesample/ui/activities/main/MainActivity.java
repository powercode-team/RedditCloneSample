package example.powercode.us.redditclonesample.ui.activities.main;

import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.databinding.ActivityMainBinding;
import example.powercode.us.redditclonesample.ui.activities.base.BaseViewModelFragmentActivity;
import example.powercode.us.redditclonesample.ui.activities.main.vm.MainViewModel;
import timber.log.Timber;

public class MainActivity extends BaseViewModelFragmentActivity<MainViewModel> implements
        TopicListFragment.OnInteractionListener, TopicCreateFragment.OnInteractionListener {

    @Inject
    ViewModelProvider.Factory vmFactory;

    private ActivityMainBinding binding;

    @Inject
    LocalNavigator localNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (savedInstanceState == null) {
            localNavigator.putTopicsFragment();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        binding = null;
    }

    @NonNull
    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected void onDetachFromViewModel() {

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
        localNavigator.putTopicCreateFragment(true, null);
    }

    /**
     * Implementation of {@link TopicCreateFragment.OnInteractionListener}
     */
    @Override
    public void onTopicCreated(long newTopicId) {
        Timber.d("Created topic with id: %d", newTopicId);
        localNavigator.popBackStack();
    }

    @Override
    public void onTopicCreateCancelled() {
        localNavigator.popBackStack();
    }
}
