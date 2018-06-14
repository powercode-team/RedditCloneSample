package example.powercode.us.redditclonesample.base.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import example.powercode.us.redditclonesample.app.di.scopes.PerActivity;

/**
 * Main view-model creator which allows to inject {@link ViewModel} descendants
 */
public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final @NonNull Map<Class<? extends ViewModel>, Provider<ViewModel>> creators_;

    @Inject
    MainViewModelFactory(@NonNull Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators_ = creators;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<? extends ViewModel> creator = creators_.get(modelClass);
        if (creator == null) {
            for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : creators_.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException("unknown model class " + modelClass + " probably forgot to add @Bind into ViewModelModule");
        }
        try {
            return (T) creator.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
