package wildbakery.ufu.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wildbakery.ufu.Adapters.ItemsAdapterNews;
import wildbakery.ufu.FetchDataPackage.DataFetcher;
import wildbakery.ufu.FetchDataPackage.VuzAPI;
import wildbakery.ufu.Models.NewsItem;
import wildbakery.ufu.Models.QueryModel;
import wildbakery.ufu.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNews extends BaseFragmentPage implements ItemsAdapterNews.OnItemClickListener{
    private static final String TAG = "FragmentNews";

    private List<NewsItem> listItems;
    private ItemsAdapterNews adapter;
    private DetailFragmentNews activeDetailFragment;
    private Call<QueryModel<NewsItem>> call;

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
        Log.v(TAG, "onCreateView()");
        call = VuzAPI.Factory.getInstance().getNews();
        fetchData();
        return view;
    }


    @Override
    public void onDestroyView() {
        if (call != null)
            call.cancel();
        super.onDestroyView();
    }

    @Override
    public void updateRecycleView() throws ExecutionException, InterruptedException {

        DataFetcher dataFetcher = new DataFetcher();
        listItems = dataFetcher.getNews();
        if (listItems != null)
            setRecyclerView();
    }

    @Override
    public void fetchData(){
        swipeRefreshLayout.setRefreshing(true);
        call.clone().enqueue(new Callback<QueryModel<NewsItem>>() {
            @Override
            public void onResponse(Call<QueryModel<NewsItem>> call, Response<QueryModel<NewsItem>> response) {
                swipeRefreshLayout.setRefreshing(false);
                listItems = response.body().getItems();
                setRecyclerView();
            }

            @Override
            public void onFailure(Call<QueryModel<NewsItem>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "Can't get news", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        if (activeDetailFragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(activeDetailFragment).commit();
            activeDetailFragment = null;
            recyclerView.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            return false;
        } else {
            return super.onBackPressed();
        }
    }

    private void setRecyclerView(){
        adapter = new ItemsAdapterNews(listItems, this);
        super.setRecyclerView(adapter);
    }

    @Override
    public void onItemClick(NewsItem item) {
        Log.d(getClass().getCanonicalName(), "onItemClick: item = " + item);
        activeDetailFragment = DetailFragmentNews.newInstance(item);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, activeDetailFragment).commit();
        recyclerView.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
    }

}