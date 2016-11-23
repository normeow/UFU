package wildbakery.ufu.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wildbakery.ufu.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentJob extends Fragment {



    public FragmentJob() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job, container, false);
    }

}
