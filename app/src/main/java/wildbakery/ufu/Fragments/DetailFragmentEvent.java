package wildbakery.ufu.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import wildbakery.ufu.Model.Models.EventItem;
import wildbakery.ufu.R;

/**
 * Created by DIKII PEKAR on 19.12.2016.
 */

public class DetailFragmentEvent extends Fragment {

    private static String ARG_ITEM;
    public EventItem item;
    private ImageView detailImage;
    private TextView detailName;
    private  TextView detailWhen;
    public DetailFragmentEvent(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = (EventItem) getArguments().getSerializable("item");


    }




    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);


        return view;
    }

    public static DetailFragmentEvent newInstance(EventItem item){
        Bundle args = new Bundle();
        ARG_ITEM = "item";
        args.putSerializable(ARG_ITEM, item);
        DetailFragmentEvent fragment = new DetailFragmentEvent();
        fragment.setArguments(args);
        return fragment;
    }

}