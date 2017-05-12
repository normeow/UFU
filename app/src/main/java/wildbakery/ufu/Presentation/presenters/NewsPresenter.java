package wildbakery.ufu.Presentation.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import retrofit2.Call;
import wildbakery.ufu.DataFetchers.NewsFetcher;
import wildbakery.ufu.Model.ApiModels.QueryModel;
import wildbakery.ufu.Model.NewsModel;
import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.Presentation.views.NewsView;

/**
 * Created by Tatiana on 24/04/2017.
 */
@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsView> implements NewsFetcher.CallbacksListener {

    private static final String TAG = "NewsPresenter";
    private NewsModel model;
    private boolean isLoading = false;
    private static final int COUNT_ITEMS_TO_LOAD = 20;


    private NewsFetcher newsFetcher;
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        newsFetcher = new NewsFetcher(this);
        model = NewsModel.getInstanse();
        getNewsFromDb();
    }

    private void setLoadingState(){
        if (isLoading)
            return;
        isLoading = true;
        getViewState().showProgressDialog();
    }

    private void setNotLoadingState(){
        if (!isLoading)
            return;
        isLoading = false;
        getViewState().hideProgressDialog();
    }

    private void getNewsFromDb(){
        Log.d(TAG, "getNewsFromDb: ");
        setLoadingState();
        newsFetcher.fetchDB();
    }

    public void tryGetNews(){
        Log.d(TAG, "tryGetNews: ");
        setLoadingState();
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
    public void onFailure() {
        // something goes wrong when tried to fetch data
        setNotLoadingState();
        onError();
    }

    @Override
    public void onFetchDataFromServerFinished() {
        Log.d(TAG, "onFetchDataFromServerFinished: ");
        setNotLoadingState();
        List<NewsItem> items = model.getItems();
        getViewState().showNews(items);
    }

    @Override
    public void onFetchDataFromDbFinished() {
        // NewsFetcher filled NewsModel with cached data
        Log.d(TAG, "onFetchDataFromDbFinished: ");
        List<NewsItem> items = model.getItems();
        if (items != null && !items.isEmpty()){
            //setNotLoadingState();
            getViewState().showNews(items);
        }

        tryGetNews();
    }

    @Override
    public void onModelAppended(int start) {
        getViewState().appendRecycleView(model.getBatchItems(start, COUNT_ITEMS_TO_LOAD));
        setNotLoadingState();
        Log.d(TAG, "onModelAppended: success");
    }

    public void onScrollToTheEnd(int start){
        //fetch next 20 items from server
        if (isLoading)
            return;
        Log.d(TAG, "onScrollToTheEnd: start = " + start);
        setLoadingState();
        List<NewsItem> cachedItems = model.getBatchItems(start, COUNT_ITEMS_TO_LOAD);
        if (cachedItems == null || cachedItems.isEmpty()){
            Log.d(TAG, "onScrollToTheEnd: fetching batch");
            newsFetcher.fetchBatch(COUNT_ITEMS_TO_LOAD);
        }
        else{

            Log.d(TAG, "onScrollToTheEnd: get cached items from model");
            getViewState().appendRecycleView(cachedItems);
        }
    }
}
