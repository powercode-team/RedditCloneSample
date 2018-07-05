package example.powercode.us.redditclonesample.main.vm.command;

import android.support.annotation.NonNull;

import example.powercode.us.redditclonesample.common.patterns.CommandBase;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.entity.VoteType;

/**
 * Created by dev for RedditCloneSample on 03-Jul-18.
 */
public class CommandVoteTopic extends CommandBase {
    @NonNull
    private final ReceiverCommandVoteTopic receiver;

    private final long topicId;
    @NonNull
    private final VoteType vt;

    public CommandVoteTopic(@NonNull ReceiverCommandVoteTopic r, long topicId, @NonNull VoteType vt) {
        this.receiver = r;
        this.topicId = topicId;
        this.vt = vt;
    }

    @Override
    public void execute() {
        receiver.voteTopic(topicId, vt);
    }

    @Override
    public void undo() {
    }
}
