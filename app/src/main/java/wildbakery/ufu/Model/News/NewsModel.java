
package wildbakery.ufu.Model.News;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import wildbakery.ufu.Model.QueryModel;

public class NewsModel extends QueryModel implements Serializable {

    @SerializedName("items")
    @Expose
    private List<NewsItem> items = null;

    public List<NewsItem> getItems() {
        return items;
    }

    public void setItems(List<NewsItem> items) {
        this.items = items;
    }


}
