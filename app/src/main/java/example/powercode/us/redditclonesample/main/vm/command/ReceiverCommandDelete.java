package example.powercode.us.redditclonesample.main.vm.command;

import androidx.annotation.NonNull;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;

/**
 * Created by dev for RedditCloneSample on 03-Jul-18.
 */
public interface ReceiverCommandDelete {
    void deleteTopic(long id);
    void undoDeleteTopic(@NonNull TopicEntity topic2Restore);
}
