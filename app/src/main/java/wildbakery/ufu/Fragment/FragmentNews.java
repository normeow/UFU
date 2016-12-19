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
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wildbakery.ufu.Adapter.ItemsAdapterNews;
import wildbakery.ufu.Interfaces.APIservice;

import wildbakery.ufu.Model.News.Image;
import wildbakery.ufu.Model.News.Item;
import wildbakery.ufu.Model.News.NewsModel;
import wildbakery.ufu.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNews extends Fragment {


    private RecyclerView recyclerView;
    private List<Item> listItems;
    private ItemsAdapterNews mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public FragmentNews() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewNews);



        APIservice.Factory.getInstance().getAllNews().enqueue(new Callback<NewsModel>() {

            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {

                if(response.isSuccess()){
                    listItems = new ArrayList<>();
                    NewsModel result = response.body();
                    listItems = result.getItems();

                    mAdapter = new ItemsAdapterNews(listItems);

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

}