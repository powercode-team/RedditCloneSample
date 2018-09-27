package example.powercode.us.redditclonesample.base.ui.fragments;

import android.content.Context;

import androidx.annotation.CallSuper;

/**
 * Implements onAttach and onDetach which are required to interact with Activity
 */
public abstract class AbsInteractFragment<InteractionListener> extends BaseInjectableFragment {
    protected InteractionListener listener_;

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener_ = (InteractionListener) context;
        }
        catch (ClassCastException e) {
            throw new RuntimeException(context.toString()
                    + " must implement <InteractionListener>");
        }
    }

    @CallSuper
    @Override
    public void onDetach() {
        super.onDetach();
        listener_ = null;
    }
}
