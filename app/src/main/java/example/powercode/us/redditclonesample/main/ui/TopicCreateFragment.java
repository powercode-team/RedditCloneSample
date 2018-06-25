package example.powercode.us.redditclonesample.main.ui;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.app.di.qualifiers.ActivityContext;
import example.powercode.us.redditclonesample.base.error.ErrorDataTyped;
import example.powercode.us.redditclonesample.base.ui.common.DefaultTagGenerator;
import example.powercode.us.redditclonesample.base.ui.common.HasFragmentTag;
import example.powercode.us.redditclonesample.base.ui.fragments.BaseViewModelFragment;
import example.powercode.us.redditclonesample.databinding.FragmentTopicCreateBinding;
import example.powercode.us.redditclonesample.databinding.FragmentTopicListBinding;
import example.powercode.us.redditclonesample.main.vm.TopicCreateViewModel;
import example.powercode.us.redditclonesample.main.vm.TopicsViewModel;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.entity.VoteType;
import example.powercode.us.redditclonesample.model.error.ErrorsTopics;
import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicCreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicCreateFragment extends BaseViewModelFragment<TopicCreateViewModel> implements HasFragmentTag {
    public static final String FRAGMENT_TAG = DefaultTagGenerator.generate(TopicCreateFragment.class);

    @NonNull
    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Inject
    OnInteractionListener listener;

    private FragmentTopicCreateBinding binding;

    private CompositeDisposable uiInputDisposable;

    public TopicCreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TopicListFragment.
     */
    public static TopicCreateFragment newInstance() {
        TopicCreateFragment fragment = new TopicCreateFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_topic_create, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uiInputDisposable = new CompositeDisposable();

    }

    private void onTopicsFetchedObserver(@NonNull Resource<List<TopicEntity>, ErrorDataTyped<ErrorsTopics>> resTopics) {
        switch (resTopics.status) {
            case SUCCESS: {
                listener.onTopicCreated(0);
                break;
            }

            case ERROR: {
                //TODO: display error

                break;
            }

            case LOADING:
                // TODO: display loading
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        uiInputDisposable.dispose();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @NonNull
    @Override
    protected Class<TopicCreateViewModel> getViewModelClass() {
        return TopicCreateViewModel.class;
    }

    @Override
    protected void onDetachFromViewModel() {
        viewModel.getCreateTopicLiveData().removeObservers(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnInteractionListener {
        void onTopicCreated(long newTopicId);
    }
}
