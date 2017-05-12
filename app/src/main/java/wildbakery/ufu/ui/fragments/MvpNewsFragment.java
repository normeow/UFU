package wildbakery.ufu.ui.fragments;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import wildbakery.ufu.App;
import wildbakery.ufu.Fragments.DetailFragmentNews;
import wildbakery.ufu.Presentation.presenters.NewsPresenter;
import wildbakery.ufu.Presentation.views.NewsView;
import wildbakery.ufu.ui.Adapters.ItemsAdapterNews;
import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.R;
import wildbakery.ufu.ui.activity.DetailNewsAcivity;

/**
 * Created by Tatiana on 26/04/2017.
 */

public class MvpNewsFragment extends MvpAppCompatFragment implements NewsView, SwipeRefreshLayout.OnRefreshListener, ItemsAdapterNews.CallbackListener {

    @InjectPresenter
    NewsPresenter presenter;

    private static final String TAG = "MvpNewsFragment";

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ItemsAdapterNews adapter;
    private CoordinatorLayout rootLayout;
    private Snackbar errorSnackBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: created");
        View view = inflater.inflate(R.layout.base_fragment_page, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewFragmentPage);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        // mLayoutManager.setReverseLayout(true);
        // mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        rootLayout = (CoordinatorLayout) view.findViewById(R.id.fragmentLayout);
        setSnackBar();
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
        Intent intent = new Intent(getContext(), DetailNewsAcivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailFragmentNews.KEY_STRING_ITEM, newsItem);
        intent.putExtras(bundle);
        startActivity(intent);

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
        presenter.onScrollToTheEnd(adapter.getItemCount());
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
}
