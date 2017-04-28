package wildbakery.ufu.Model;

import java.util.List;

import wildbakery.ufu.Model.ApiModels.NewsItem;

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

    /**
     *
     * @param count count of items in batch
     * @param lastId if of last item we know, -1 if no items
     * @return
     */
    public List<NewsItem> getBatchItems(int count, int lastId){
        return null;
    }

    private int getPositionById(int id){
        return -1;
    }

    public void setItems(List<NewsItem> items) {
        this.items = items;
    }
}
