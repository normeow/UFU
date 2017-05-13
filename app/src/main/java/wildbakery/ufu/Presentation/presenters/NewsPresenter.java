package wildbakery.ufu.Presentation.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import wildbakery.ufu.DataFetchers.NewsFetcher;
import wildbakery.ufu.Model.NewsModel;
import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.Presentation.views.NewsView;

/**
 * Created by Tatiana on 24/04/2017.
 */
@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsView> implements NewsFetcher.CallbacksListener {

    private static final int COUNT_ITEMS_TO_LOAD = 15;

    private static final String TAG = "NewsPresenter";
    private NewsModel model;


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
        getViewState().showProgressBar();
        newsFetcher.refreshData(COUNT_ITEMS_TO_LOAD);
    }

    public void showDetailFragment(NewsItem item){
        getViewState().showDetail(item);

    }

    @Override
    public void onDestroy() {
        // todo cancel all tasks in newsFetcher
        super.onDestroy();
    }

    public void onError(){
        getViewState().showToastMessage("Failed to get news");
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
        List<NewsItem> items = model.getItems();
        getViewState().showNews(items);
    }

    @Override
    public void onFetchDataFromDbFinished() {
        // NewsFetcher filled NewsModel with cached data
        Log.d(TAG, "onFetchDataFromDbFinished: ");
        List<NewsItem> items = model.getItems();
        if (items != null && !items.isEmpty()){
            //getViewState().hideProgressBar();
            getViewState().showNews(items);
        }

        tryGetNews();
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

    @Override
    public void onLoadBatchFailed() {
        Log.d(TAG, "onLoadBatchFailed: ");
        getViewState().hideBottomProgressBar();
        getViewState().showLoadingBatchError();
    }
}
