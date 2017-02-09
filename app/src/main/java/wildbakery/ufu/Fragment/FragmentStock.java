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
import wildbakery.ufu.Adapter.ItemsAdapterStock;
import wildbakery.ufu.Interfaces.APIservice;
import wildbakery.ufu.Model.Stock.Item;
import wildbakery.ufu.Model.Stock.StockModel;
import wildbakery.ufu.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStock extends BaseFragment {
    private RecyclerView recyclerView;
    private List<Item> listItems;
    private ItemsAdapterStock mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public FragmentStock() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stock, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewStock);


        APIservice.Factory.getInstance().getAllStock().enqueue(new Callback<StockModel>() {

            @Override
            public void onResponse(Call<StockModel> call, Response<StockModel> response) {

                if (response.isSuccess()) {
                    listItems = new ArrayList<>();
                    StockModel result = response.body();
                    listItems = result.getItems();

                    mAdapter = new ItemsAdapterStock(listItems);

                    mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                }

            }

            @Override
            public void onFailure(Call<StockModel> call, Throwable t) {

            }
        });


        return view;


    }

}
