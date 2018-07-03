package example.powercode.us.redditclonesample.common.patterns;

/**
 * Created by dev for RedditCloneSample on 03-Jul-18.
 */
public abstract class CommandArgBase<Arg> extends CommandBase {
    public abstract void execute(Arg arg);

    public void execute() {
        execute(null);
    }

    public void undo() {
        // Default implementation doesn't support revert
        throw new UnsupportedOperationException("CommandArgBase::undo() is not supported, override in children");
    }
}
