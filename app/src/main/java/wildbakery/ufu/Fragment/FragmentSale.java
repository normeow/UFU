package wildbakery.ufu.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wildbakery.ufu.Adapter.ItemsAdapterSale;
import wildbakery.ufu.Interfaces.APIservice;
import wildbakery.ufu.Model.SaleItem;
import wildbakery.ufu.R;
import wildbakery.ufu.Model.QueryModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSale extends BaseFragment {

    private static final String TAG = "FragmentSale";
    private RecyclerView recyclerView;
    private List<SaleItem> listItems;
    private ItemsAdapterSale mAdapter;
    private LinearLayoutManager mLayoutManager;

    public FragmentSale() {
    }

    public static FragmentSale newInstance() {

        Bundle args = new Bundle();

        FragmentSale fragment = new FragmentSale();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewSale);
        Log.v(TAG, "onCreateView()");
        APIservice.Factory.getInstance().getAllSale().enqueue(new Callback<QueryModel<SaleItem>>() {

            @Override
            public void onResponse(Call<QueryModel<SaleItem>> call, Response<QueryModel<SaleItem>> response) {
                if (response.isSuccess()) {
                    Log.v(TAG, "refresh");
                    listItems = new ArrayList<>();
                    QueryModel<SaleItem> result = response.body();
                    listItems = result.getItems();
                    mAdapter = new ItemsAdapterSale(listItems);
                    mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    mLayoutManager.setStackFromEnd(true);
                    mLayoutManager.setReverseLayout(true);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }

            }

            @Override
            public void onFailure(Call<QueryModel<SaleItem>> call, Throwable t) {

            }
        });


        return view;


    }

}
