package wildbakery.ufu.ui.Adapters;

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

import wildbakery.ufu.Constants;
import wildbakery.ufu.Model.ApiModels.SaleItem;
import wildbakery.ufu.R;

public class ItemsAdapterSale extends  RecyclerView.Adapter<ItemsAdapterSale.ViewHolder>  {
    private List<SaleItem> items;

    public ItemsAdapterSale(List<SaleItem> items) {
        this.items = items;
    }

    @Override
    public ItemsAdapterSale.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_sale_row_item, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ItemsAdapterSale.ViewHolder viewHolder, int i) {

        final SaleItem item = items.get(i);

        viewHolder.tv_name_sale.setText(item.getName());
        viewHolder.tv_description_sale.setText(Html.fromHtml(item.getDescription()));
        viewHolder.tv_who_sale.setText(item.getWho());
        if (item.getDateStart() != null){
            viewHolder.tv_date_start.setText(item.getDateStart().substring(0,10));
        }
        else {
            viewHolder.tv_date_start.setText("");
        }

        if(item.getDateEnd() != null){
            viewHolder.tv_date_end.setText(item.getDateEnd().substring(0,10));
        }
        else {
            viewHolder.tv_date_end.setText("");
        }


        Picasso.with(viewHolder.tv_image_sale.getContext()).load(Constants.HTTP.IMAGE_URL +items.get(i).getImage().getPath()).resize(900,500).centerInside().into(viewHolder.tv_image_sale);

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
}
