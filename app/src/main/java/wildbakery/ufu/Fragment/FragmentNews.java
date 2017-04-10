package wildbakery.ufu.Fragment;


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
import wildbakery.ufu.Adapter.ItemsAdapterNews;
import wildbakery.ufu.Interfaces.APIservice;
import wildbakery.ufu.Model.News.Item;
import wildbakery.ufu.Model.News.NewsModel;
import wildbakery.ufu.R;

import static wildbakery.ufu.R.id.recyclerviewNews;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNews extends BaseFragment {
    private static final String TAG = "FragmentNews";

    private RecyclerView recyclerView;
    private List<Item> listItems;
    private ItemsAdapterNews mAdapter;
    private LinearLayoutManager mLayoutManager;
    private DetailFragmentNews activeDetailFragment;

    public FragmentNews() {

    }

    public static FragmentNews newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentNews fragment = new FragmentNews();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = (RecyclerView) view.findViewById(recyclerviewNews);


        Log.v(TAG, "onCreateView()");
        APIservice.Factory.getInstance().getAllNews().enqueue(new Callback<NewsModel>() {

            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                if (response.isSuccess()) {
                    Log.v(TAG, "refresh");
                    listItems = new ArrayList<>();
                    NewsModel result = response.body();
                    listItems = result.getItems();

                    mAdapter = new ItemsAdapterNews(listItems, new ItemsAdapterNews.OnItemClickListener() {
                        @Override
                        public void onItemClick(Item item) {
                            Log.d(getClass().getCanonicalName(), "onItemClick: item = " + item);
                            activeDetailFragment = DetailFragmentNews.newInstance(item);

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.viewNews, activeDetailFragment).commit();
                            recyclerView.setVisibility(View.GONE);
                        }
                    });

                    mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    mLayoutManager.setReverseLayout(true);
                    mLayoutManager.setStackFromEnd(true);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });


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
}