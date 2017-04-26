package wildbakery.ufu.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import wildbakery.ufu.Fragments.DetailFragmentNews;
import wildbakery.ufu.Presentation.presenters.NewsPresenter;
import wildbakery.ufu.Presentation.views.NewsViews;
import wildbakery.ufu.ui.Adapters.ItemsAdapterNews;
import wildbakery.ufu.Model.Models.NewsItem;
import wildbakery.ufu.R;
import wildbakery.ufu.ui.activity.DetailNewsAcivity;

/**
 * Created by Tatiana on 26/04/2017.
 */

public class MvpNewsFragment extends MvpAppCompatFragment implements NewsViews, SwipeRefreshLayout.OnRefreshListener, ItemsAdapterNews.OnItemClickListener {

    @InjectPresenter
    NewsPresenter presenter;

    private static final String TAG = "MvpNewsFragment";

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ItemsAdapterNews adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment_page, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewFragmentPage);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.testswipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void showNews(List<NewsItem> news) {
        adapter = new ItemsAdapterNews(news, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void showDetail(NewsItem newsItem) {
        Log.d(getClass().getCanonicalName(), "onItemClick: item = " + newsItem);
        Intent intent = new Intent(getContext(), DetailNewsAcivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailFragmentNews.KEY_STRING_ITEM, newsItem);
        NewsItem extracted = (NewsItem) bundle.getSerializable(DetailFragmentNews.KEY_STRING_ITEM);
        Log.d(getClass().getCanonicalName(), "extracted item = " + newsItem);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void showProgressDialog() {
        swipeRefreshLayout.setRefreshing(true);

    }

    @Override
    public void hideProgressDialog() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showToastMessage(String msg) {

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



}
