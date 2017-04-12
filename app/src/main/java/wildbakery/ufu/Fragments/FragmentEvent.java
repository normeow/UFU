package wildbakery.ufu.Fragments;

/**
 * Created by DIKII PEKAR on 13.02.2017.
 */

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
import wildbakery.ufu.Adapters.ItemsAdapterEvent;
import wildbakery.ufu.FetchDataPackage.VuzAPI;
import wildbakery.ufu.Models.EventItem;
import wildbakery.ufu.Models.QueryModel;
import wildbakery.ufu.R;





/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEvent extends BaseFragment {

    private static final String TAG = "FragmentEvent";
    private RecyclerView recyclerView;
    private List<EventItem> listItems;
    private ItemsAdapterEvent mAdapter;
    private LinearLayoutManager mLayoutManager;
    private DetailFragmentEvent activeDetailFragment;


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
        View view =  inflater.inflate(R.layout.fragment_event, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewEvent);

        Log.v(TAG, "onCreateView()");
        VuzAPI.Factory.getInstance().getEvents().enqueue(new Callback<QueryModel<EventItem>>() {

            @Override
            public void onResponse(Call<QueryModel<EventItem>> call, Response<QueryModel<EventItem>> response) {

                if(response.isSuccess()){

                    Log.v(TAG, "refresh");
                    listItems = new ArrayList<>();
                    QueryModel<EventItem> result = response.body();
                    listItems = result.getItems();

                    mAdapter = new ItemsAdapterEvent(listItems,new ItemsAdapterEvent.OnItemClickListener(){
                        @Override
                        public void onItemClick(EventItem item) {
                            Log.d(getClass().getCanonicalName(), "onItemClick: item = " + item);
                            activeDetailFragment =  DetailFragmentEvent.newInstance(item);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.viewEvent, activeDetailFragment).commit();
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
            public void onFailure(Call<QueryModel<EventItem>> call, Throwable t) {

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












