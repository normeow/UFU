package wildbakery.ufu.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.List;

import wildbakery.ufu.Model.ApiModels.NewsItem;

/**
 * Created by Tatiana on 24/04/2017.
 */
// get newsitems from database or server and put them into model, notify listener (presenter)
public class NewsService extends IntentService {

    interface CallbacksListener {
        void onResponse(List<NewsItem> items);

        void onFailure();

    }

    private CallbacksListener listener;

    public NewsService(){
        super("NewsService");
    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}