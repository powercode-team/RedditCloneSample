package example.powercode.us.redditclonesample.app.di.qualifiers;

import android.support.v4.app.FragmentActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Qualifier for Activity's FragmentManager {@link FragmentActivity#getSupportFragmentManager()}
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ParentFragmentManager {
}
