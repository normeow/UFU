package wildbakery.ufu.Presentation.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import wildbakery.ufu.DataFetchers.FetcherCallbacksListener;
import wildbakery.ufu.DataFetchers.EventsFetcher;
import wildbakery.ufu.Model.ApiModels.EventItem;
import wildbakery.ufu.Model.EventsModel;
import wildbakery.ufu.Presentation.views.EventsView;

/**
 * Created by Tatiana on 13/05/2017.
 */
@InjectViewState
public class EventsPresenter extends MvpPresenter<EventsView> implements FetcherCallbacksListener {
    private static final int COUNT_ITEMS_TO_LOAD = 16;

    private static final String TAG = "EventsPresenter";
    private EventsModel model;

    private EventsFetcher eventsFetcher;
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        eventsFetcher = new EventsFetcher(this);
        model = EventsModel.getInstanse();
        getEventsFromDb();
    }

    private void getEventsFromDb(){
        Log.d(TAG, "getEventsFromDb: ");
        getViewState().showProgressBar();
        eventsFetcher.fetchDB();
    }

    public void tryGetEvents(){
        Log.d(TAG, "tryGetEvents: ");
        getViewState().showProgressBar();
        eventsFetcher.refreshData(COUNT_ITEMS_TO_LOAD);
    }

    public void showDetailFragment(EventItem item){
        getViewState().showDetail(item);

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        eventsFetcher.cancel();
        super.onDestroy();
    }

    public void onError(){
        getViewState().showToastMessage("Failed to get events");
    }


    @Override
    public void onRefreshFailed() {
        // something goes wrong when tried to fetch data
        getViewState().hideProgressBar();
        getViewState().showOnRefreshError();
    }

    @Override
    public void onFetchDataFromServerFinished() {
        Log.d(TAG, "onFetchDataFromServerFinished: ");
        getViewState().hideProgressBar();
        List<EventItem> items = model.getItems();
        getViewState().showEvents(items);
    }

    @Override
    public void onFetchDataFromDbFinished() {
        // EventsFetcher filled EventsModel with cached data
        Log.d(TAG, "onFetchDataFromDbFinished: ");
        List<EventItem> items = model.getItems();
        if (items != null && !items.isEmpty()){
            getViewState().hideProgressBar();
            getViewState().showEvents(items);
        }

        tryGetEvents();
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
        List<EventItem> cachedItems = model.getBatchItems(start, COUNT_ITEMS_TO_LOAD);
        if (cachedItems == null || cachedItems.isEmpty()){
            Log.d(TAG, "onScrollToTheEnd: fetching batch");
            getViewState().showBottomProgressBar();
            eventsFetcher.fetchBatch(start, COUNT_ITEMS_TO_LOAD);
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
