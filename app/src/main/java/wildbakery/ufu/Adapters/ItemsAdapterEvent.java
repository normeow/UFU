package wildbakery.ufu.Adapters;

/**
 * Created by DIKII PEKAR on 13.02.2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import wildbakery.ufu.Constants;
import wildbakery.ufu.Models.EventItem;
import wildbakery.ufu.R;




public class ItemsAdapterEvent extends RecyclerView.Adapter<ItemsAdapterEvent.ViewHolder> {
    private List<EventItem> items;
    private OnItemClickListener onItemClickListener;

    public ItemsAdapterEvent(List<EventItem> items, ItemsAdapterEvent.OnItemClickListener onItemClickListener) {
        this.items = items;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemsAdapterEvent.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_event_row_item, viewGroup, false);



        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ItemsAdapterEvent.ViewHolder viewHolder, int i) {
        final EventItem item = items.get(i);

        viewHolder.tv_when_event.setText(item.getEventWhen().substring(0,10));
        viewHolder.tv_name_event.setText(item.getName());
        Picasso.with(viewHolder.tv_image_event.getContext()).load(Constants.HTTP.IMAGE_URL +items.get(i).getImage().getPath()).resize(900,500).centerCrop().into(viewHolder.tv_image_event);





        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(item);

            }
        };
        viewHolder.mView.setOnClickListener(listener);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name_event, tv_description_event,tv_month_event,tv_when_event;
        private View mView;
        private ImageView tv_image_event;
        public ViewHolder(View view) {
            super(view);
            mView = view.findViewById(R.id.viewEvent);
            tv_name_event = (TextView) view.findViewById(R.id.tvNameEvent);
            //tv_month_event = (TextView) view.findViewById(R.id.tvMonthEvent_2);
           // tv_description_event = (TextView) view.findViewById(R.id.tvShortDescriptionNews_2);
            tv_when_event = (TextView) view.findViewById(R.id.tvWhenEvent_2);
            tv_image_event = (ImageView) view.findViewById(R.id.tvImageEvent_2);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(EventItem item);
    }
}

