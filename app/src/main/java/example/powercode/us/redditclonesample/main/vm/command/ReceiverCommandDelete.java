package example.powercode.us.redditclonesample.main.vm.command;

import android.support.annotation.NonNull;

import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.entity.VoteType;

/**
 * Created by dev for RedditCloneSample on 03-Jul-18.
 */
public interface ReceiverCommandDelete {
    void deleteTopic(long id);
    void undoDeleteTopic(@NonNull TopicEntity topic2Restore);
}
