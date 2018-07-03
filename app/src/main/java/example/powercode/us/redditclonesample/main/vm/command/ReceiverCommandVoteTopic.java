package example.powercode.us.redditclonesample.main.vm.command;

import android.support.annotation.NonNull;

import example.powercode.us.redditclonesample.model.entity.VoteType;

/**
 * Created by dev for RedditCloneSample on 03-Jul-18.
 */
@FunctionalInterface
public interface ReceiverCommandVoteTopic {
    void voteTopic(long id, @NonNull VoteType vt);
}
