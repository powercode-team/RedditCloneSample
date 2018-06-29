package example.powercode.us.redditclonesample.ui.utils;

import android.os.SystemClock;
import android.util.SparseLongArray;
import android.view.View;

import java.util.concurrent.TimeUnit;

/**
 * @author meugen
 */
public abstract class AbstractOnClickListener implements View.OnClickListener {

    private final SparseLongArray array;
    private final long period;

    protected AbstractOnClickListener() {
        this(TimeUnit.SECONDS.toMillis(1));
    }

    protected AbstractOnClickListener(final long period) {
        this.array = new SparseLongArray();
        this.period = period;
    }

    @Override
    public final void onClick(final View v) {
        final long lastClickTime = array.get(v.getId(), 0L);
        if (SystemClock.elapsedRealtime() - lastClickTime < period) {
            return;
        }
        array.put(v.getId(), lastClickTime);

        _onClick(v);
    }

    protected abstract void _onClick(final View view);
}
