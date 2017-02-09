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


    private RecyclerView recyclerView;
    private List<Item> listItems;
    private ItemsAdapterNews mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DetailFragmentNews activeDetailFragment;

    public FragmentNews() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = (RecyclerView) view.findViewById(recyclerviewNews);


        APIservice.Factory.getInstance().getAllNews().enqueue(new Callback<NewsModel>() {

            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                if (response.isSuccess()) {
                    listItems = new ArrayList<>();
                    NewsModel result = response.body();
                    listItems = result.getItems();

                    mAdapter = new ItemsAdapterNews(listItems, new ItemsAdapterNews.OnItemClickListener() {
                        @Override
                        public void onItemClick(Item item) {
                            Log.d(getClass().getCanonicalName(), "onItemClick: item = " + item);
                            activeDetailFragment = DetailFragmentNews.newInstance(item);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.rootView, activeDetailFragment).commit();
                            recyclerView.setVisibility(View.GONE);
                        }
                    });

                    mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
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