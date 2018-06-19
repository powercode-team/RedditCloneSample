package example.powercode.us.redditclonesample.model;

import android.support.annotation.NonNull;

import java.util.Objects;

/**
 * Created by dev for RedditCloneSample on 19-Jun-18.
 */
public class TopicEntity {
    @NonNull
    public final String title;
    private int rating;

    public TopicEntity(@NonNull String title, int rating) {
        this.title = title;
        this.rating = rating;
    }

    public TopicEntity(@NonNull String title) {
        this(title, 0);
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TopicEntity)) return false;
        TopicEntity that = (TopicEntity) o;
        return rating == that.rating &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, rating);
    }
}
