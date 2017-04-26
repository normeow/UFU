package wildbakery.ufu.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import wildbakery.ufu.R;
import wildbakery.ufu.ui.fragments.MvpNewsFragment;

/**
 * Created by Tatiana on 26/04/2017.
 */

public class MvpMainAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detailFrameLayout, new MvpNewsFragment())
                .commit();

    }
}
