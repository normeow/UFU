package wildbakery.ufu.Fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;


import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
public class FragmentJob extends Fragment {

    private RecyclerView recyclerView;
    private List<Item> listItems;
    private ItemsAdapterJob mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public FragmentJob() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_job, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewJob);



        APIservice.Factory.getInstance().getAllJob().enqueue(new Callback<JobsModel>() {

            @Override
            public void onResponse(Call<JobsModel> call, Response<JobsModel> response) {

                if(response.isSuccess()){
                    listItems = new ArrayList<>();
                    JobsModel result = response.body();
                    listItems = result.getItems();

                    mAdapter = new ItemsAdapterJob(listItems);

                    mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }

            }

            @Override
            public void onFailure(Call<JobsModel> call, Throwable t) {

            }
        });


        return view;
    }



}











