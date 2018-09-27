package example.powercode.us.redditclonesample.app.di.qualifiers;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

import androidx.fragment.app.FragmentActivity;

/**
 * Qualifier for Activity's FragmentManager {@link FragmentActivity#getSupportFragmentManager()}
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ParentFragmentManager {
}
