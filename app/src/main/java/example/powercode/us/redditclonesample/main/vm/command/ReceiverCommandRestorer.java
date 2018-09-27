package example.powercode.us.redditclonesample.main.vm.command;

import androidx.annotation.NonNull;
import example.powercode.us.redditclonesample.model.entity.VoteType;

/**
 * Created by dev for RedditCloneSample on 03-Jul-18.
 */
public interface ReceiverCommandRestorer {
    void voteTopic(long id, @NonNull VoteType vt);
}
