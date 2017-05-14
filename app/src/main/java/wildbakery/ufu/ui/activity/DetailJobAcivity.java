package wildbakery.ufu.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import wildbakery.ufu.Fragments.DetailFragmentJob;
import wildbakery.ufu.Model.ApiModels.JobItem;
import wildbakery.ufu.R;

/**
 * Created by Tatiana on 26/04/2017.
 */

public class DetailJobAcivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        JobItem item = (JobItem) getIntent().getExtras().getSerializable(DetailFragmentJob.KEY_STRING_ITEM);
        Fragment fragment = DetailFragmentJob.newInstance(item);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detailFrameLayout, fragment)
                .commit();
    }
}
