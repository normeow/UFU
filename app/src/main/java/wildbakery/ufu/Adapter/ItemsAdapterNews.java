package wildbakery.ufu.Adapter;

/**
 * Created by DIKII PEKAR on 19.12.2016.
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
import wildbakery.ufu.Model.News.Item;
import wildbakery.ufu.R;

public class ItemsAdapterNews extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> items;

    private OnItemClickListener onItemClickListener;
    final int ORIENTATION_MODE_1 = 1;
    final int ORIENTATION_MODE_2 = 2;
    public ItemsAdapterNews(List<Item> items, OnItemClickListener onItemClickListener) {
        this.items = items;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int i) {
        final Item item = items.get(i);
        if(item.getOrientationMode() == 1){
            return ORIENTATION_MODE_1;
        }
        else {return ORIENTATION_MODE_2;}
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;

        switch (i) {
            case 1: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_news_row_item_orientation_1, viewGroup, false);
                return new OrientationMode1ViewHolder(view);
            }

            case 2: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_news_row_item_orientation_2, viewGroup, false);
                return new OrientationMode2ViewHolder(view);
            }
        }
      return null;
}

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        final Item item = items.get(i);

        if(viewHolder instanceof OrientationMode1ViewHolder) {
            OrientationMode1ViewHolder viewHolder1 = (OrientationMode1ViewHolder)viewHolder;
             viewHolder1.tv_name_news.setText(item.getName());
             viewHolder1.tv_when_news.setText(item.getNewsWhen().substring(0, 10));
             viewHolder1.tv_category_news.setText(item.getCategory().getName());
            Picasso.with( viewHolder1.tv_image.getContext())
                    .load(Constants.HTTP.IMAGE_URL + item.getImage().getName())
                    .into( viewHolder1.tv_image);

        }
        else if(viewHolder instanceof OrientationMode2ViewHolder) {
            OrientationMode2ViewHolder viewHolder2 = (OrientationMode2ViewHolder) viewHolder;
            viewHolder2.tv_category_news.setText(item.getCategory().getName());
            viewHolder2.tv_name_news.setText(item.getName());
            viewHolder2.tv_when_news.setText(item.getNewsWhen().substring(0, 10));
            viewHolder2.tv_short_description.setText(item.getShortDescription());

            if(item.getImage() != null) {
                Picasso.with(viewHolder2.tv_image_news_2.getContext()).load(Constants.HTTP.IMAGE_URL + item.getImage().getPath())/*.memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE)*/.resize(500, 300).centerCrop().into(viewHolder2.tv_image_news_2);
            }
            else {
                Picasso.with(viewHolder2.tv_image_news_2.getContext()).load(R.drawable.logo).resize(500,300).centerInside().into(viewHolder2.tv_image_news_2);
            }
        }
        if(viewHolder instanceof OrientationMode1ViewHolder) {
            OrientationMode1ViewHolder viewHolder1 = (OrientationMode1ViewHolder) viewHolder;
                View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(item);
                }
            };
            viewHolder1.mView.setOnClickListener(listener);
        }
        else if(viewHolder instanceof OrientationMode2ViewHolder){
            OrientationMode2ViewHolder viewHolder2 = (OrientationMode2ViewHolder) viewHolder;
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(item);
                }
            };
            viewHolder2.mView.setOnClickListener(listener);


        }
    }
    @Override
    public int getItemCount() {
        return items.size();
    }



    public  class OrientationMode1ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name_news, tv_when_news,tv_category_news;
        private ImageView tv_image;
        private View mView;

       public OrientationMode1ViewHolder(View view) {
            super(view);
            mView = view.findViewById(R.id.viewNews_1);
            tv_name_news = (TextView) view.findViewById(R.id.tvNameNews);
            tv_image = (ImageView) view.findViewById(R.id.imageNews);
            tv_when_news = (TextView) view.findViewById(R.id.tvWhenNews);
            tv_category_news = (TextView) view.findViewById(R.id.tvCategoryNews);
        }

    }

    public class OrientationMode2ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name_news, tv_short_description, tv_when_news,tv_category_news;
        private ImageView tv_image_news_2;
        private View mView;

       public OrientationMode2ViewHolder(View view) {
            super(view);
            mView = view.findViewById(R.id.viewNews_2);
            tv_name_news = (TextView) view.findViewById(R.id.tvNameNews_2);
            tv_image_news_2 = (ImageView) view.findViewById(R.id.tvImageNews_2);
            tv_when_news = (TextView) view.findViewById(R.id.tvWhenNews_2);
            tv_category_news = (TextView) view.findViewById(R.id.tvCategoryNews_2);
            tv_short_description = (TextView) view.findViewById(R.id.tvShortDescriptionNews_2);
        }

    }

    public interface OnItemClickListener {

        void onItemClick(Item item);
    }


}
