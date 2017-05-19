package wildbakery.ufu.Presentation.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import wildbakery.ufu.DataFetchers.FetcherCallbacksListener;
import wildbakery.ufu.DataFetchers.JobsFetcher;
import wildbakery.ufu.Model.ApiModels.JobItem;
import wildbakery.ufu.Model.JobsModel;
import wildbakery.ufu.Presentation.views.JobsView;

/**
 * Created by Tatiana on 13/05/2017.
 */
@InjectViewState
public class JobsPresenter extends MvpPresenter<JobsView> implements FetcherCallbacksListener {
    // shold be power of 2
    private static final int COUNT_ITEMS_TO_LOAD = 16;

    private static final String TAG = "JobsPresenter";
    private JobsModel model;


    private JobsFetcher jobsFetcher;
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        jobsFetcher = new JobsFetcher(this);
        model = JobsModel.getInstanse();
        getJobsFromDb();
    }

    private void getJobsFromDb(){
        Log.d(TAG, "getJobsFromDb: ");
        getViewState().showProgressBar();
        jobsFetcher.fetchDB();
    }

    public void tryGetJobs(){
        Log.d(TAG, "tryGetJobs: ");
        getViewState().showProgressBar();
        jobsFetcher.refreshData(COUNT_ITEMS_TO_LOAD);
    }

    public void showDetailFragment(JobItem item){
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
        Log.d(TAG, "onRefreshFailed: ");
        // something goes wrong when tried to fetch data
        getViewState().hideProgressBar();
        getViewState().showOnRefreshError();
    }

    @Override
    public void onFetchDataFromServerFinished() {
        Log.d(TAG, "onFetchDataFromServerFinished: ");
        getViewState().hideProgressBar();
        List<JobItem> items = model.getItems();
        getViewState().showJobs(items);
    }

    @Override
    public void onFetchDataFromDbFinished() {
        // JobsFetcher filled JobsModel with cached data
        Log.d(TAG, "onFetchDataFromDbFinished: ");
        List<JobItem> items = model.getItems();
        if (items != null && !items.isEmpty()){
            getViewState().hideProgressBar();
            getViewState().showJobs(items);
        }

        tryGetJobs();
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
        List<JobItem> cachedItems = model.getBatchItems(start, COUNT_ITEMS_TO_LOAD);
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

    public void hideDetailFragment(){
        getViewState().hideDetail();
    }
}
