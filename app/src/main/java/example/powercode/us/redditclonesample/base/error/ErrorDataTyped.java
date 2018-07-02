package example.powercode.us.redditclonesample.base.error;

/**
 * Typed error holder
 */
public class ErrorDataTyped<ErrorTypes extends Enum<ErrorTypes>> implements ErrorBase {
    private final ErrorTypes errorTypes;
    private final String message;

    public ErrorDataTyped(String message, ErrorTypes errorTypes) {
        this.message = message;
        this.errorTypes = errorTypes;
    }

    public ErrorTypes getErrorTypes() {
        return errorTypes;
    }

    public String getMessage() {
        return message;
    }
}
