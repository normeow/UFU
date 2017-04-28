package wildbakery.ufu.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import wildbakery.ufu.Fragments.DetailFragmentNews;
import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.R;

/**
 * Created by Tatiana on 26/04/2017.
 */

public class DetailNewsAcivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        NewsItem item = (NewsItem) getIntent().getExtras().getSerializable(DetailFragmentNews.KEY_STRING_ITEM);
        Fragment fragment = DetailFragmentNews.newInstance(item);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detailFrameLayout, fragment)
                .commit();
    }
}
