package example.powercode.us.redditclonesample.common.patterns;

/**
 * Created by dev for RedditCloneSample on 03-Jul-18.
 */
public abstract class CommandBase {
    public abstract void execute();

    public void undo() {
        // Default implementation doesn't support revert
        throw new UnsupportedOperationException("CommandArgBase::undo() is not supported, override in children");
    }
}
