package wildbakery.ufu.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import wildbakery.ufu.Presentation.presenters.NewsPresenter;
import wildbakery.ufu.Presentation.views.NewsView;
import wildbakery.ufu.ui.Adapters.ItemsAdapterNews;
import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.R;
import wildbakery.ufu.ui.fragments.DetailFragments.DetailFragmentNews;

/**
 * Created by Tatiana on 26/04/2017.
 */

public class MvpNewsFragment extends MvpBaseFragment implements NewsView, SwipeRefreshLayout.OnRefreshListener, ItemsAdapterNews.CallbackListener {

    @InjectPresenter
    NewsPresenter presenter;

    private static final String TAG = "MvpNewsFragment";

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ItemsAdapterNews adapter;
    private CoordinatorLayout rootLayout;
    private Snackbar errorLoadSnackBar;
    private Snackbar refreshErrorSnackBar;

    private DetailFragmentNews activeDetailFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: created");
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.newsRecycleView);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.newsSwipeLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        rootLayout = (CoordinatorLayout) view.findViewById(R.id.newsFragmentLayout);
        setBottomSnackBar();
        setRefreshSnackBar();
        return view;
    }

    private void setBottomSnackBar(){
        errorLoadSnackBar = Snackbar.make(rootLayout, R.string.cant_load, BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again, new View.OnClickListener() {
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
                        presenter.tryGetNews();

                    }
                });

        View view = refreshErrorSnackBar.getView();
        CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
    }

    @Override
    public void showNews(List<NewsItem> news) {
        hideSnackBars();
        adapter = new ItemsAdapterNews(news, this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void showDetail(NewsItem newsItem) {
        Log.d(getClass().getCanonicalName(), "showDetail: item = " + newsItem);
        activeDetailFragment = DetailFragmentNews.newInstance(newsItem);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.newsFragmentLayout, activeDetailFragment).commit();
        recyclerView.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        showArrowBack();
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
        Log.d(TAG, "showToastMessage: ");
        if (this.getUserVisibleHint())
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        presenter.tryGetNews();
    }

    @Override
    public void onItemClick(NewsItem item) {
        presenter.showDetailFragment(item);
    }

    @Override
    public void onScrolledToTheEnd() {
        if (errorLoadSnackBar.isShown())
            errorLoadSnackBar.dismiss();
        presenter.onScrollToTheEnd(adapter.getActualItemCount());
    }

    @Override
    public void appendRecycleView(List<NewsItem> items) {
        adapter.add(items);
    }

    @Override
    public void showLoadingBatchError() {
        setBottomSnackBar();
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
        hideArrowBack();
    }

    @Override
    public void refresh() {
        presenter.tryGetNews();
    }

    @Override
    public void showOnRefreshError() {
        if(getUserVisibleHint()) {
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
    public void showArrowBack() {
        if (activeDetailFragment != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

}
