package wildbakery.ufu.Presentation.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import wildbakery.ufu.App;
import wildbakery.ufu.DataFetchers.FetcherCallbacksListener;
import wildbakery.ufu.DataFetchers.NewsFetcher;
import wildbakery.ufu.Model.NewsModel;
import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.Presentation.views.NewsView;
import wildbakery.ufu.R;
import wildbakery.ufu.Utils.ConnectionDetector;

/**
 * Created by Tatiana on 24/04/2017.
 */
@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsView> implements FetcherCallbacksListener {

    private static final int COUNT_ITEMS_TO_LOAD = 20;

    private static final String TAG = "NewsPresenter";
    private NewsModel model;
    private boolean isLoading;
    private boolean isNoData = true;


    private NewsFetcher newsFetcher;
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        newsFetcher = new NewsFetcher(this);
        model = NewsModel.getInstanse();
        getNewsFromDb();
    }

    private void getNewsFromDb(){
        Log.d(TAG, "getNewsFromDb: ");
        getViewState().showProgressBar();
        newsFetcher.fetchDB();
    }

    public void tryGetNews(){
        Log.d(TAG, "tryGetNews: ");
        if (isNoData) {
            getViewState().hideNoDataMessage();
            getViewState().showGettingDataMessage();
        }
        getViewState().showProgressBar();
        newsFetcher.refreshData(COUNT_ITEMS_TO_LOAD);
    }

    public void showDetailFragment(NewsItem item){
        getViewState().showDetail(item);

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        newsFetcher.cancel();
        super.onDestroy();
    }

    public void onError(){
        Log.d(TAG, "onError: ");
        getViewState().showToastMessage(App.getContext().getResources().getString(R.string.cant_load));
    }


    @Override
    public void onRefreshFailed() {
        Log.d(TAG, "onRefreshFailed: ");
        // something goes wrong when tried to fetch data
        getViewState().hideProgressBar();
        if (isNoData) {
            getViewState().hideGettingDataMessage();
            getViewState().showNoDataMessage();
        }
        //getViewState().showOnRefreshError();
        onError();
    }

    @Override
    public void onFetchDataFromServerFinished() {
        Log.d(TAG, "onFetchDataFromServerFinished: ");
        if (isNoData){
            getViewState().hideGettingDataMessage();
        }
        isNoData = false;
        getViewState().hideProgressBar();
        List<NewsItem> items = model.getItems();
        getViewState().showNews(items);
    }

    @Override
    public void onFetchDataFromDbFinished() {
        // NewsFetcher filled NewsModel with cached data
        Log.d(TAG, "onFetchDataFromDbFinished: ");
        List<NewsItem> items = model.getItems();
        if (items != null && !items.isEmpty()){
            getViewState().hideProgressBar();
            getViewState().showNews(items);
            isNoData = false;
        }

        tryGetNews();
    }

    @Override
    public void onModelAppended(int start) {
        isLoading = false;
        getViewState().hideBottomProgressBar();
        getViewState().appendRecycleView(model.getBatchItems(start, COUNT_ITEMS_TO_LOAD));
        Log.d(TAG, "onModelAppended: start = " + start);
    }

    public void onScrollToTheEnd(int start){
        //fetch next 20 items from server
        if (!isLoading){
            Log.d(TAG, "onScrollToTheEnd: start = " + start);
            List<NewsItem> cachedItems = model.getBatchItems(start, COUNT_ITEMS_TO_LOAD);
            if (cachedItems == null || cachedItems.isEmpty()){
                Log.d(TAG, "onScrollToTheEnd: fetching batch");
                getViewState().showBottomProgressBar();
                newsFetcher.fetchBatch(COUNT_ITEMS_TO_LOAD);
            }
            else{
                Log.d(TAG, "onScrollToTheEnd: get cached items from model");
                getViewState().appendRecycleView(cachedItems);
            }
        }
    }

    @Override
    public void onLoadBatchFailed() {
        Log.d(TAG, "onLoadBatchFailed: ");
        isLoading = false;
        getViewState().hideBottomProgressBar();
        getViewState().showLoadingBatchError();
    }

    public void hideDetailFragment(){
        getViewState().hideDetail();
    }



}
