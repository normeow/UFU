package wildbakery.ufu.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import wildbakery.ufu.Model.ApiModels.JobItem;
import wildbakery.ufu.Presentation.presenters.JobsPresenter;
import wildbakery.ufu.Presentation.views.JobsView;
import wildbakery.ufu.R;
import wildbakery.ufu.ui.Adapters.ItemsAdapterJob;
import wildbakery.ufu.ui.fragments.DetailFragments.DetailFragmentJob;

/**
 * Created by Tatiana on 26/04/2017.
 */

public class MvpJobsFragment extends MvpBaseFragment implements JobsView, SwipeRefreshLayout.OnRefreshListener, ItemsAdapterJob.OnItemClickListener {

    @InjectPresenter
    JobsPresenter presenter;

    private static final String TAG = "MvpJobsFragment";

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ItemsAdapterJob adapter;
    private CoordinatorLayout rootLayout;
    private Snackbar errorSnackBar;
    private DetailFragmentJob activeDetailFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: created");
        View view = inflater.inflate(R.layout.jobs_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.jobsRecycleView);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.jobsSwipeLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        // mLayoutManager.setReverseLayout(true);
        // mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        rootLayout = (CoordinatorLayout) view.findViewById(R.id.jobsFragmentLayout);
        setSnackBar();
        return view;
    }

    private void setSnackBar(){
        errorSnackBar = Snackbar.make(rootLayout, "Can't load jobs", BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction("Try again", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "onClick: snackbar");
                        onScrolledToTheEnd();

                    }
                });
    }

    @Override
    public void showJobs(List<JobItem> jobs) {
        if (errorSnackBar.isShown())
            errorSnackBar.dismiss();
        adapter = new ItemsAdapterJob(jobs, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showDetail(JobItem jobsItem) {
        Log.d(getClass().getCanonicalName(), "onItemClick: item = " + jobsItem);
        activeDetailFragment = DetailFragmentJob.newInstance(jobsItem);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.jobsFragmentLayout, activeDetailFragment).commit();
        recyclerView.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);

    }

    @Override
    public void showProgressBar() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showToastMessage(String msg) {
        if (this.isVisible())
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        presenter.tryGetJobs();
    }

    @Override
    public void onItemClick(JobItem item) {
        Log.d(getClass().getCanonicalName(), "onItemClick: item = " + item);
        presenter.showDetailFragment(item);
    }

    @Override
    public void onScrolledToTheEnd() {
        if (errorSnackBar.isShown())
            errorSnackBar.dismiss();
        presenter.onScrollToTheEnd(adapter.getActualItemCount());
    }

    @Override
    public void appendRecycleView(List<JobItem> items) {
        adapter.add(items);
    }

    @Override
    public void showLoadingBatchError() {
        setSnackBar();
        errorSnackBar.show();
    }

    @Override
    public void showBottomProgressBar() {
        adapter.showProgressBar();
        recyclerView.scrollToPosition(adapter.getItemCount());
    }

    @Override
    public void hideBottomProgressBar() {
        adapter.hideProgressBar();
    }
    @Override
    public boolean onBackPressed() {
        if (activeDetailFragment != null) {
            presenter.hideDetailFragment();
            return false;
        } else {
            return super.onBackPressed();
        }
    }

    @Override
    public void hideDetail() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(activeDetailFragment).commit();
        activeDetailFragment = null;
        recyclerView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void refresh() {
        presenter.tryGetJobs();
    }
}
