package wildbakery.ufu.ui.Adapters;

/**
 * Created by DIKII PEKAR on 13.02.2017.
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

import wildbakery.ufu.Constants;
import wildbakery.ufu.Model.ApiModels.EventItem;
import wildbakery.ufu.Model.ApiModels.EventItem;
import wildbakery.ufu.R;
import wildbakery.ufu.Utils.PicassoCache;


public class ItemsAdapterEvent extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ItemsAdapterEvent";
    private List<EventItem> items;
    private static final int ORIENTATION_MODE_1 = 1;
    private static final int ORIENTATION_MODE_2 = 2;
    private static final int PROGRESS_BAR = 3;

    private boolean isLoading;
    private CallbackListener listener;

    // number of item from the end when should be started loading
    private final int COUNT_OFFSET = 2;

    public interface CallbackListener{
        void onScrolledToTheEnd();
    }

    public ItemsAdapterEvent(List<EventItem> items, CallbackListener listener) {
        this.listener = listener;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view;
        switch (viewType){
            case ORIENTATION_MODE_1:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_event_row_item_orientation_1,viewGroup,false);
                return new OrientationMode1ViewHolder(view);
            }
            case ORIENTATION_MODE_2:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_event_row_item_orientation_2,viewGroup,false);
                return new OrientationMode2ViewHolder(view);
            }
            case PROGRESS_BAR:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progressbar, viewGroup, false);
                return new ProgressBarViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        final EventItem item = items.get(position);
        if (item == null)
            return PROGRESS_BAR;

        if(item.getOrientationMode() == 1){
            return ORIENTATION_MODE_1;
        }
        else {return ORIENTATION_MODE_2;}
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        final EventItem item = items.get(i);

        //scroll almost to the end. notify listener.
        if (i > items.size() - COUNT_OFFSET){
            if (!isLoading)
                listener.onScrolledToTheEnd();
        }


        if (viewHolder instanceof OrientationMode1ViewHolder) {
            OrientationMode1ViewHolder viewHolder1 = (OrientationMode1ViewHolder)viewHolder;
            viewHolder1.tvWhenEvent_1.setText(item.getEventWhen().substring(8,10));
            viewHolder1.tvNameEvent_1.setText(item.getName());
            PicassoCache.loadImage(item.getImage(),viewHolder1.tvImageEvent_1);
        }

        else if (viewHolder instanceof  OrientationMode2ViewHolder){
            OrientationMode2ViewHolder viewHolder2 = (OrientationMode2ViewHolder)viewHolder;
            viewHolder2.tvNameEvent_2.setText(item.getName());

            for (int k = 0; k <= Constants.MONTHS.month.length ; k++){
                if(item.getEventWhen().substring(5,7).startsWith("0",0)) {
                    if (item.getEventWhen().substring(5, 7).equals("0" + Integer.toString(k))) {
                        viewHolder2.tvMonthEvent_2.setText(Constants.MONTHS.month1[k]);
                        viewHolder2.tvWhenEvent_2.setText(item.getEventWhen().substring(8, 10) + " " + Constants.MONTHS.month[k]);
                    }
                }else {
                    if (item.getEventWhen().substring(5, 7).equals(Integer.toString(k))) {
                        viewHolder2.tvMonthEvent_2.setText(Constants.MONTHS.month1[k]);
                        viewHolder2.tvWhenEvent_2.setText(item.getEventWhen().substring(8, 10) + " " + Constants.MONTHS.month[k]);
                    }
                }
            }
            PicassoCache.loadImage(item.getImage(), viewHolder2.tvImageEvent_2);
            viewHolder2.tvDescription_2.setText(Html.fromHtml(item.getDescription()));
        }

        else if (viewHolder instanceof ProgressBarViewHolder){
            ((ProgressBarViewHolder) viewHolder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public int getActualItemCount(){
        if (isLoading)
            return items.size() - 1;
        return items.size();
    }


    public void add(List<EventItem> items) {
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

    public void showProgressBar() {Log.d(TAG, "showProgressBar: isLoading = " + isLoading);

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

    public class OrientationMode2ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameEvent_2,tvMonthEvent_2,tvWhenEvent_2,tvDescription_2;
        private ImageView tvImageEvent_2;
        public OrientationMode2ViewHolder(View view) {
            super(view);

            tvNameEvent_2 = (TextView) view.findViewById(R.id.tvNameEvent_2);
            tvMonthEvent_2 = (TextView) view.findViewById(R.id.tvMonthEvent_2);
            tvWhenEvent_2 = (TextView) view.findViewById(R.id.tvWhenEvent_2);
            tvImageEvent_2 = (ImageView) view.findViewById(R.id.tvImageEvent_2);
            tvDescription_2 = (TextView) view.findViewById(R.id.tvDescriptionEvent_2);
        }
    }

    public  class OrientationMode1ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNameEvent_1, tvWhenEvent_1;
        private ImageView tvImageEvent_1;

        public OrientationMode1ViewHolder(View view) {
            super(view);
            tvNameEvent_1 = (TextView) view.findViewById(R.id.tvNameEvent_1);
            tvImageEvent_1 = (ImageView) view.findViewById(R.id.tvImageEvent_1);
            tvWhenEvent_1 = (TextView) view.findViewById(R.id.tvWhenEvent_1);
        }
    }

}

