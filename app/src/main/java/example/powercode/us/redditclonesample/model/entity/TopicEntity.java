package example.powercode.us.redditclonesample.model.entity;

import android.support.annotation.NonNull;

import java.util.Objects;

import example.powercode.us.redditclonesample.base.ui.common.HasId;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
public class TopicEntity implements HasId<Long> {
    public final long id;
    @NonNull
    public final String title;
    private int rating;

    public TopicEntity(long id, @NonNull String title, int rating) {
        this.id = id;
        this.title = title;
        this.rating = rating;
    }

    public TopicEntity(long id, @NonNull String title) {
        this(id, title, 0);
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @NonNull
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TopicEntity)) return false;
        TopicEntity that = (TopicEntity) o;
        return id == that.id
                && rating == that.rating
                && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, rating);
    }
}
