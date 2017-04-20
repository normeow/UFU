package wildbakery.ufu.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import wildbakery.ufu.Models.JobItem;
import wildbakery.ufu.R;

public class ItemsAdapterJob extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<JobItem> items;

    private OnItemClickListener onItemClickListener;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    public ItemsAdapterJob(List<JobItem> items, OnItemClickListener onItemClickListener) {
        this.items = items;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int i) {

      return i == items.size()-1 ? TYPE_HEADER : TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view;


        if(i == TYPE_HEADER){

                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_job_count, viewGroup, false);
                return new OrientationMode0ViewHolder(view);
            }

           else {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_job_row_item, viewGroup, false);

                return new OrientationMode1ViewHolder(view);
            }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        final JobItem item = items.get(i);
            if ( viewHolder instanceof OrientationMode0ViewHolder ) {
                OrientationMode0ViewHolder viewHolder0 = (OrientationMode0ViewHolder) viewHolder;
                viewHolder0.tv_job_count.setText(Integer.toString(items.size()-1) + " вакансии");
            }

        else if(viewHolder instanceof OrientationMode1ViewHolder) {
            OrientationMode1ViewHolder viewHolder1 = (OrientationMode1ViewHolder) viewHolder;
            viewHolder1.tv_name_job.setText(item.getName());
        }


       if( viewHolder instanceof OrientationMode1ViewHolder) {
           OrientationMode1ViewHolder viewHolder1 = (OrientationMode1ViewHolder) viewHolder;

           View.OnClickListener listener = new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   onItemClickListener.onItemClick(item);
               }
           };
           viewHolder1.mView.setOnClickListener(listener);
       }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public static class OrientationMode0ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_job_count;

        public OrientationMode0ViewHolder(View view) {
            super(view);
           tv_job_count = (TextView) view.findViewById(R.id.tvJobCount);

        }
    }

    public  class OrientationMode1ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name_job, tv_short_description,tv_wage_job;
        private View mView;

       public OrientationMode1ViewHolder(View view) {
            super(view);
            mView = view.findViewById(R.id.viewJob);
            tv_wage_job = (TextView) view.findViewById(R.id.tvWageJob);
            tv_name_job = (TextView) view.findViewById(R.id.tvNameJob);

            //tv_short_description = (TextView) view.findViewById(R.id.tvShortDescriptionJob);
        }
    }



    public interface OnItemClickListener {
        void onItemClick(JobItem item);
    }
}
