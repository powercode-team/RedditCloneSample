package example.powercode.us.redditclonesample.main.vm.command;

import android.support.annotation.NonNull;

import example.powercode.us.redditclonesample.common.patterns.CommandBase;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;

/**
 * Created by dev for RedditCloneSample on 03-Jul-18.
 */
public class CommandDeleteTopic extends CommandBase {
    @NonNull
    private final ReceiverCommandDelete deleter;

    private TopicEntity deletedTopic;

    public CommandDeleteTopic(@NonNull ReceiverCommandDelete deleter, @NonNull TopicEntity topicEntity) {
        this.deleter = deleter;
        deletedTopic = topicEntity;
    }

    @Override
    public void execute() {
        deleter.deleteTopic(deletedTopic.id);
    }

    @Override
    public void undo() {
        deleter.undoDeleteTopic(deletedTopic);
    }
}
