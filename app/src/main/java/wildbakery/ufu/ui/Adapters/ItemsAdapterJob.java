package wildbakery.ufu.ui.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import wildbakery.ufu.Model.ApiModels.JobItem;
import wildbakery.ufu.R;


public class ItemsAdapterJob extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ItemsAdapterJob";
    private List<JobItem> items;

    private OnItemClickListener listener;
    public static final int TYPE_NORMAL = 0;
    public static final int PROGRESS_BAR = 1;

    // number of item from the end when should be started loading
    private final int COUNT_OFFSET = 2;

    private boolean isLoading = false;

    public ItemsAdapterJob(List<JobItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int i) {
        final JobItem item = items.get(i);
        if (item == null)
            return PROGRESS_BAR;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view;
        switch (viewType){
            case TYPE_NORMAL:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_job_row_item, viewGroup, false);
                return new OrientationMode1ViewHolder(view);
            case PROGRESS_BAR:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progressbar, viewGroup, false);
                return new ProgressBarViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        final JobItem item = items.get(i);

        //scroll almost to the end. notify listener.
        if (i > items.size() - COUNT_OFFSET){
            if (!isLoading)
                listener.onScrolledToTheEnd();
        }
        

        if(viewHolder instanceof OrientationMode1ViewHolder) {
            OrientationMode1ViewHolder viewHolder1 = (OrientationMode1ViewHolder) viewHolder;
            viewHolder1.tv_name_job.setText(item.getName());
                viewHolder1.tv_short_description.setText(item.getShortDescription());
                if(item.getWage().equals("0")) {
                    viewHolder1.tv_wage_job.setText("Не указана");
                }else {
                    viewHolder1.tv_wage_job.setText(item.getWage());
                }
        }

        else if (viewHolder instanceof ProgressBarViewHolder){
            ((ProgressBarViewHolder) viewHolder).progressBar.setIndeterminate(true);
        }

       if( viewHolder instanceof OrientationMode1ViewHolder) {
           OrientationMode1ViewHolder viewHolder1 = (OrientationMode1ViewHolder) viewHolder;

           View.OnClickListener listener = new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   ItemsAdapterJob.this.listener.onItemClick(item);
               }
           };
           viewHolder1.mView.setOnClickListener(listener);
       }
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    public int getActualItemCount() {
        int res = items.size();
       // if (items.get(items.size() - 1) == null)
        if (isLoading)
            res = res - 1;
        Log.d(TAG, String.format("getActualItemCount: size = %d, isLoading = %s", res, Boolean.toString(isLoading)));
        return res;
    }

    public void add(List<JobItem> items) {
        try {
            if (!items.isEmpty()) {
                this.items.addAll(items);
                notifyDataSetChanged();
            }
        }
        catch (Exception e){
            Log.d(TAG, "add: caught");
            e.printStackTrace();
        }
    }

    public void showProgressBar(){
        Log.d(TAG, "showProgressBar: isLoading = " + isLoading);
        if (!isLoading) {
            items.add(null);
            try {
                notifyItemInserted(items.size() - 1);
                isLoading = true;
            } catch (IllegalStateException e) {
                items.remove(items.size() - 1);
                Log.d(TAG, "showProgressBar: catch exception, remove null last item");
                e.printStackTrace();
            }
        }
    }

    public void hideProgressBar(){
        Log.d("", "hideProgressBar: isLoading = " + isLoading);
        if (isLoading){
            items.remove(items.size() - 1);
            notifyItemRemoved(items.size());
            isLoading = false;
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
            tv_short_description = (TextView) view.findViewById(R.id.tvShortDescriptionJob);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(JobItem item);
        void onScrolledToTheEnd();
    }
}
