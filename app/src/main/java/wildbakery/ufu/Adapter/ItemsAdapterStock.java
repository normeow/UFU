package wildbakery.ufu.Adapter;

/**
 * Created by DIKII PEKAR on 19.12.2016.
 */


import wildbakery.ufu.Model.News.Image;
import wildbakery.ufu.Model.Stock.Item;
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

public class ItemsAdapterStock extends  RecyclerView.Adapter<ItemsAdapterStock.ViewHolder>  {
    private List<Item> items;

    public ItemsAdapterStock(List<Item> items) {
        this.items = items;
    }





    @Override
    public ItemsAdapterStock.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_stock_row_item, viewGroup, false);
        return new ViewHolder(view);

    }




    @Override
    public void onBindViewHolder(ItemsAdapterStock.ViewHolder viewHolder, int i) {
        viewHolder.tv_name_stock.setText(items.get(i).getName());
        viewHolder.tv_when_stock.setText(items.get(i).getEventWhen());
        viewHolder.tv_description_stock.setText(Html.fromHtml(items.get(i).getDescription()));
        Picasso.with(viewHolder.tv_image.getContext()).load("http://vuz1.webant.ru/uploads/" +items.get(i).getImage().getPath()).into(viewHolder.tv_image);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name_stock,tv_description_stock,tv_when_stock;
        private ImageView tv_image;
        public ViewHolder(View view) {
            super(view);

            tv_name_stock = (TextView)view.findViewById(R.id.tvNameStock);
            tv_description_stock = (TextView)view.findViewById(R.id.tvDescriptionStock);
            tv_image = (ImageView)view.findViewById(R.id.ImageStock);
            tv_when_stock = (TextView) view.findViewById(R.id.tvWhenStock);



        }


    }
}
