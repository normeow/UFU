package wildbakery.ufu.Adapter;

/**
 * Created by DIKII PEKAR on 19.12.2016.
 */


import wildbakery.ufu.Model.News.Image;
import wildbakery.ufu.Model.News.Item;
import wildbakery.ufu.Model.News.NewsModel;
import wildbakery.ufu.R;


import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static wildbakery.ufu.Constants.HTTP.BASE_URL;

public class ItemsAdapterNews extends  RecyclerView.Adapter<ItemsAdapterNews.ViewHolder>  {
    private List<Item> items;

    public ItemsAdapterNews(List<Item> items) {
        this.items = items;
    }





    @Override
    public ItemsAdapterNews.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_news_row_item, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ItemsAdapterNews.ViewHolder viewHolder, int i) {
        viewHolder.tv_name_news.setText(items.get(i).getName());
        viewHolder.tv_when_news.setText(items.get(i).getNewsWhen());
        viewHolder.tv_short_description.setText(items.get(i).getShortDescription());
        viewHolder.tv_description_news.setText(Html.fromHtml(items.get(i).getDescription()));
        Picasso.with(viewHolder.tv_image.getContext()).load("http://vuz1.webant.ru/uploads/" +items.get(i).getImage().getPath()).into(viewHolder.tv_image);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name_news,tv_description_news,tv_short_description,tv_when_news;
        private ImageView tv_image;
        public ViewHolder(View view) {
            super(view);

            tv_name_news = (TextView)view.findViewById(R.id.tvNameNews);
            tv_description_news = (TextView)view.findViewById(R.id.tvDescriptionNews);
            tv_image = (ImageView)view.findViewById(R.id.imageNews);
            tv_short_description = (TextView) view.findViewById(R.id.tvShortDescriptionNews);
            tv_when_news = (TextView) view.findViewById(R.id.tvWhenNews);



        }


    }
}
