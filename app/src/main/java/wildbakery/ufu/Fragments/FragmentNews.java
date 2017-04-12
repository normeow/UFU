package wildbakery.ufu.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wildbakery.ufu.Adapters.ItemsAdapterNews;
import wildbakery.ufu.FetchDataPackage.VuzAPI;
import wildbakery.ufu.Models.NewsItem;
import wildbakery.ufu.Models.QueryModel;
import wildbakery.ufu.R;

import static wildbakery.ufu.R.id.recyclerviewNews;
import static wildbakery.ufu.R.id.submenuarrow;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNews extends FragmentPage implements ItemsAdapterNews.OnItemClickListener{
    private static final String TAG = "FragmentNews";

    private List<NewsItem> listItems;
    private ItemsAdapterNews mAdapter;
    private DetailFragmentNews activeDetailFragment;

    public FragmentNews() {

    }

    public static FragmentNews newInstance() {
        FragmentNews fragment = new FragmentNews();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = super.onCreateView(inflater, container, savedInstanceState);


        return view;
    }

    @Override
    public boolean onBackPressed() {
        if (activeDetailFragment != null) {

            getActivity().getSupportFragmentManager().beginTransaction().remove(activeDetailFragment).commit();
            activeDetailFragment = null;
            recyclerView.setVisibility(View.VISIBLE);
            return false;
        } else {
            return super.onBackPressed();
        }
    }

    private void setRecyclerView(){

        mAdapter = new ItemsAdapterNews(listItems, new ItemsAdapterNews.OnItemClickListener() {
            @Override
            public void onItemClick(NewsItem item) {
            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(NewsItem item) {
        Log.d(getClass().getCanonicalName(), "onItemClick: item = " + item);
        activeDetailFragment = DetailFragmentNews.newInstance(item);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.viewNews, activeDetailFragment).commit();
        recyclerView.setVisibility(View.GONE);
    }
}