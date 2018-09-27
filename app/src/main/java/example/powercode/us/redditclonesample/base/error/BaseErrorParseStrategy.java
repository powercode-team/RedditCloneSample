package example.powercode.us.redditclonesample.base.error;

import androidx.annotation.NonNull;

/**
 * Defines strategy to parse errors to appropriate view
 */
public abstract class BaseErrorParseStrategy<E extends ErrorBase> {
    public abstract <Response> E parse(@NonNull Response response);
    public abstract E parse(@NonNull Throwable th);
}
