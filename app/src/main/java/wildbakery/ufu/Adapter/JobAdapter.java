package wildbakery.ufu.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import wildbakery.ufu.Model.Job.JobsModel;
import wildbakery.ufu.R;

/**
 * Created by DIKII PEKAR on 15.12.2016.
 */

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.Holder> {

    private List<JobsModel> mJobsModel= new ArrayList<>();
    public JobAdapter(){
        mJobsModel = new ArrayList<>();
    }




    @Override
    public JobAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_job_row_item,null,false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(JobAdapter.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }
    public void addJob(JobsModel jobsModel) {
        mJobsModel.add(jobsModel);
        notifyDataSetChanged();
    }


    public class Holder extends RecyclerView.ViewHolder{

        public Holder(View itemView) {
            super(itemView);
        }
    }

}
