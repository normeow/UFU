package wildbakery.ufu.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import wildbakery.ufu.Model.News.Item;
import wildbakery.ufu.R;

/**
 * Created by DIKII PEKAR on 19.12.2016.
 */

public class DetailFragmentNews extends Fragment {

    private static String ARG_ITEM;
    private Item item;

    public DetailFragmentNews() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = (Item) getArguments().getSerializable("item");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_detail, container, false);

        TextView fragmentTextView = (TextView) view.findViewById(R.id.fragmentTextView);
        fragmentTextView.setText("items id: " + item.getId());

        return view;
    }

    public static DetailFragmentNews newInstance(Item item) {

        Bundle args = new Bundle();
        ARG_ITEM = "item";
        args.putSerializable(ARG_ITEM, item);
        DetailFragmentNews fragment = new DetailFragmentNews();
        fragment.setArguments(args);
        return fragment;
    }
}
