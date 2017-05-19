package wildbakery.ufu.ui.fragments;

import android.support.v7.app.AppCompatActivity;

import com.arellomobile.mvp.MvpAppCompatFragment;

/**
 * Created by Tatiana on 14/05/2017.
 */

public class MvpBaseFragment extends MvpAppCompatFragment {
    public boolean onBackPressed() {
        return true;
    }

    /**
     * show back button in toolbar if detailview on the screen
     */
    public void showArrowBack(){};
    /**
     * hide back button in toolbar if detailview on the screen
     */
    public void hideArrowBack(){
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);};
    public void refresh() {  }
}
