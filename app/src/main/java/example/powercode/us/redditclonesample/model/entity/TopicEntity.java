package example.powercode.us.redditclonesample.model.entity;

import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
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

    public TopicEntity(@NonNull TopicEntity other) {
        this(other.id, other.title, other.rating);
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

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return String.format(Locale.UK, "{\n\ttitle: %2$s," +
                "\n\tid: %1$d," +
                "\n\trating: %3$d" +
                "\n}", id, title, rating);
    }
}
