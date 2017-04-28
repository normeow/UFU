package wildbakery.ufu.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wildbakery.ufu.ui.Adapters.ItemsAdapterSale;
import wildbakery.ufu.Model.DataFetcher;
import wildbakery.ufu.Model.VuzAPI;
import wildbakery.ufu.Model.ApiModels.QueryModel;
import wildbakery.ufu.Model.ApiModels.SaleItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSale extends BaseFragmentPage {

    private static final String TAG = "FragmentSale";
    private List<SaleItem> listItems;
    private ItemsAdapterSale adapter;
    private Call<QueryModel<SaleItem>> call;

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
        View view = super.onCreateView(inflater, container, savedInstanceState);
        call = VuzAPI.Factory.getInstance().getSales();
        fetchData();
        return view;
    }

    @Override
    public void updateRecycleView() throws ExecutionException, InterruptedException {
        DataFetcher dataFetcher = new DataFetcher();
        listItems = dataFetcher.getSales();
        setRecyclerView();
    }

    private void setRecyclerView(){
        adapter = new ItemsAdapterSale(listItems);
        super.setRecyclerView(adapter);
    }

    @Override
    protected void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
        call.clone().enqueue(new Callback<QueryModel<SaleItem>>() {
            @Override
            public void onResponse(Call<QueryModel<SaleItem>> call, Response<QueryModel<SaleItem>> response) {
                swipeRefreshLayout.setRefreshing(false);
                listItems = response.body().getItems();
                setRecyclerView();
            }

            @Override
            public void onFailure(Call<QueryModel<SaleItem>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "Can't get sales", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
