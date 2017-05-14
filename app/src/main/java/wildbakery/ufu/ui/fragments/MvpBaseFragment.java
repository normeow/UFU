package wildbakery.ufu.ui.fragments;

import com.arellomobile.mvp.MvpAppCompatFragment;

/**
 * Created by Tatiana on 14/05/2017.
 */

public class MvpBaseFragment extends MvpAppCompatFragment {
    public boolean onBackPressed() {
        return true;
    }

    public void refresh() {  }
}
