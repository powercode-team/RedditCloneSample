package example.powercode.us.redditclonesample.base.ui;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dagger.android.AndroidInjection;

/**
 * Basic activity which supports dependency injection
 */
public abstract class BaseInjectableActivity extends AppCompatActivity {
    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }
}
