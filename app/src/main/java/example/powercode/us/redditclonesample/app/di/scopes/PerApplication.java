package example.powercode.us.redditclonesample.app.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Per Application scope
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApplication {
}
