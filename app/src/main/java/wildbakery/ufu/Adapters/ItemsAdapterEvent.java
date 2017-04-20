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




public class ItemsAdapterEvent extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<EventItem> items;
    private OnItemClickListener onItemClickListener;
    final int ORIENTATION_MODE_1 = 1;
    final int ORIENTATION_MODE_2 = 2;
    private  String [] month = {"","Января","Февраля","Марта","Апреля","Мая","Июня","Июля","Августа","Сентября","Октября","Ноября","Декабря"};
    private  String [] month1 = {"","Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};

    public ItemsAdapterEvent(List<EventItem> items, ItemsAdapterEvent.OnItemClickListener onItemClickListener) {
        this.items = items;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view;
        switch (i){
            case 1:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_event_row_item_orientation_1,viewGroup,false);
                return new OrientationMode1ViewHolder(view);
            }
            case 2:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_event_row_item_orientation_2,viewGroup,false);
                return new OrientationMode2ViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        final EventItem item = items.get(position);
        if(item.getOrientationMode() == 1){
            return ORIENTATION_MODE_1;
        }
        else {return ORIENTATION_MODE_2;}
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        final EventItem item = items.get(i);
        if (viewHolder instanceof OrientationMode1ViewHolder) {
            OrientationMode1ViewHolder viewHolder1 = (OrientationMode1ViewHolder)viewHolder;
            viewHolder1.tvWhenEvent_1.setText(item.getEventWhen().substring(8,10));
            viewHolder1.tvNameEvent_1.setText(item.getName());
            Picasso.with( viewHolder1.tvImageEvent_1.getContext())
                    .load(Constants.HTTP.IMAGE_URL + item.getImage().getName()).resize(60,60)
                    .into( viewHolder1.tvImageEvent_1);

        }else if (viewHolder instanceof  OrientationMode2ViewHolder){
            OrientationMode2ViewHolder viewHolder2 = (OrientationMode2ViewHolder)viewHolder;
            viewHolder2.tvNameEvent_2.setText(item.getName());

            for (int k = 0; k <= month.length ; k++){
                if(item.getEventWhen().substring(5,7).startsWith("0",0)) {

                    if (item.getEventWhen().substring(5, 7).equals("0" + Integer.toString(k))) {
                        viewHolder2.tvMonthEvent_2.setText(month1[k]);
                        viewHolder2.tvWhenEvent_2.setText(item.getEventWhen().substring(8, 10) + " " + month[k]);
                    }
                }else {
                    if (item.getEventWhen().substring(5, 7).equals(Integer.toString(k))) {
                        viewHolder2.tvMonthEvent_2.setText(month1[k]);
                        viewHolder2.tvWhenEvent_2.setText(item.getEventWhen().substring(8, 10) + " " + month[k]);

                    }

                }
            }
            Picasso.with(viewHolder2.tvImageEvent_2.getContext()).load(Constants.HTTP.IMAGE_URL + item.getImage().getPath())
                    /*.memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE)*/
                    .resize(500, 300).centerCrop().into(viewHolder2.tvImageEvent_2);
        }

        if(viewHolder instanceof OrientationMode1ViewHolder) {
            OrientationMode1ViewHolder viewHolder1 = (OrientationMode1ViewHolder) viewHolder;
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(item);
                }
            };
            viewHolder1.mView_1.setOnClickListener(listener);
        }
        else if (viewHolder instanceof OrientationMode2ViewHolder) {
            OrientationMode2ViewHolder viewHolder2 = (OrientationMode2ViewHolder) viewHolder;
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(item);
                }
            };
            viewHolder2.mView_2.setOnClickListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class OrientationMode2ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameEvent_2,tvMonthEvent_2,tvWhenEvent_2;
        private View mView_2;
        private ImageView tvImageEvent_2;
        public OrientationMode2ViewHolder(View view) {
            super(view);
            mView_2 = view.findViewById(R.id.viewEvent_2);
            tvNameEvent_2 = (TextView) view.findViewById(R.id.tvNameEvent_2);
            tvMonthEvent_2 = (TextView) view.findViewById(R.id.tvMonthEvent_2);
            tvWhenEvent_2 = (TextView) view.findViewById(R.id.tvWhenEvent_2);
            tvImageEvent_2 = (ImageView) view.findViewById(R.id.tvImageEvent_2);
        }
    }

    public  class OrientationMode1ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNameEvent_1, tvWhenEvent_1,tvMonthEvent_1;
        private ImageView tvImageEvent_1;
        private View mView_1;

        public OrientationMode1ViewHolder(View view) {
            super(view);
            mView_1 = view.findViewById(R.id.viewEvent_1);
            tvNameEvent_1 = (TextView) view.findViewById(R.id.tvNameEvent_1);
            tvImageEvent_1 = (ImageView) view.findViewById(R.id.tvImageEvent_1);
            tvWhenEvent_1 = (TextView) view.findViewById(R.id.tvWhenEvent_1);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(EventItem item);
    }
}

