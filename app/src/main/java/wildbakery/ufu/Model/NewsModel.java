package wildbakery.ufu.Model;

import java.util.List;

import wildbakery.ufu.Model.Models.NewsItem;

/**
 * Created by Tatiana on 24/04/2017.
 */

public class NewsModel {
    private List<NewsItem> items;

    private static NewsModel instanse;

    private NewsModel(){}

    public static NewsModel getInstanse() {
        if (instanse == null)
            instanse = new NewsModel();
        return instanse;
    }

    public List<NewsItem> getItems() {
        return items;
    }

    public void setItems(List<NewsItem> items) {
        this.items = items;
    }
}
