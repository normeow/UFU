package wildbakery.ufu.Adapter;

/**
 * Created by DIKII PEKAR on 19.12.2016.
 */


import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import wildbakery.ufu.Model.News.Item;
import wildbakery.ufu.R;

public class ItemsAdapterNews extends RecyclerView.Adapter<ItemsAdapterNews.ViewHolder> {

    private List<Item> items;
    private OnItemClickListener onItemClickListener;

    public ItemsAdapterNews(List<Item> items, OnItemClickListener onItemClickListener) {
        this.items = items;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemsAdapterNews.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_news_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemsAdapterNews.ViewHolder viewHolder, int i) {
        final Item item = items.get(i);
        viewHolder.tv_name_news.setText(item.getName());
        viewHolder.tv_when_news.setText(item.getNewsWhen());
        viewHolder.tv_short_description.setText(item.getShortDescription());
        viewHolder.tv_description_news.setText(Html.fromHtml(item.getDescription()));
        Picasso.with(viewHolder.tv_image.getContext()).load("http://vuz1.webant.ru/uploads/" + item.getImage().getPath()).into(viewHolder.tv_image);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(item);
            }
        };
        viewHolder.mView.setOnClickListener(listener);
        viewHolder.tv_name_news.setOnClickListener(listener);
        viewHolder.tv_description_news.setOnClickListener(listener);
        viewHolder.tv_short_description.setOnClickListener(listener);
        viewHolder.tv_when_news.setOnClickListener(listener);
        viewHolder.tv_name_news.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name_news, tv_description_news, tv_short_description, tv_when_news;
        private ImageView tv_image;
        private View mView;

        ViewHolder(View view) {
            super(view);
            mView = view.findViewById(R.id.rootView);
            tv_name_news = (TextView) view.findViewById(R.id.tvNameNews);
            tv_description_news = (TextView) view.findViewById(R.id.tvDescriptionNews);
            tv_image = (ImageView) view.findViewById(R.id.imageNews);
            tv_short_description = (TextView) view.findViewById(R.id.tvShortDescriptionNews);
            tv_when_news = (TextView) view.findViewById(R.id.tvWhenNews);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(Item item);
    }
}
