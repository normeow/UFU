package wildbakery.ufu.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.concurrent.ExecutionException;

import wildbakery.ufu.Adapters.ItemsAdapterNews;
import wildbakery.ufu.FetchDataPackage.DataFetcher;
import wildbakery.ufu.Models.NewsItem;
import wildbakery.ufu.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNews extends FragmentPage implements ItemsAdapterNews.OnItemClickListener{
    private static final String TAG = "FragmentNews";

    private List<NewsItem> listItems;
    private ItemsAdapterNews adapter;
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
        try {
            updateList();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void updateList() throws ExecutionException, InterruptedException {
        DataFetcher dataFetcher = DataFetcher.getInstance();
        listItems = dataFetcher.getNews();
        setRecyclerView();

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

        adapter = new ItemsAdapterNews(listItems, this);
        super.setRecyclerView(adapter);
    }

    @Override
    public void onItemClick(NewsItem item) {
        Log.d(getClass().getCanonicalName(), "onItemClick: item = " + item);
        activeDetailFragment = DetailFragmentNews.newInstance(item);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, activeDetailFragment).commit();
        recyclerView.setVisibility(View.GONE);
    }
}