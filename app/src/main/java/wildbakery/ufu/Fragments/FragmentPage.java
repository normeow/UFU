package wildbakery.ufu.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import wildbakery.ufu.Adapters.ItemsAdapterNews;
import wildbakery.ufu.Models.Item;
import wildbakery.ufu.Models.NewsItem;
import wildbakery.ufu.R;

import static wildbakery.ufu.R.id.recyclerviewNews;

/**
 * Created by Tatiana on 10/04/2017.
 */

public class FragmentPage extends BaseFragment {

    private static final String TAG = "FragmentPage";

    protected RecyclerView recyclerView;
    protected LinearLayoutManager mLayoutManager;


    public static FragmentNews newInstance() {
        FragmentNews fragment = new FragmentNews();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewFragmentPage);
        Log.v(TAG, "onCreateView()");
        return view;
    }


    protected void setRecyclerView(RecyclerView.Adapter<?> adapter){
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
}
