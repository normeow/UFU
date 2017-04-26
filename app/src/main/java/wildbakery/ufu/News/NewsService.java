package wildbakery.ufu.News;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

import wildbakery.ufu.Models.NewsItem;

/**
 * Created by Tatiana on 24/04/2017.
 */
// get newsitems from database or server and put them into model, notify listener (presenter)
public class NewsService extends Service {
    interface CallbacksListener{
        void onResponse(List<NewsItem> items);
        void onFailure();

    }

    private CallbacksListener listener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
