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
    private Snackbar errorSnackBar;

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
        // mLayoutManager.setReverseLayout(true);
        // mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        rootLayout = (CoordinatorLayout) view.findViewById(R.id.newsFragmentLayout);
        setSnackBar();
//        recyclerView.setVisibility(View.VISIBLE);
//        swipeRefreshLayout.setVisibility(View.VISIBLE);
        return view;
    }

    private void setSnackBar(){
        errorSnackBar = Snackbar.make(rootLayout, "Can't load news", BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction("Try again", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "onClick: snackbar");
                        onScrolledToTheEnd();

                    }
                });
    }

    @Override
    public void showNews(List<NewsItem> news) {
        if (errorSnackBar.isShown())
            errorSnackBar.dismiss();
        adapter = new ItemsAdapterNews(news, this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void showDetail(NewsItem newsItem) {
        Log.d(getClass().getCanonicalName(), "onItemClick: item = " + newsItem);
        activeDetailFragment = DetailFragmentNews.newInstance(newsItem);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.newsFragmentLayout, activeDetailFragment).commit();
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
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        presenter.tryGetNews();
    }

    @Override
    public void onItemClick(NewsItem item) {
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
    public void appendRecycleView(List<NewsItem> items) {
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
        presenter.tryGetNews();
    }
}
