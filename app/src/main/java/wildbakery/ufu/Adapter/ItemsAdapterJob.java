package wildbakery.ufu.Adapter;

import wildbakery.ufu.Model.Job.Item;
import wildbakery.ufu.Model.Job.JobsModel;
import wildbakery.ufu.R;


        import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

import java.util.List;

public class ItemsAdapterJob extends RecyclerView.Adapter<ItemsAdapterJob.ViewHolder> {
    private List<Item> items;
    private List<JobsModel> mJobsmodel;

    public ItemsAdapterJob(List<Item> items) {
        this.items = items;
    }

    @Override
    public ItemsAdapterJob.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_job_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemsAdapterJob.ViewHolder viewHolder, int i) {
        final ItemsAdapterJob.ViewHolder holder1 = viewHolder;
        viewHolder.tv_name.setText(items.get(i).getName());

        viewHolder.tv_description.setText(Html.fromHtml(items.get(i).getDescription()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public JobsModel getSelectedJob(int position) {
        return mJobsmodel.get(position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_description;

        public ViewHolder(View view) {
            super(view);

            tv_name = (TextView) view.findViewById(R.id.tvNameJob);
            tv_description = (TextView) view.findViewById(R.id.tvDescriptionJob);


        }


    }
}
