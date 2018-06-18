package example.powercode.us.redditclonesample.base.vm;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by dev for RedditCloneSample on 13-Jun-18.
 */
public interface ViewModelHelper {
    // To avoid DRY
    static <VM extends ViewModel> VM getViewModel(@NonNull FragmentActivity activity, @NonNull Class<VM> vmClass, @Nullable ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(activity, factory).get(vmClass);
    }

    static <VM extends ViewModel> VM getViewModel(@NonNull Fragment fragment, @NonNull Class<VM> vmClass, @Nullable ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(vmClass);
    }

    static <VM extends ViewModel> ViewModelProvider.Factory createFor(@NonNull final VM model) {
        return new ViewModelProvider.Factory() {
            @SuppressWarnings(value={"unchecked"})
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(model.getClass())) {
                    return (T)model;
                }

                throw new IllegalArgumentException("unknown model class " + modelClass);
            }
        };
    }
}
