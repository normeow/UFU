package wildbakery.ufu.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import wildbakery.ufu.Model.Sale.Item;
import wildbakery.ufu.Model.Sale.SaleModel;
import wildbakery.ufu.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSale extends BaseFragment {
    private RecyclerView recyclerView;
    private List<Item> listItems;
    private ItemsAdapterSale mAdapter;
    private LinearLayoutManager mLayoutManager;

    public FragmentSale() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewSale);
        APIservice.Factory.getInstance().getAllSale().enqueue(new Callback<SaleModel>() {

            @Override
            public void onResponse(Call<SaleModel> call, Response<SaleModel> response) {
                if (response.isSuccess()) {
                    listItems = new ArrayList<>();
                    SaleModel result = response.body();
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
            public void onFailure(Call<SaleModel> call, Throwable t) {

            }
        });


        return view;


    }

}