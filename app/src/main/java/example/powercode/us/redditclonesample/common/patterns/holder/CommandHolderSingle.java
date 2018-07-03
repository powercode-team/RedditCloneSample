package example.powercode.us.redditclonesample.common.patterns.holder;

import android.support.annotation.NonNull;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.common.patterns.CommandBase;

/**
 * Created by dev for RedditCloneSample on 03-Jul-18.
 */
public class CommandHolderSingle extends CommandHolder {
    private final Deque<CommandBase> commands = new ArrayDeque<>();

    @Inject
    public CommandHolderSingle() {
    }

    @Override
    public void push(@NonNull CommandBase c) {
        commands.push(c);
    }

    @Override
    public CommandBase pop() {
        return commands.pop();
    }

    @Override
    public CommandBase current() {
        return commands.peek();
    }

    @Override
    public int size() {
        return commands.size();
    }

    @Override
    public boolean isEmpty() {
        return commands.isEmpty();
    }

    @Override
    public void clear() {
        commands.clear();
    }
}
