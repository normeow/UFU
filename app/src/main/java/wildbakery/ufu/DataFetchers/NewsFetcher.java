package wildbakery.ufu.DataFetchers;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.Model.ApiModels.QueryModel;
import wildbakery.ufu.Model.DAO.NewsDAO;
import wildbakery.ufu.Model.HelperFactory;
import wildbakery.ufu.Model.NewsModel;
import wildbakery.ufu.Model.VuzAPI;
import wildbakery.ufu.Utils.RestClient;

/**
 * Created by Tatiana on 24/04/2017.
 */
// get newsitems from database or server and put them into model, notify listener (presenter)
public class NewsFetcher{


    private static final String TAG = "NewsFetcher";

    private FetcherCallbacksListener listener;
    private NewsDAO newsDAO;

    private FetchDbTask dbTask;
    private RefreshDataTask refreshDataTask;
    private FetchBatchTask fetchBatchTask;

    private NewsModel newsModel = NewsModel.getInstanse();

    public NewsFetcher(FetcherCallbacksListener listener){
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
        if (dbTask != null)
            dbTask.cancel(true);
        if (refreshDataTask != null)
            refreshDataTask.cancel(true);
        if(fetchBatchTask != null)
            fetchBatchTask.cancel(true);
        Log.d(TAG, "cancel: everything cancelled");
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
        private Exception e;
        @Override
        protected List<NewsItem> doInBackground(final Integer... params) {
            List<NewsItem> items = null;

            Map<String, String> queries = new HashMap<>();
            queries.put("from", "now");
            queries.put("limit", Integer.toString(params[0]));

            Call<QueryModel<NewsItem>> call = VuzAPI.Factory.newInstance(RestClient.getOkHttpClient(queries)).getNews();

            try {
                items = call.execute().body().getItems();Log.d(TAG, "doInBackground: gonna set NewsModel items");
                newsDAO.deleteAllNews();
                newsDAO.insertNews(items);
                synchronized (newsModel) {
                     newsModel.setItems(items);
                }
            } catch (Exception e) {
                this.e = e;
                e.printStackTrace();
            }
            return items;
        }


        @Override
        protected void onPostExecute(List<NewsItem> items) {
            // and in model
            if (e != null) {
                listener.onRefreshFailed();
                return;
            }
            listener.onFetchDataFromServerFinished();
            super.onPostExecute(items);
        }
    }

    /**
     * append model with previous data
     */
    private class FetchBatchTask extends AsyncTask<Integer, Void, List<NewsItem>>{

        private int start;
        private Exception e;
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
                    newsDAO.insertNews(items);
                    synchronized (newsModel){
                        newsModel.addItems(items);
                    }
            } catch (Exception e) {
                this.e = e;
                e.printStackTrace();
            }

            return items;
        }

        @Override
        protected void onPostExecute(List<NewsItem> items) {
            if (e != null){
                listener.onLoadBatchFailed();
                return;
            }
            listener.onModelAppended(start);
            super.onPostExecute(items);
        }
    }


}