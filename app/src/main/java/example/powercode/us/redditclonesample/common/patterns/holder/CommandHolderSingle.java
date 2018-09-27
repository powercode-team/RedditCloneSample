package example.powercode.us.redditclonesample.common.patterns.holder;

import java.util.ArrayDeque;
import java.util.Deque;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import example.powercode.us.redditclonesample.common.patterns.CommandBase;

/**
 * Created by dev for RedditCloneSample on 03-Jul-18.
 */
public class CommandHolderSingle extends CommandHolder {
    private static final int ITEMS_COUNT_TO_HOLD = 1;

    @NonNull
    private final Deque<CommandBase> commands = new ArrayDeque<>();


    @Inject
    public CommandHolderSingle() {
    }

    @Override
    public void push(@NonNull CommandBase c) {
        while (!isEmpty() && (size() >= ITEMS_COUNT_TO_HOLD)) {
            pop();
        }

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
