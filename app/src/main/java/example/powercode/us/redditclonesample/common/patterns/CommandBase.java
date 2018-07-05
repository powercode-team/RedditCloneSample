package example.powercode.us.redditclonesample.common.patterns;

/**
 * Created by dev for RedditCloneSample on 03-Jul-18.
 */
public abstract class CommandBase implements Runnable {

    /**
     * Default implementation does not support undo, so derived classes must re-implement it
     */
    public void undo() {
        throw new UnsupportedOperationException("CommandArgBase::undo() is not supported, override in children");
    }

    /**
     * Simply call execute
     */
    public void redo() {
        run();
    }
}
