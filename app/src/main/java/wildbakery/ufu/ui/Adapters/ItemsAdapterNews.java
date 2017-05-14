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

import java.util.List;

import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.R;
import wildbakery.ufu.Utils.PicassoCache;

public class ItemsAdapterNews extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ItemsAdapterNews";

    private List<NewsItem> items;

    private CallbackListener listener;
    final int ORIENTATION_MODE_1 = 1;
    final int ORIENTATION_MODE_2 = 2;
    final int PROGRESS_BAR = 3;

    // number of item from the end when should be started loading
    private static final int COUNT_OFFSET = 3;

    private boolean isLoading = false;

    public ItemsAdapterNews(List<NewsItem> items, CallbackListener onItemClickListener) {
        this.items = items;
        this.listener = onItemClickListener;
    }


    @Override
    public int getItemViewType(int i) {
        final NewsItem item = items.get(i);
        if (item == null)
            return PROGRESS_BAR;

        if(item.getOrientationMode() == 1){
            return ORIENTATION_MODE_1;
        }
        else {return ORIENTATION_MODE_2;}
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;

        switch (viewType) {
            case ORIENTATION_MODE_1: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_news_row_item_orientation_1, viewGroup, false);
                return new OrientationMode1ViewHolder(view);
            }

            case ORIENTATION_MODE_2: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_news_row_item_orientation_2, viewGroup, false);
                return new OrientationMode2ViewHolder(view);
            }

            case PROGRESS_BAR:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progressbar, viewGroup, false);
                return new ProgressBarViewHolder(view);
            }
        }
      return null;
}

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        //scroll almost to the end. notify listener.
        if (i > items.size() - COUNT_OFFSET){
            if (!isLoading)
                listener.onScrolledToTheEnd();
        }
        final NewsItem item = items.get(i);

        if(viewHolder instanceof OrientationMode1ViewHolder) {
            OrientationMode1ViewHolder viewHolder1 = (OrientationMode1ViewHolder)viewHolder;
             viewHolder1.tv_name_news.setText(item.getName());
             viewHolder1.tv_when_news.setText(item.getNewsWhen().substring(0, 10));
             viewHolder1.tv_category_news.setText(item.getCategory().getName());
             PicassoCache.loadImage(item.getImage(), viewHolder1.tv_image);


        }
        else if(viewHolder instanceof OrientationMode2ViewHolder) {

            OrientationMode2ViewHolder viewHolder2 = (OrientationMode2ViewHolder) viewHolder;
            viewHolder2.tv_category_news.setText(item.getCategory().getName());
            viewHolder2.tv_name_news.setText(item.getName());
            viewHolder2.tv_when_news.setText(item.getNewsWhen().substring(0, 10));
            viewHolder2.tv_short_description.setText(item.getShortDescription());

            PicassoCache.loadImage(item.getImage(), viewHolder2.tv_image_news_2);


        }
        if(viewHolder instanceof OrientationMode1ViewHolder) {
            OrientationMode1ViewHolder viewHolder1 = (OrientationMode1ViewHolder) viewHolder;
                View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ItemsAdapterNews.this.listener.onItemClick(item);
                }
            };
            viewHolder1.mView.setOnClickListener(listener);
        }
        else if(viewHolder instanceof OrientationMode2ViewHolder){
            OrientationMode2ViewHolder viewHolder2 = (OrientationMode2ViewHolder) viewHolder;
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ItemsAdapterNews.this.listener.onItemClick(item);
                }
            };
            viewHolder2.mView.setOnClickListener(listener);
        }
        else if (viewHolder instanceof ProgressBarViewHolder){
            ((ProgressBarViewHolder) viewHolder).progressBar.setIndeterminate(true);
        }
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * for outer invokations
     * @return actual number of items in list without progress bar
     */
    public int getActualItemCount(){

        if (isLoading)
            return items.size() - 1;
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

    public void add(List<NewsItem> items){
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

    public interface CallbackListener {
        void onItemClick(NewsItem item);
        void onScrolledToTheEnd();
    }

    public void showProgressBar(){
        Log.d(TAG, "showProgressBar: isLoading = " + isLoading);
        if (!isLoading) {
            isLoading = true;
            items.add(null);
            try {
                notifyItemInserted(items.size() - 1);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void hideProgressBar(){
        Log.d(TAG, "hideProgressBar: isLoading = " + isLoading);
        if (isLoading){
            isLoading = false;
            items.remove(items.size() - 1);
            notifyItemRemoved(items.size());
        }
    }




}
