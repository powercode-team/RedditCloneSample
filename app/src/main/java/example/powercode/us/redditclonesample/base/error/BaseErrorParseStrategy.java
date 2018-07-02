package example.powercode.us.redditclonesample.base.error;

import android.support.annotation.NonNull;

import example.powercode.us.redditclonesample.base.error.ErrorBase;

/**
 * Defines strategy to parse errors to appropriate view
 */
public abstract class BaseErrorParseStrategy<E extends ErrorBase> {
    public abstract <Response> E parse(@NonNull Response response);
    public abstract E parse(@NonNull Throwable th);
}
