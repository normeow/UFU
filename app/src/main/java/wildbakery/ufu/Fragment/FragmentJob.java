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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wildbakery.ufu.Adapter.ItemsAdapterJob;
import wildbakery.ufu.Interfaces.APIservice;
import wildbakery.ufu.Model.Job.Item;
import wildbakery.ufu.Model.Job.JobsModel;
import wildbakery.ufu.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentJob extends BaseFragment {


    private static final String TAG = "FragmentJob";

    private RecyclerView recyclerView;
    private List<Item> listItems;
    private ItemsAdapterJob mAdapter;
    private LinearLayoutManager mLayoutManager;
    private DetailFragmentJob activeDetailFragment;
    private TextView mText;



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
        final View view =  inflater.inflate(R.layout.fragment_job, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewJob);
        Log.v(TAG, "onCreateView()");

        APIservice.Factory.getInstance().getAllJob().enqueue(new Callback<JobsModel>() {

            @Override
            public void onResponse(Call<JobsModel> call, Response<JobsModel> response) {

                if(response.isSuccess()){
                    Log.v(TAG, "refresh");
                    listItems = new ArrayList<>();
                    JobsModel result = response.body();
                    listItems = result.getItems();
                    listItems.add(listItems.size(),new Item());



                    mAdapter = new ItemsAdapterJob(listItems,new ItemsAdapterJob.OnItemClickListener(){
                        @Override
                        public void onItemClick(Item item) {
                            Log.d(getClass().getCanonicalName(), "onItemClick: item = " + item);
                            activeDetailFragment = DetailFragmentJob.newInstance(item);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.viewJob, activeDetailFragment).commit();
                            recyclerView.setVisibility(View.GONE);
                        }


                    });

                    mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    mLayoutManager.setReverseLayout(true);
                    mLayoutManager.setStackFromEnd(true);

                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setAdapter(mAdapter);


                }

            }

            @Override
            public void onFailure(Call<JobsModel> call, Throwable t) {

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











