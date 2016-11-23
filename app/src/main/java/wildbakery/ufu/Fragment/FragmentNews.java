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
public class FragmentNews extends Fragment {

    //private TextView infoTextView;


    public FragmentNews() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        // infoTextView = (TextView) v.findViewById(R.id.infoTextView);
        // MainActivity.infoTextView.setText(R.string.news);
        return v;
    }


}