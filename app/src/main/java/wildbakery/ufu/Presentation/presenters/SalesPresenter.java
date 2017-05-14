package wildbakery.ufu.Presentation.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import wildbakery.ufu.DataFetchers.FetcherCallbacksListener;
import wildbakery.ufu.DataFetchers.SalesFetcher;
import wildbakery.ufu.Model.ApiModels.SaleItem;
import wildbakery.ufu.Model.SalesModel;
import wildbakery.ufu.Presentation.views.SalesView;

/**
 * Created by Tatiana on 13/05/2017.
 */
@InjectViewState
public class SalesPresenter extends MvpPresenter<SalesView> implements FetcherCallbacksListener {
    private static final int COUNT_ITEMS_TO_LOAD = 16;

    private static final String TAG = "SalesPresenter";
    private SalesModel model;

    private SalesFetcher jobsFetcher;
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        jobsFetcher = new SalesFetcher(this);
        model = SalesModel.getInstanse();
        getSalesFromDb();
    }

    private void getSalesFromDb(){
        Log.d(TAG, "getSalesFromDb: ");
        getViewState().showProgressBar();
        jobsFetcher.fetchDB();
    }

    public void tryGetSales(){
        Log.d(TAG, "tryGetSales: ");
        getViewState().showProgressBar();
        jobsFetcher.refreshData(COUNT_ITEMS_TO_LOAD);
    }

    public void showDetailFragment(SaleItem item){
        getViewState().showDetail(item);

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        jobsFetcher.cancel();
        super.onDestroy();
    }

    public void onError(){
        getViewState().showToastMessage("Failed to get jobs");
    }


    @Override
    public void onRefreshFailed() {
        // something goes wrong when tried to fetch data
        getViewState().hideProgressBar();
        onError();
    }

    @Override
    public void onFetchDataFromServerFinished() {
        Log.d(TAG, "onFetchDataFromServerFinished: ");
        getViewState().hideProgressBar();
        List<SaleItem> items = model.getItems();
        getViewState().showSales(items);
    }

    @Override
    public void onFetchDataFromDbFinished() {
        // SalesFetcher filled SalesModel with cached data
        Log.d(TAG, "onFetchDataFromDbFinished: ");
        List<SaleItem> items = model.getItems();
        if (items != null && !items.isEmpty()){
            getViewState().hideProgressBar();
            getViewState().showSales(items);
        }

        tryGetSales();
    }

    @Override
    public void onModelAppended(int start) {
        getViewState().hideBottomProgressBar();
        getViewState().appendRecycleView(model.getBatchItems(start, COUNT_ITEMS_TO_LOAD));
        Log.d(TAG, "onModelAppended: success");
    }

    public void onScrollToTheEnd(int start){
        //fetch next 20 items from server
        Log.d(TAG, "onScrollToTheEnd: start = " + start);
        List<SaleItem> cachedItems = model.getBatchItems(start, COUNT_ITEMS_TO_LOAD);
        if (cachedItems == null || cachedItems.isEmpty()){
            Log.d(TAG, "onScrollToTheEnd: fetching batch");
            getViewState().showBottomProgressBar();
            jobsFetcher.fetchBatch(start, COUNT_ITEMS_TO_LOAD);
        }
        else{
            Log.d(TAG, "onScrollToTheEnd: get cached items from model");
            getViewState().appendRecycleView(cachedItems);
        }
    }

    @Override
    public void onLoadBatchFailed() {
        Log.d(TAG, "onLoadBatchFailed: ");
        getViewState().hideBottomProgressBar();
        getViewState().showLoadingBatchError();
    }
}
