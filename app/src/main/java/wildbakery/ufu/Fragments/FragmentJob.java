package wildbakery.ufu.Fragments;


import android.app.ProgressDialog;
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
import wildbakery.ufu.Adapters.ItemsAdapterJob;
import wildbakery.ufu.FetchDataPackage.DataFetcher;
import wildbakery.ufu.FetchDataPackage.VuzAPI;
import wildbakery.ufu.Models.JobItem;
import wildbakery.ufu.Models.QueryModel;
import wildbakery.ufu.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentJob extends BaseFragmentPage implements ItemsAdapterJob.OnItemClickListener{

    private static final String TAG = "FragmentJob";

    private List<JobItem> listItems;
    private ItemsAdapterJob adapter;
    private DetailFragmentJob activeDetailFragment;
    Call<QueryModel<JobItem>> call;

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
        call = VuzAPI.Factory.getInstance().getJobs();
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
    public void onDestroyView() {
        if (call != null)
            call.cancel();
        super.onDestroyView();
    }

    @Override
    public void updateRecycleView() throws ExecutionException, InterruptedException {
        DataFetcher dataFetcher = new DataFetcher();
        listItems = dataFetcher.getJobs();
        setRecyclerView();
    }

    @Override
    public void fetchData(){
        swipeRefreshLayout.setRefreshing(true);
        call.clone().enqueue(new Callback<QueryModel<JobItem>>() {
            @Override
            public void onResponse(Call<QueryModel<JobItem>> call, Response<QueryModel<JobItem>> response) {
                listItems = response.body().getItems();
                swipeRefreshLayout.setRefreshing(false);
                setRecyclerView();
            }

            @Override
            public void onFailure(Call<QueryModel<JobItem>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "Can't get jobs", Toast.LENGTH_SHORT).show();
            }
        });
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
        swipeRefreshLayout.setVisibility(View.GONE);
    }

}











