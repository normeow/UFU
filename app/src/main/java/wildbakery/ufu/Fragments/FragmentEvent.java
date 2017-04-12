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

import java.util.List;
import java.util.concurrent.ExecutionException;

import wildbakery.ufu.Adapters.ItemsAdapterEvent;
import wildbakery.ufu.FetchDataPackage.DataFetcher;
import wildbakery.ufu.Models.EventItem;
import wildbakery.ufu.R;





/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEvent extends FragmentPage implements ItemsAdapterEvent.OnItemClickListener{

    private static final String TAG = "FragmentEvent";
    private List<EventItem> listItems;
    private ItemsAdapterEvent adapter;
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
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Log.v(TAG, "onCreateView()");
        try {
            updateRecycleView();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

    @Override
    protected void updateRecycleView() throws ExecutionException, InterruptedException {
        DataFetcher dataFetcher = DataFetcher.getInstance();
        listItems = dataFetcher.getEvents();
        setRecyclerView();
    }


    private void setRecyclerView(){
        adapter = new ItemsAdapterEvent(listItems, this);
        super.setRecyclerView(adapter);
    }

    @Override
    public void onItemClick(EventItem item) {
        Log.d(getClass().getCanonicalName(), "onItemClick: item = " + item);
        activeDetailFragment =  DetailFragmentEvent.newInstance(item);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, activeDetailFragment).commit();
        recyclerView.setVisibility(View.GONE);

    }
}












