package wildbakery.ufu.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.concurrent.ExecutionException;

import wildbakery.ufu.Adapters.ItemsAdapterJob;
import wildbakery.ufu.FetchDataPackage.DataFetcher;
import wildbakery.ufu.Models.JobItem;
import wildbakery.ufu.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentJob extends FragmentPage implements ItemsAdapterJob.OnItemClickListener {

    private static final String TAG = "FragmentJob";

    private List<JobItem> listItems;
    private ItemsAdapterJob adapter;
    private DetailFragmentJob activeDetailFragment;

    public FragmentJob() {
    }

    public static FragmentJob newInstance() {

        Bundle args = new Bundle();

        FragmentJob fragment = new FragmentJob();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
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
        listItems = dataFetcher.getJobs();
        setRecyclerView();
    }

    private void setRecyclerView(){
        adapter = new ItemsAdapterJob(listItems, this);
        super.setRecyclerView(adapter);
    }

    @Override
    public void onItemClick(JobItem item) {

        Log.d(getClass().getCanonicalName(), "onItemClick: item = " + item);
        activeDetailFragment = DetailFragmentJob.newInstance(item);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, activeDetailFragment).commit();
        recyclerView.setVisibility(View.GONE);
    }
}











