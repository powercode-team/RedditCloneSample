package example.powercode.us.redditclonesample.ui.activities.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.app.di.qualifiers.ParentFragmentManager;

/**
 * Created by dev for RedditCloneSample on 14-Jun-18.
 */
class LocalNavigator {
    @NonNull
    private final FragmentManager fragmentManager;

    @Inject
    LocalNavigator(@NonNull @ParentFragmentManager FragmentManager fm) {
        this.fragmentManager = fm;
    }

    public void popBackStack() {
        fragmentManager.popBackStack();
    }

    public void putTopicsFragment() {
        TopicListFragment tlf = (TopicListFragment) fragmentManager.findFragmentByTag(TopicListFragment.FRAGMENT_TAG);

        if (tlf == null) {
            tlf = TopicListFragment.newInstance();

            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, tlf, tlf.getFragmentTag())
                    .commit();
        } else {
            fragmentManager
                    .beginTransaction()
                    .show(tlf)
                    .commit();
        }
    }

    public void putTopicCreateFragment(boolean toBackStack, @Nullable String backStackName) {
        TopicCreateFragment tcf = (TopicCreateFragment) fragmentManager.findFragmentByTag(TopicCreateFragment.FRAGMENT_TAG);
        if (tcf == null) {
            tcf = TopicCreateFragment.newInstance();

            FragmentTransaction ft = fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, tcf, tcf.getFragmentTag());

            if (toBackStack) {
                ft.addToBackStack(backStackName);
            }

            ft.commit();
        }
        else {
            fragmentManager
                    .beginTransaction()
                    .show(tcf)
                    .commit();
        }
    }
}
