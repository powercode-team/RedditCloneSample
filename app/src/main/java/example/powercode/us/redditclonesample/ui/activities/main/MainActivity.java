package example.powercode.us.redditclonesample.ui.activities.main;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.ui.activities.base.BaseViewModelFragmentActivity;
import example.powercode.us.redditclonesample.ui.activities.main.vm.MainViewModel;
import timber.log.Timber;

public class MainActivity extends BaseViewModelFragmentActivity<MainViewModel> implements
        TopicListFragment.OnInteractionListener, TopicCreateFragment.OnInteractionListener {

    @Inject
    ViewModelProvider.Factory vmFactory;

    @Inject
    LocalNavigator localNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            localNavigator.putTopicsFragment();
        }
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
