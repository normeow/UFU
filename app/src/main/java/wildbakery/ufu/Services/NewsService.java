package wildbakery.ufu.Services;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import java.util.List;

import wildbakery.ufu.Model.ApiModels.NewsItem;

/**
 * Created by Tatiana on 24/04/2017.
 */
// get newsitems from database or server and put them into model, notify listener (presenter)
public class NewsService{

    interface CallbacksListener {
        void onResponse(List<NewsItem> items);
        void onFailure();
    }

    private CallbacksListener listener;

    public void fetchDB(){

    }

    // internet connection excp
    public void fetchServer(){

    }

    private class FetchDbTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }
}