package wildbakery.ufu.DataFetchers;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import wildbakery.ufu.Constants;
import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.Model.ApiModels.QueryModel;
import wildbakery.ufu.Model.DAO.NewsDAO;
import wildbakery.ufu.Model.HelperFactory;
import wildbakery.ufu.Model.NewsModel;
import wildbakery.ufu.Model.VuzAPI;
import wildbakery.ufu.App;
import wildbakery.ufu.Utils.ImageSaver;
import wildbakery.ufu.Utils.RestClient;

/**
 * Created by Tatiana on 24/04/2017.
 */
// get newsitems from database or server and put them into model, notify listener (presenter)
public class NewsFetcher {

    public interface CallbacksListener {
        void onFailure();
        void onFetchDataFromServerFinished();
        void onFetchDataFromDbFinished();
        void onModelAppended(int start);
    }

    private static final String TAG = "NewsFetcher";

    private CallbacksListener listener;
    private NewsDAO newsDAO;
    private FetchDbTask dbTask;
    private RefreshDataTask refreshDataTask;
    private FetchBatchTask fetchBatchTask;
    private VuzAPI vuzAPI;

    private NewsModel newsModel = NewsModel.getInstanse();

    private boolean isDbFetching;

    public NewsFetcher(CallbacksListener listener){
        this.listener = listener;
        try {
            newsDAO = HelperFactory.getHelper().getNewsDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void fetchDB(){
        dbTask = new FetchDbTask();
        dbTask.execute();
    }

    /**
     * replace data in database and model with new data
     * @param count how many items to load
     */
    public void refreshData(int count){
        refreshDataTask = new RefreshDataTask();
        refreshDataTask.execute(count);
    }

    public void fetchBatch(int count){
        fetchBatchTask = new FetchBatchTask();
        fetchBatchTask.execute(count);
    }

    /**
     * call when invoking component is destroying
     */
    public void cancel(){
        // we can cancel only fetching. not when we are filling up the model
        dbTask.cancel(true);
    }

    /**
     * place all data from DB to model
     */
    private class FetchDbTask extends AsyncTask<Void, Void, List<NewsItem>>{

        @Override
        protected List<NewsItem> doInBackground(Void... voids) {
            try {

                List<NewsItem> news = newsDAO.getAllNews();
                newsModel.setItems(news);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<NewsItem> items) {
            listener.onFetchDataFromDbFinished();
            super.onPostExecute(items);
        }

    }


    /**
     * replace data in database and model with new data. Fetch first N items
     */
    private class RefreshDataTask extends AsyncTask<Integer, Void, List<NewsItem>>{
        @Override
        protected List<NewsItem> doInBackground(final Integer... params) {
            List<NewsItem> items = null;

            Map<String, String> queries = new HashMap<>();
            queries.put("from", "now");
            queries.put("limit", Integer.toString(params[0]));

            Call<QueryModel<NewsItem>> call = VuzAPI.Factory.newInstance(RestClient.getOkHttpClient(queries)).getNews();

            try {
                items = call.execute().body().getItems();
                synchronized (newsModel) {
                    Log.d(TAG, "doInBackground: gonna set NewsModel items");
                    newsDAO.deleteAllNews();
                    long t1 = System.nanoTime();
                    //cacheData(items);
                    //cacheDataWithoudDownloadImages(items);
                    newsDAO.insertNews(items);
                    long t2 = System.nanoTime();
                    Log.d(TAG, String.format("doInBackground: time of caching data = %.1fms", (t2 - t1) / 1e6d) );
                    newsModel.setItems(items);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return items;
        }


        @Override
        protected void onPostExecute(List<NewsItem> items) {
            // and in model
            listener.onFetchDataFromServerFinished();
            super.onPostExecute(items);
        }
    }

    /**
     * append model with previous data
     */
    private class FetchBatchTask extends AsyncTask<Integer, Void, List<NewsItem>>{

        private int start;
        @Override
        protected List<NewsItem> doInBackground(final Integer... params) {
            start = NewsModel.getInstanse().getItems().size();
                List<NewsItem> items = null;

                Map<String, String> queries = new HashMap<>();
                queries.put("from", Integer.toString(NewsModel.getInstanse().getItems().get(start - 1).getId()));
                queries.put("limit", Integer.toString(params[0]));

                Call<QueryModel<NewsItem>> call = VuzAPI.Factory.newInstance(RestClient.getOkHttpClient(queries)).getNews();

                try {
                    items = call.execute().body().getItems();
                    long t1 = System.nanoTime();
                    // cacheData(items);
                    //cacheDataWithoudDownloadImages(items);
                    newsDAO.insertNews(items);
                    long t2 = System.nanoTime();
                    Log.d(TAG, String.format("doInBackground: time of caching data = %.1fms", (t2 - t1) / 1e6d) );

                    synchronized (newsModel){
                        newsModel.addItems(items);
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return items;
        }

        @Override
        protected void onPostExecute(List<NewsItem> items) {

            // and in model
            listener.onModelAppended(start);
            super.onPostExecute(items);
        }
    }


}