package wildbakery.ufu.Fragments;

/**
 * Created by DIKII PEKAR on 13.02.2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wildbakery.ufu.Adapters.ItemsAdapterEvent;
import wildbakery.ufu.FetchDataPackage.DataFetcher;
import wildbakery.ufu.FetchDataPackage.VuzAPI;
import wildbakery.ufu.Models.EventItem;
import wildbakery.ufu.Models.QueryModel;





/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEvent extends BaseFragmentPage {

    private static final String TAG = "FragmentEvent";
    private List<EventItem> listItems;
    private ItemsAdapterEvent adapter;
    private DetailFragmentEvent activeDetailFragment;
    private Call<QueryModel<EventItem>> call;


    public FragmentEvent() {
    }

    public static FragmentEvent newInstance() {
        Bundle args = new Bundle();
        FragmentEvent fragment = new FragmentEvent();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Log.v(TAG, "onCreateView()");
        call = VuzAPI.Factory.getInstance().getEvents();
        fetchData();
        return view;
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

    @Override
    public void updateRecycleView() throws ExecutionException, InterruptedException {
        DataFetcher dataFetcher = new DataFetcher();
        listItems = dataFetcher.getEvents();
        setRecyclerView();
    }


    private void setRecyclerView(){
        adapter = new ItemsAdapterEvent(listItems);
        super.setRecyclerView(adapter);
    }



    @Override
    protected void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
        call.clone().enqueue(new Callback<QueryModel<EventItem>>() {
            @Override
            public void onResponse(Call<QueryModel<EventItem>> call, Response<QueryModel<EventItem>> response) {
                listItems = response.body().getItems();
                swipeRefreshLayout.setRefreshing(false);
                setRecyclerView();
            }

            @Override
            public void onFailure(Call<QueryModel<EventItem>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "Can't get events", Toast.LENGTH_SHORT).show();
            }
        });
    }
}












