package wildbakery.ufu.Fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import wildbakery.ufu.Adapter.JobAdapter;
import wildbakery.ufu.Interfaces.APIservice;



import wildbakery.ufu.R;
import wildbakery.ufu.Model.Job.JobsModel;
import wildbakery.ufu.controller.RestManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentJob extends Fragment /*implements View.OnClickListener */{
    private RecyclerView mRecyclerView;
    private JobAdapter mJobAdapter;
    private RestManager mManager;



    public FragmentJob() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_job, container, false);
       configViews();

        Call<List<JobsModel>> listCall = mManager.getIstance().getAllJob();
        listCall.enqueue(new Callback<List<JobsModel>>() {
            @Override
            public void onResponse(Call<List<JobsModel>> call, Response<List<JobsModel>> response) {

                if(response.isSuccess()){
                    List<JobsModel> jobsModelList =  response.body();

                    for (int i =0;i < jobsModelList.size();i++ ){
                        JobsModel jobsModel = jobsModelList.get(i);
                        mJobAdapter.addJob(jobsModel);
                    }
                }
                else {
                    int sc = response.code();
                    switch (sc){

                    }
                }
            }

            @Override
            public void onFailure(Call<List<JobsModel>> call, Throwable t) {

            }
        });



        return view;
    }

    private void configViews() {
        mRecyclerView = (RecyclerView) mRecyclerView.findViewById(R.id.recyclerview1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        mJobAdapter = new JobAdapter();
        mRecyclerView.setAdapter(mJobAdapter);
    }
}









