package example.powercode.us.redditclonesample.model.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ObjectsCompat;

import java.util.Objects;

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
@SuppressWarnings(value = {"WeakerAccess, unused"})
public class Resource<T> {

    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    public final Throwable error;

    private Resource(
            @NonNull Status status,
            @Nullable T data,
            @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(
            @Nullable Throwable error) {
        return new Resource<>(Status.ERROR, null, error);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Resource<?> resource = (Resource<?>) o;

        return (status == resource.status)
//                && ObjectsCompat.equals(message, resource.message)
                && ObjectsCompat.equals(data, resource.data)
                && ObjectsCompat.equals(error, resource.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, data, error);
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", data=" + data +
                ", error=" + error +
                '}';
    }
}
