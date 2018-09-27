package example.powercode.us.redditclonesample.main.ui;


import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableInt;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import example.powercode.us.redditclonesample.R;
import example.powercode.us.redditclonesample.app.di.qualifiers.ActivityContext;
import example.powercode.us.redditclonesample.base.error.ErrorDataTyped;
import example.powercode.us.redditclonesample.base.ui.common.DefaultTagGenerator;
import example.powercode.us.redditclonesample.base.ui.common.HasFragmentTag;
import example.powercode.us.redditclonesample.base.ui.fragments.BaseViewModelFragment;
import example.powercode.us.redditclonesample.base.vm.ViewModelAttachHelper;
import example.powercode.us.redditclonesample.databinding.FragmentTopicListBinding;
import example.powercode.us.redditclonesample.main.vm.TopicsViewModel;
import example.powercode.us.redditclonesample.model.common.Resource;
import example.powercode.us.redditclonesample.model.entity.TopicEntity;
import example.powercode.us.redditclonesample.model.entity.VoteType;
import example.powercode.us.redditclonesample.model.error.ErrorsTopics;
import example.powercode.us.redditclonesample.utils.ViewUtils;
import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopicListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopicListFragment extends BaseViewModelFragment<TopicsViewModel> implements HasFragmentTag,
        TopicsAdapter.InteractionListener {
    public static final String FRAGMENT_TAG = DefaultTagGenerator.generate(TopicListFragment.class);

    @NonNull
    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Inject
    @ActivityContext
    Context c;
    @Inject
    OnInteractionListener listener;

    @Inject
    TopicsAdapter adapter;

    private FragmentTopicListBinding binding;

    private CompositeDisposable uiInputDisposable;

    @NonNull
    private final ObservableInt topicsCount = new ObservableInt(0);

    public TopicListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TopicListFragment.
     */
    public static TopicListFragment newInstance() {
        TopicListFragment fragment = new TopicListFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_topic_list, container, false);
        binding.setItemsCount(topicsCount);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupTopicsRecyclerView(adapter,
                new DividerItemDecoration(c, DividerItemDecoration.VERTICAL),
                new DefaultItemAnimator(),
                new TopicsTouchHelper(0, ItemTouchHelper.LEFT, swipeInteractionListener)
        );

        binding.fabTopicCreate.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setRecyclerViewPaddingToFAB(getResources().getDimensionPixelSize(R.dimen.margin_rv_last_item_to_fab));
                ViewUtils.removeOnGlobalLayoutListener(binding.fabTopicCreate, this);
            }
        });

        uiInputDisposable = new CompositeDisposable();
        assignListeners(uiInputDisposable);
    }

    private void assignListeners(@NonNull CompositeDisposable uiInputDisposable) {
        uiInputDisposable.add(
                RxView.clicks(binding.fabTopicCreate)
//                        .takeUntil(RxView.detaches(binding.fabTopicCreate))
                        .subscribe(o -> listener.onCreateNewTopic())
        );
    }

    private void setupTopicsRecyclerView(@Nullable RecyclerView.Adapter adapter,
                                         @NonNull DividerItemDecoration dividerDecoration,
                                         @NonNull RecyclerView.ItemAnimator itemAnimator,
                                         @NonNull ItemTouchHelper.Callback callback) {
        binding.rvTopics.setItemAnimator(itemAnimator);
        binding.rvTopics.addItemDecoration(dividerDecoration);

        binding.rvTopics.setAdapter(adapter);
        new ItemTouchHelper(callback).attachToRecyclerView(binding.rvTopics);
    }

    private void setRecyclerViewPaddingToFAB(int extraPaddingBottom) {
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) binding.fabTopicCreate.getLayoutParams();

        int bottomPadding = binding.fabTopicCreate.getHeight() + mlp.bottomMargin + extraPaddingBottom;
        ViewUtils.setPaddingRelative(binding.rvTopics, ViewUtils.getPaddingStart(binding.rvTopics),
                binding.rvTopics.getPaddingTop(),
                ViewUtils.getPaddingEnd(binding.rvTopics), bottomPadding);
    }

    private void onTopicsFetchedObserver(@NonNull Resource<List<TopicEntity>, ErrorDataTyped<ErrorsTopics>> resTopics) {
        switch (resTopics.status) {
            case SUCCESS: {
                adapter.submitItems(resTopics.data);
                final int safeItemsCount = resTopics.data != null ? resTopics.data.size() : 0;
                topicsCount.set(safeItemsCount);

                break;
            }

            case ERROR: {
                adapter.submitItems(null);
                topicsCount.set(-1);

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
    protected Class<TopicsViewModel> getViewModelClass() {
        return TopicsViewModel.class;
    }

    @Override
    protected void onAttachViewModel() {
        viewModel.getTopicsLiveData().observe(this, this::onTopicsFetchedObserver);
        ViewModelAttachHelper.attachObserverIfLoading(viewModel.getDeleteTopicLiveData(), this, deleteTopicObserver);
        ViewModelAttachHelper.attachObserverIfLoading(viewModel.getRestoreTopicLiveData(), this, restoreDeletedTopicObserver);
        ViewModelAttachHelper.attachObserverIfLoading(viewModel.getApplyVoteLiveData(), this, voteTopicObserver);
    }

    @Override
    protected void onDetachViewModel() {
        viewModel.getTopicsLiveData().removeObservers(this);
        viewModel.getApplyVoteLiveData().removeObservers(this);
        viewModel.getDeleteTopicLiveData().removeObservers(this);
        viewModel.getRestoreTopicLiveData().removeObservers(this);
    }

    @Override
    public void onVoteClick(@NonNull View v, int adapterPos, @NonNull VoteType vt) {
        final TopicEntity topic = adapter.getItem(adapterPos);
        viewModel.getApplyVoteLiveData().observe(this, voteTopicObserver);
        viewModel.topicVote(topic.id, vt);
    }

    @NonNull
    private final Observer<Resource<Long, ErrorDataTyped<ErrorsTopics>>> voteTopicObserver = new Observer<Resource<Long, ErrorDataTyped<ErrorsTopics>>>() {
        @Override
        public void onChanged(@Nullable Resource<Long, ErrorDataTyped<ErrorsTopics>> votedTopicIdResource) {
            Objects.requireNonNull(votedTopicIdResource);
            switch (votedTopicIdResource.status) {
                case SUCCESS: {
                    viewModel.getApplyVoteLiveData().removeObserver(this);

                    Objects.requireNonNull(votedTopicIdResource.data, "Status.SUCCESS implies data to be set");

                    final long updatedItemId = votedTopicIdResource.data;
                    int updatedItemPosition = adapter.findItemPosition(topicEntity -> updatedItemId == topicEntity.id);
                    if (updatedItemPosition != RecyclerView.NO_POSITION) {
                        adapter.notifyItemChanged(updatedItemPosition);
                    }

                    break;
                }
                case ERROR: {
                    viewModel.getApplyVoteLiveData().removeObserver(this);
                    break;
                }
                case LOADING:
                    break;
            }
        }
    };

    @NonNull
    private final TopicsTouchHelper.InteractionListener<RecyclerView, TopicsAdapter.ItemViewHolder> swipeInteractionListener = new TopicsTouchHelper.InteractionListener<RecyclerView, TopicsAdapter.ItemViewHolder>() {
        @Override
        public void onSwiped(TopicsAdapter.ItemViewHolder viewHolder, int direction) {
            TopicEntity topicToDelete = adapter.getItem(viewHolder.getAdapterPosition());
            viewModel.getDeleteTopicLiveData().observe(TopicListFragment.this, deleteTopicObserver);
            viewModel.topicDelete(topicToDelete.id);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, TopicsAdapter.ItemViewHolder viewHolder, TopicsAdapter.ItemViewHolder target) {
            return false;
        }
    };

    @NonNull
    private final Observer<Resource<Long, ErrorDataTyped<ErrorsTopics>>> deleteTopicObserver = new Observer<Resource<Long, ErrorDataTyped<ErrorsTopics>>>() {
        @Override
        public void onChanged(@Nullable Resource<Long, ErrorDataTyped<ErrorsTopics>> deletedTopicIdResource) {
            Objects.requireNonNull(deletedTopicIdResource);
            switch (deletedTopicIdResource.status) {
                case SUCCESS: {
                    viewModel.getDeleteTopicLiveData().removeObserver(this);
                    // Do nothing since notify changes will trigger update

                    Snackbar
                            .make(binding.getRoot(), R.string.msg_item_deleted, Snackbar.LENGTH_LONG)
                            .setAction(R.string.action_undo, v -> {
                                        viewModel.getRestoreTopicLiveData().observe(TopicListFragment.this, restoreDeletedTopicObserver);
                                        viewModel.undoTopicDelete();
                                    }
                            )
                            /*.addCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {

                                }
                            })*/
                            .show();

                    break;
                }
                case ERROR: {
                    viewModel.getDeleteTopicLiveData().removeObserver(this);
                    Objects.requireNonNull(deletedTopicIdResource.data, "Status.SUCCESS implies data to be set");
                    final long failedDeleteItemId = deletedTopicIdResource.data;
                    int failedDeleteItemPosition = adapter.findItemPosition(topicEntity -> failedDeleteItemId == topicEntity.id);
                    adapter.notifyItemChanged(failedDeleteItemPosition);

                    break;
                }
                case LOADING:
                    break;
            }
        }
    };

    @NonNull
    private final Observer<Resource<Long, ErrorDataTyped<ErrorsTopics>>> restoreDeletedTopicObserver = new Observer<Resource<Long, ErrorDataTyped<ErrorsTopics>>>() {
        @Override
        public void onChanged(@Nullable Resource<Long, ErrorDataTyped<ErrorsTopics>> restoredTopicIdResource) {
            Objects.requireNonNull(restoredTopicIdResource);
            switch (restoredTopicIdResource.status) {
                case SUCCESS: {
                    viewModel.getRestoreTopicLiveData().removeObserver(this);
                    Snackbar
                            .make(binding.getRoot(), R.string.msg_item_restored, Snackbar.LENGTH_SHORT)
                            .show();
                    break;
                }

                case ERROR: {
                    viewModel.getRestoreTopicLiveData().removeObserver(this);
                    break;
                }
                case LOADING:
                    break;
            }
        }
    };

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
        void onCreateNewTopic();
    }
}
