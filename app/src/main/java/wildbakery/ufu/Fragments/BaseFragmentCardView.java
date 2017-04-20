package wildbakery.ufu.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.ExecutionException;

import wildbakery.ufu.R;


public class BaseFragmentCardView extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "BaseFragmentPage";

    protected RecyclerView recyclerView;
    protected LinearLayoutManager mLayoutManager;
    protected ProgressDialog progressDialog;
    protected SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_page, container, false);
        //recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewFragmentPage);
        View view = inflater.inflate(R.layout.base_fragment_with_swipe, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.baseRecyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        Log.v(TAG, "onCreateView()");
        //progressDialog = new ProgressDialog(getContext());
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

    public boolean onBackPressed() {
        return true;
    }
    public void updateRecycleView() throws ExecutionException, InterruptedException {};
    protected void fetchData() {}

    @Override
    public void onRefresh() {
        fetchData();
    }
}
