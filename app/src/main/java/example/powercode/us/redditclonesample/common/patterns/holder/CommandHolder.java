package example.powercode.us.redditclonesample.common.patterns.holder;

import android.support.annotation.NonNull;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

import example.powercode.us.redditclonesample.common.patterns.CommandBase;

/**
 * Created by dev for RedditCloneSample on 03-Jul-18.
 */
public abstract class CommandHolder {
    public abstract void push(@NonNull CommandBase c);

    public abstract CommandBase pop();

    public abstract CommandBase current();

    public abstract void clear();

    public abstract int size();

    public abstract boolean isEmpty();
}
