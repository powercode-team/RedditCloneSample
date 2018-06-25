package example.powercode.us.redditclonesample.main.ui;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.app.di.qualifiers.FragmentContainerRes;
import example.powercode.us.redditclonesample.app.di.qualifiers.ParentFragmentManager;
import example.powercode.us.redditclonesample.app.di.scopes.PerActivity;

/**
 * Created by dev for RedditCloneSample on 14-Jun-18.
 */
@PerActivity
class LocalNavigator {
    @NonNull
    private final FragmentManager fragmentManager;
    private @IdRes
    int fragmentContainer;

    @Inject
    LocalNavigator(@NonNull @ParentFragmentManager FragmentManager fm, @FragmentContainerRes @IdRes int fragmentContainer) {
        this.fragmentManager = fm;
        this.fragmentContainer = fragmentContainer;
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
                    .replace(fragmentContainer, tlf, tlf.getFragmentTag())
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
                    .replace(fragmentContainer, tcf, tcf.getFragmentTag());

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
