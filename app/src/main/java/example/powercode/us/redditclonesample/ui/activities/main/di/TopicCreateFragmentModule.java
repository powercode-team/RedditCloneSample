package example.powercode.us.redditclonesample.ui.activities.main.di;

import dagger.Binds;
import dagger.Module;
import example.powercode.us.redditclonesample.app.di.scopes.PerFragment;
import example.powercode.us.redditclonesample.ui.activities.base.di.BaseInjectableFragmentModule;
import example.powercode.us.redditclonesample.ui.activities.main.binding.TopicCreateBinding;
import example.powercode.us.redditclonesample.ui.activities.main.binding.TopicCreateBindingImpl;

/**
 * @author meugen
 */
@Module(includes = BaseInjectableFragmentModule.class)
public interface TopicCreateFragmentModule {

    @Binds @PerFragment
    TopicCreateBinding bindBinding(TopicCreateBindingImpl impl);
}
