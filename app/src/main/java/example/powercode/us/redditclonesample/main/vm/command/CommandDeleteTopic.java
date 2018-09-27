package example.powercode.us.redditclonesample.main.vm.command;

import androidx.annotation.NonNull;
import example.powercode.us.redditclonesample.common.patterns.CommandBase;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;

/**
 * Created by dev for RedditCloneSample on 03-Jul-18.
 */
public class CommandDeleteTopic extends CommandBase {
    @NonNull
    private final ReceiverCommandDelete receiver;

    private TopicEntity deletedTopic;

    public CommandDeleteTopic(@NonNull ReceiverCommandDelete r, @NonNull TopicEntity topicEntity) {
        this.receiver = r;
        deletedTopic = topicEntity;
    }

    @Override
    public void run() {
        receiver.deleteTopic(deletedTopic.id);
    }

    @Override
    public void undo() {
        receiver.undoDeleteTopic(deletedTopic);
    }
}
