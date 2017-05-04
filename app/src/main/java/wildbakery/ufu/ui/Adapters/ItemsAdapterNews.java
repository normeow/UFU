package wildbakery.ufu.ui.Adapters;

/**
 * Created by DIKII PEKAR on 19.12.2016.
 */


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.StatsSnapshot;

import java.io.File;
import java.util.List;

import wildbakery.ufu.App;
import wildbakery.ufu.Constants;
import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.R;
import wildbakery.ufu.Utils.ImageSaver;

public class ItemsAdapterNews extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsItem> items;

    private OnItemClickListener onItemClickListener;
    final int ORIENTATION_MODE_1 = 1;
    final int ORIENTATION_MODE_2 = 2;
    public ItemsAdapterNews(List<NewsItem> items, OnItemClickListener onItemClickListener) {
        this.items = items;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int i) {
        final NewsItem item = items.get(i);
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

        final NewsItem item = items.get(i);
        Log.d(getClass().getCanonicalName() + " imagePath: ", item.getImagePath());

        if(viewHolder instanceof OrientationMode1ViewHolder) {
            OrientationMode1ViewHolder viewHolder1 = (OrientationMode1ViewHolder)viewHolder;
             viewHolder1.tv_name_news.setText(item.getName());
             viewHolder1.tv_when_news.setText(item.getNewsWhen().substring(0, 10));
             viewHolder1.tv_category_news.setText(item.getCategory().getName());
             Picasso.with(viewHolder1.tv_image.getContext())
                    .load(new File(item.getImagePath()))
                    .into( viewHolder1.tv_image);

        }
        else if(viewHolder instanceof OrientationMode2ViewHolder) {

            OrientationMode2ViewHolder viewHolder2 = (OrientationMode2ViewHolder) viewHolder;
            Picasso mPicasso = Picasso.with(viewHolder2.tv_image_news_2.getContext());
            mPicasso.setIndicatorsEnabled(true);
            StatsSnapshot picassoStats = mPicasso.getSnapshot();
            viewHolder2.tv_category_news.setText(item.getCategory().getName());
            viewHolder2.tv_name_news.setText(item.getName());
            viewHolder2.tv_when_news.setText(item.getNewsWhen().substring(0, 10));
            viewHolder2.tv_short_description.setText(item.getShortDescription());


            mPicasso.load(new File(item.getImagePath())).resize(300, 200).centerCrop().into(viewHolder2.tv_image_news_2);
            /*if(item.getImagePath() != null) {

                mPicasso.load(Constants.HTTP.IMAGE_URL + item.getImage().getPath())
                .resize(300, 200).centerCrop().into(viewHolder2.tv_image_news_2);
                Log.d("Picasso stats",picassoStats.toString());


            }
            else {
                mPicasso.load(R.drawable.logo).resize(100,100).centerCrop().into(viewHolder2.tv_image_news_2);
            }*/
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

        void onItemClick(NewsItem item);
    }


}
