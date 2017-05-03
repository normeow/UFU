package wildbakery.ufu.DataFetchers;

import android.os.AsyncTask;

import java.sql.SQLException;
import java.util.List;

import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.Model.DAO.NewsDAO;
import wildbakery.ufu.Model.HelperFactory;
import wildbakery.ufu.Model.NewsModel;

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

    public NewsFetcher(CallbacksListener listener){
        this.listener = listener;
        try {
            newsDAO = HelperFactory.getHelper().getNewsDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fetchDB(){
        FetchDbTask task = new FetchDbTask();
        task.execute();
    }

    // internet connection excp
    public void fetchServer(){

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

    private class FetchServerTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            // get data from server
            // cache image to local storage
            // put newsItems in DataBase
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}