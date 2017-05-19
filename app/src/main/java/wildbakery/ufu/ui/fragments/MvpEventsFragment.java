package wildbakery.ufu.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import wildbakery.ufu.Model.ApiModels.EventItem;
import wildbakery.ufu.Presentation.presenters.EventsPresenter;
import wildbakery.ufu.Presentation.views.EventsView;
import wildbakery.ufu.R;
import wildbakery.ufu.ui.Adapters.ItemsAdapterEvent;

/**
 * Created by Tatiana on 26/04/2017.
 */

public class MvpEventsFragment extends MvpBaseFragment implements EventsView, SwipeRefreshLayout.OnRefreshListener, ItemsAdapterEvent.CallbackListener, TryAgatinFragment.TryAgainListener {

    @InjectPresenter
    EventsPresenter presenter;

    private static final String TAG = "MvpEventsFragment";

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ItemsAdapterEvent adapter;
    private CoordinatorLayout rootLayout;
    private Snackbar errorLoadSnackBar;
    private Snackbar refreshErrorSnackBar;
    private View gettingDataMsg;
    private Fragment tryAgainFragment = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: created");
        View view = inflater.inflate(R.layout.event_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.eventsRecycleView);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.eventsSwipeLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        // mLayoutManager.setReverseLayout(true);
        // mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        rootLayout = (CoordinatorLayout) view.findViewById(R.id.eventsFragmentLayout);
        gettingDataMsg = view.findViewById(R.id.events_gettingdata);
        setSnackBar();
        return view;
    }

    private void setSnackBar(){
        errorLoadSnackBar = Snackbar.make(rootLayout, getString(R.string.cant_load), BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.try_again), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "onClick: snackbar");
                        onScrolledToTheEnd();

                    }
                });
    }

    private void setRefreshSnackBar(){
        refreshErrorSnackBar = Snackbar.make(rootLayout, getString(R.string.cant_refresh), BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.try_again), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "onClick: snackbar");
                        presenter.tryGetEvents();

                    }
                });

        View view = refreshErrorSnackBar.getView();
        CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
    }
    @Override
    public void showEvents(List<EventItem> events) {
        if (errorLoadSnackBar.isShown())
            errorLoadSnackBar.dismiss();
        adapter = new ItemsAdapterEvent(events, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showDetail(EventItem eventsItem) {
//        Log.d(getClass().getCanonicalName(), "onItemClick: item = " + eventsItem);
//        Intent intent = new Intent(getContext(), DetailEventAcivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(DetailFragmentEvent.KEY_STRING_ITEM, eventsItem);
//        intent.putExtras(bundle);
//        startActivity(intent);

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
        if (getUserVisibleHint())
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        presenter.tryGetEvents();
    }

    @Override
    public void onScrolledToTheEnd() {
        if (errorLoadSnackBar.isShown())
            errorLoadSnackBar.dismiss();
        presenter.onScrollToTheEnd(adapter.getActualItemCount());
    }

    @Override
    public void appendRecycleView(List<EventItem> items) {
        adapter.add(items);
    }

    @Override
    public void showLoadingBatchError() {
        setSnackBar();
        errorLoadSnackBar.show();
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
        return super.onBackPressed();
    }

    @Override
    public void refresh() {
        presenter.tryGetEvents();
    }

    @Override
    public void showOnRefreshError() {
        if (getUserVisibleHint()) {
            Log.d(TAG, "showOnRefreshError: ");
            setRefreshSnackBar();
            refreshErrorSnackBar.show();
        }
    }

    private void hideSnackBars(){

        if (errorLoadSnackBar.isShown())
            errorLoadSnackBar.dismiss();
        if (refreshErrorSnackBar.isShown())
            refreshErrorSnackBar.dismiss();

    }
    @Override
    public void showNoDataMessage() {
        Log.d(TAG, "showNoDataMessage: ");
        if (tryAgainFragment == null) {
            tryAgainFragment = new TryAgatinFragment();
            getChildFragmentManager().beginTransaction().replace(R.id.eventsFragmentLayout, tryAgainFragment).commit();
        }

    }

    @Override
    public void showGettingDataMessage() {
        Log.d(TAG, "showGettingDataMessage: ");
        gettingDataMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideGettingDataMessage() {
        Log.d(TAG, "hideGettingDataMessage: ");
        gettingDataMsg.setVisibility(View.GONE);
    }

    @Override
    public void hideNoDataMessage() {
        Log.d(TAG, "hideNoDataMessage: ");
        if (tryAgainFragment != null) {
            getChildFragmentManager().beginTransaction().remove(tryAgainFragment).commit();
            tryAgainFragment = null;
        }
    }


    @Override
    public void onTryAgainClicked() {
        refresh();

    }
}
