package wildbakery.ufu.DataFetchers;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
    private FetchServerTask serverTask;
    private RefreshDataTask refreshDataTask;
    private FetchBatchTask fetchBatchTask;

    private NewsModel newsModel = NewsModel.getInstanse();

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
    // internet connection excp
    public void fetchServer(){
        serverTask = new FetchServerTask();
        serverTask.execute();
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
        serverTask.cancel(true);
    }

    private void cacheData(List<NewsItem> items) throws SQLException {
        // cache image to local storage
        ImageSaver imageSaver = new ImageSaver(App.getContext());
        for (int i = 0; i < items.size(); i++){
            NewsItem item = items.get(i);
            if(item.getImage() != null) {
                String imagePath = item.getImage().getPath();
                String newImagePath = imageSaver.downloadAndSaveImage(Constants.HTTP.IMAGE_URL + imagePath, item.getImage().getName() + ".png");
                item.setImagePath(newImagePath);
            }
        }

    }

    /**
     * place all data from DB to model
     */
    private class FetchDbTask extends AsyncTask<Void, Void, List<NewsItem>>{

        @Override
        protected List<NewsItem> doInBackground(Void... voids) {
            try {
                List<NewsItem> news = newsDAO.getAllNews();
                NewsModel.getInstanse().setItems(news);
            } catch (SQLException e) {
                e.printStackTrace();
                listener.onFailure();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<NewsItem> items) {
            listener.onFetchDataFromDbFinished();
            super.onPostExecute(items);
        }

    }

    private class FetchServerTask extends AsyncTask<Void, Void, List<NewsItem>>{
        @Override
        protected List<NewsItem> doInBackground(Void... voids) {
            // get data from server
            Call<QueryModel<NewsItem>> call = VuzAPI.Factory.getInstance().getNews();
            try {
                List<NewsItem> items = call.execute().body().getItems();

                // cache image to local storage
                ImageSaver imageSaver = new ImageSaver(App.getContext());
                for (int i = 0; i < items.size(); i++){
                    NewsItem item = items.get(i);
                    String imagePath = item.getImage().getPath();
                    String newImagePath = imageSaver.downloadAndSaveImage(Constants.HTTP.IMAGE_URL + imagePath, item.getImage().getName() + ".png");
                    item.setImagePath(newImagePath);
                }

                // put newsItems in DataBase
                newsDAO.insertNews(items);
                // and in model
                NewsModel.getInstanse().setItems(items);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<NewsItem> items) {
            //todo handle exceptions here
            listener.onFetchDataFromServerFinished();
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
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            okhttp3.Request request = chain.request();
                            okhttp3.HttpUrl url = request.url()
                                    .newBuilder()
                                    .addQueryParameter("from", "now")
                                    .addQueryParameter("limit", Integer.toString(params[0]))
                                    .build();

                            request = request.newBuilder()
                                    .url(url)
                                    .build();

                            return chain.proceed(request);
                        }
                    }).build();

            Call<QueryModel<NewsItem>> call = VuzAPI.Factory.newInstance(client).getNews();
            try {
                items = call.execute().body().getItems();
                newsDAO.deleteAllNews();
                cacheData(items);
                // put newsItems in DataBase
                newsDAO.insertNews(items);
                synchronized (newsModel) {
                    Log.d(TAG, "doInBackground: gonna set NewsModel items");
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
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            okhttp3.Request request = chain.request();
                            okhttp3.HttpUrl url = request.url()
                                    .newBuilder()
                                    .addQueryParameter("from", Integer.toString(NewsModel.getInstanse().getItems().get(start - 1).getId()))
                                    .addQueryParameter("limit", Integer.toString(params[0]))
                                    .build();

                            request = request.newBuilder()
                                    .url(url)
                                    .build();

                            return chain.proceed(request);
                        }
                    }).build();

            Call<QueryModel<NewsItem>> call = VuzAPI.Factory.newInstance(client).getNews();
            try {
                items = call.execute().body().getItems();
                cacheData(items);
                newsDAO.insertNews(items);
                synchronized (newsModel){
                    newsModel.addItems(items);
                }
                cacheData(items);
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