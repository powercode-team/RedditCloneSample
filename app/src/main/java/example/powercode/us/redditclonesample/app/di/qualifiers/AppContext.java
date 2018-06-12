package example.powercode.us.redditclonesample.app.di.qualifiers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Qualifier for application context
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface AppContext {
}
