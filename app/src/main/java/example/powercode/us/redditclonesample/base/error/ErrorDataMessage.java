package example.powercode.us.redditclonesample.base.error;

import example.powercode.us.redditclonesample.base.error.ErrorBase;

/**
 * Created by dev for YoungBusinessClub on 19-Mar-18.
 * Contains error message which will be used by model
 */
public class ErrorDataMessage implements ErrorBase {
    protected final String message;

    ErrorDataMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
