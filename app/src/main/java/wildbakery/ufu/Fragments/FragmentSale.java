package wildbakery.ufu.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.concurrent.ExecutionException;

import wildbakery.ufu.Adapters.ItemsAdapterSale;
import wildbakery.ufu.FetchDataPackage.DataFetcher;
import wildbakery.ufu.Models.SaleItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSale extends FragmentPage {

    private static final String TAG = "FragmentSale";
    private List<SaleItem> listItems;
    private ItemsAdapterSale adapter;

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
        try {
            updateRecycleView();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    protected void updateRecycleView() throws ExecutionException, InterruptedException {
        DataFetcher dataFetcher = DataFetcher.getInstance();
        listItems = dataFetcher.getSales();
        setRecyclerView();
    }

    private void setRecyclerView(){
        adapter = new ItemsAdapterSale(listItems);
        super.setRecyclerView(adapter);
    }
}
