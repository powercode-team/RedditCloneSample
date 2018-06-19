package example.powercode.us.redditclonesample.model.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ObjectsCompat;

import java.util.Objects;

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 * @param <E>
 */
@SuppressWarnings(value = {"WeakerAccess, unused"})
public class Resource<T, E> {

    @NonNull
    public final Status status;

//    @Nullable
//    public final String message;

    @Nullable
    public final T data;

    @Nullable
    public final E error;

    public Resource(@NonNull Status status, @Nullable T data, /*@Nullable String message,*/ @Nullable E error) {
        this.status = status;
        this.data = data;
        this.error = error;
//        this.message = message;
    }

    public static <T, E> Resource<T, E> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, /*null,*/ null);
    }

    public static <T, E> Resource<T, E> error(/*String msg, */@Nullable E error, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, /*msg,*/ error);
    }

//    public static <T, E> Resource<T, E> error(String msg, @Nullable E error, @Nullable T data) {
//        return new Resource<>(ERROR, data, msg, error);
//    }

    public static <T, E> Resource<T, E> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, /*null,*/ null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Resource<?, ?> resource = (Resource<?, ?>) o;

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
