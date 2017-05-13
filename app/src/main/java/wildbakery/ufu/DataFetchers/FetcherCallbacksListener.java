package wildbakery.ufu.DataFetchers;

/**
 * Created by Tatiana on 13/05/2017.
 */

public interface FetcherCallbacksListener {
    void onRefreshFailed();
    void onFetchDataFromServerFinished();
    void onFetchDataFromDbFinished();
    void onModelAppended(int start);
    void onLoadBatchFailed();
}
