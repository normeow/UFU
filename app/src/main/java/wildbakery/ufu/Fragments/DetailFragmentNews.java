package wildbakery.ufu.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import wildbakery.ufu.Constants;
import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.R;
import wildbakery.ufu.Utils.PicassoCache;

/**
 * Created by DIKII PEKAR on 19.12.2016.
 */

public class DetailFragmentNews extends Fragment {

    public static final String KEY_STRING_ITEM = "item";

    private static String ARG_ITEM;
    private NewsItem item;
    private TextView tvNameDetail,tvCatrgoryDetail,tvWhenDetail,tvDescriptionDetail;
    private ImageView tvImageDetail;
    public DetailFragmentNews() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = (NewsItem) getArguments().getSerializable(KEY_STRING_ITEM);
    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        tvNameDetail = (TextView) view.findViewById(R.id.tvNameNews_details);
        tvCatrgoryDetail = (TextView) view.findViewById(R.id.tvCategoryNews_details);
        tvWhenDetail = (TextView) view.findViewById(R.id.tvWhenNews_details);
        tvDescriptionDetail = (TextView) view.findViewById(R.id.tvDescriptionNews_details);
        tvImageDetail = (ImageView) view.findViewById(R.id.tvImageNews_details);

        tvWhenDetail.setText(item.getNewsWhen().substring(0, 10));
        tvCatrgoryDetail.setText(item.getCategory().getName());
        tvNameDetail.setText(item.getName());
        tvDescriptionDetail.setText(Html.fromHtml(item.getDescription()));

        PicassoCache.loadImage(item.getImage(), tvImageDetail);

        return view;
    }

    public static DetailFragmentNews newInstance(NewsItem item) {

        Bundle args = new Bundle();
        ARG_ITEM = "item";
        args.putSerializable(ARG_ITEM, item);
        DetailFragmentNews fragment = new DetailFragmentNews();
        fragment.setArguments(args);
        return fragment;
    }
}
