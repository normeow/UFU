package wildbakery.ufu.ui.Adapters;

/**
 * Created by DIKII PEKAR on 19.12.2016.
 */


import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.Callable;

import wildbakery.ufu.Constants;
import wildbakery.ufu.Model.ApiModels.SaleItem;
import wildbakery.ufu.R;
import wildbakery.ufu.Utils.PicassoCache;

public class ItemsAdapterSale extends  RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private static final String TAG = "ItemsAdapterSale";
    private List<SaleItem> items;
    private boolean isLoading;
    private CallbackListener listener;

    public static final int TYPE_NORMAL = 0;
    public static final int PROGRESS_BAR = 1;

    // number of item from the end when should be started loading
    private final int COUNT_OFFSET = 2;

    public interface CallbackListener{
        void onScrolledToTheEnd();
    }

    public ItemsAdapterSale(List<SaleItem> items, CallbackListener listener) {
        this.listener = listener;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        SaleItem item = items.get(position);
        if (item == null)
            return PROGRESS_BAR;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        switch (viewType){
            case TYPE_NORMAL:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_sale_row_item, viewGroup, false);
                return new ViewHolder(view);
            case PROGRESS_BAR:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progressbar, viewGroup, false);
                return new ProgressBarViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        final SaleItem item = items.get(i);

        //scroll almost to the end. notify listener.
        if (i >= items.size() - COUNT_OFFSET){
            if (!isLoading)
                listener.onScrolledToTheEnd();
        }
        if (viewHolder instanceof ProgressBarViewHolder){
            ((ProgressBarViewHolder) viewHolder).progressBar.setIndeterminate(true);
        }
        else {
            ItemsAdapterSale.ViewHolder viewHolder1 = (ItemsAdapterSale.ViewHolder) viewHolder;
            viewHolder1.tv_name_sale.setText(item.getName());
            viewHolder1.tv_description_sale.setText(Html.fromHtml(item.getDescription()));
            viewHolder1.tv_who_sale.setText(item.getWho());
            if (item.getDateStart() != null) {
                viewHolder1.tv_date_start.setText(item.getDateStart().substring(0, 10));
            } else {
                viewHolder1.tv_date_start.setText("");
            }

            if (item.getDateEnd() != null) {
                viewHolder1.tv_date_end.setText(item.getDateEnd().substring(0, 10));
            } else {
                viewHolder1.tv_date_end.setText("");
            }

            PicassoCache.loadImage(item.getImage(), viewHolder1.tv_image_sale);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name_sale,tv_description_sale,tv_who_sale,tv_date_start,tv_date_end;
        private ImageView tv_image_sale;
        public ViewHolder(View view) {
            super(view);
            tv_name_sale = (TextView)view.findViewById(R.id.tvNameSale);
            tv_description_sale = (TextView)view.findViewById(R.id.tvDescriptionSale);
            tv_image_sale = (ImageView)view.findViewById(R.id.tvImageSale);
            tv_who_sale = (TextView) view.findViewById(R.id.tvWhoSale);
            tv_date_start = (TextView) view.findViewById(R.id.tvDateStartSale);
            tv_date_end = (TextView) view.findViewById(R.id.tvDateEndSale);

        }
    }

    public int getActualItemCount(){
        if (isLoading)
            return items.size() - 1;
        return items.size();
    }


    public void add(List<SaleItem> items) {
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

    public void showProgressBar() {
        Log.d(TAG, "showProgressBar: isLoading = " + isLoading);
        if (!isLoading) {
            items.add(null);
            try {
                notifyItemInserted(items.size() - 1);
                isLoading = true;
            } catch (IllegalStateException e) {
                items.remove(items.size() - 1);
                e.printStackTrace();
            }
        }
    }

    public void hideProgressBar() {
        Log.d("", "hideProgressBar: isLoading = " + isLoading);
        if (isLoading){
            isLoading = false;
            items.remove(items.size() - 1);
            notifyItemRemoved(items.size());
        }
    }
}
