package wildbakery.ufu.DataFetchers;

import android.os.AsyncTask;

import java.sql.SQLException;
import java.util.List;

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
    }

    private CallbacksListener listener;
    private NewsDAO newsDAO;
    private FetchDbTask dbTask;
    private FetchServerTask serverTask;

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

    // todo get data in batches

    // internet connection excp
    public void fetchServer(){
        serverTask = new FetchServerTask();
        serverTask.execute();
    }

    /**
     * call when invoking component is destroying
     */
    public void cancel(){
        // we can cancel only fetching. not when we are filling up the model
        dbTask.cancel(true);
        serverTask.cancel(true);
    }

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
}