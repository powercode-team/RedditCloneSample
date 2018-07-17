package example.powercode.us.redditclonesample.ui.activities.main.di;

import dagger.Binds;
import dagger.Module;
import example.powercode.us.redditclonesample.app.di.scopes.PerFragment;
import example.powercode.us.redditclonesample.ui.activities.base.binding.Binding;
import example.powercode.us.redditclonesample.ui.activities.base.binding.BindingImpl;
import example.powercode.us.redditclonesample.ui.activities.base.di.BaseInjectableFragmentModule;

/**
 * @author meugen
 */
@Module(includes = BaseInjectableFragmentModule.class)
public interface TopicCreateFragmentModule {

    @Binds @PerFragment
    Binding bindBinding(BindingImpl impl);
}
