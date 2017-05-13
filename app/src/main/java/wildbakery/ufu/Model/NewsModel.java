package wildbakery.ufu.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import wildbakery.ufu.Model.ApiModels.NewsItem;

/**
 * Created by Tatiana on 24/04/2017.
 */

public class NewsModel {
    private static final String TAG = "NewsModel";

    private List<NewsItem> items = new ArrayList<>();

    private static NewsModel instanse;

    private NewsModel(){}

    public static NewsModel getInstanse() {
        if (instanse == null)
            instanse = new NewsModel();
        return instanse;
    }

    public List<NewsItem> getItems() {
        // returns copy of data
        return new ArrayList<>(items);
    }

    /**
     *
     * @param count count of items in batch
     * @return last items in list
     */

    public List<NewsItem> getBatchItems(int start, int count){
        if (items.isEmpty())
            return null;
        int end = (start + count) < items.size() ? start + count : items.size();
        Log.d("TEST", "getBatchItems: start = " + start + " end = " + end);
        List<NewsItem> subl = items.subList(start, end);
        return subl;
    }

    public void setItems(List<NewsItem> items) {
        Log.d(TAG, "setItems");
        this.items = items;
    }

    public void addItems(List<NewsItem> items) {
        if (items == null)
            return;
        if (this.items == null)
            this.items = new ArrayList<>();
        this.items.addAll(items);
    }

    public void clearModel(){
        Log.d(TAG, "clearModel: ");
        items = new ArrayList<>();
    }
}
