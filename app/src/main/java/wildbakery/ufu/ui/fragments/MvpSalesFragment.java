package wildbakery.ufu.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import wildbakery.ufu.Model.ApiModels.SaleItem;
import wildbakery.ufu.Presentation.presenters.SalesPresenter;
import wildbakery.ufu.Presentation.views.SalesView;
import wildbakery.ufu.R;
import wildbakery.ufu.ui.Adapters.ItemsAdapterSale;

/**
 * Created by Tatiana on 26/04/2017.
 */

public class MvpSalesFragment extends MvpBaseFragment implements SalesView, SwipeRefreshLayout.OnRefreshListener, ItemsAdapterSale.CallbackListener, TryAgatinFragment.TryAgainListener {

    @InjectPresenter
    SalesPresenter presenter;

    private static final String TAG = "MvpSalesFragment";

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ItemsAdapterSale adapter;
    private CoordinatorLayout rootLayout;
    private Snackbar errorSnackBar;
    private Snackbar refreshErrorSnackBar;
    private View gettingDataMsg;
    private Fragment tryAgainFragment = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: created");
        View view = inflater.inflate(R.layout.sales_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.salesRecycleView);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.salesSwipeLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        // mLayoutManager.setReverseLayout(true);
        // mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        rootLayout = (CoordinatorLayout) view.findViewById(R.id.salesFragmentLayout);
        gettingDataMsg = view.findViewById(R.id.sales_gettingdata);
        setSnackBar();
        return view;
    }

    private void setSnackBar(){
        errorSnackBar = Snackbar.make(rootLayout, getString(R.string.cant_load), BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.try_again), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "onClick: snackbar");
                        onScrolledToTheEnd();

                    }
                });
    }

    private void setRefreshSnackBar(){
        refreshErrorSnackBar = Snackbar.make(rootLayout, getString(R.string.cant_refresh), BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.try_again), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "onClick: snackbar");
                        presenter.tryGetSales();

                    }
                });

        View view = refreshErrorSnackBar.getView();
        CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
    }

    @Override
    public void showSales(List<SaleItem> sales) {
        if (errorSnackBar.isShown())
            errorSnackBar.dismiss();
        adapter = new ItemsAdapterSale(sales, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showDetail(SaleItem salesItem) {
//        Log.d(getClass().getCanonicalName(), "onItemClick: item = " + salesItem);
//        Intent intent = new Intent(getContext(), DetailSaleAcivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(DetailFragmentSale.KEY_STRING_ITEM, salesItem);
//        intent.putExtras(bundle);
//        startActivity(intent);

    }

    @Override
    public void showProgressBar() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showToastMessage(String msg) {
        if (getUserVisibleHint())
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        presenter.tryGetSales();
    }

    @Override
    public void onScrolledToTheEnd() {
        if (errorSnackBar.isShown())
            errorSnackBar.dismiss();
        presenter.onScrollToTheEnd(adapter.getActualItemCount());
    }

    @Override
    public void appendRecycleView(List<SaleItem> items) {
        adapter.add(items);
    }

    @Override
    public void showLoadingBatchError() {
        setSnackBar();
        errorSnackBar.show();
    }

    @Override
    public void showBottomProgressBar() {
        adapter.showProgressBar();
        recyclerView.scrollToPosition(adapter.getActualItemCount());
    }

    @Override
    public void hideBottomProgressBar() {
        adapter.hideProgressBar();
    }

    @Override
    public boolean onBackPressed() {
        return super.onBackPressed();
    }

    @Override
    public void refresh() {
        presenter.tryGetSales();
    }

    @Override
    public void showOnRefreshError() {
        if (getUserVisibleHint()) {
            Log.d(TAG, "showOnRefreshError: ");
            setRefreshSnackBar();
            refreshErrorSnackBar.show();
        }
    }

    @Override
    public void showNoDataMessage() {
        Log.d(TAG, "showNoDataMessage: ");

        if (tryAgainFragment == null) {
            tryAgainFragment = new TryAgatinFragment();
            getChildFragmentManager().beginTransaction().replace(R.id.salesFragmentLayout, tryAgainFragment).commit();
        }

    }

    @Override
    public void showGettingDataMessage() {
        Log.d(TAG, "showGettingDataMessage: ");
        gettingDataMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideGettingDataMessage() {
        Log.d(TAG, "hideGettingDataMessage: ");
        gettingDataMsg.setVisibility(View.GONE);
    }

    @Override
    public void hideNoDataMessage() {
        Log.d(TAG, "hideNoDataMessage: ");
        if (tryAgainFragment != null) {
            getChildFragmentManager().beginTransaction().remove(tryAgainFragment).commit();
            tryAgainFragment = null;
        }
    }


    @Override
    public void onTryAgainClicked() {
        refresh();

    }
}
