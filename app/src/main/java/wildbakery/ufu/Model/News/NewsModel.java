
package wildbakery.ufu.Model.News;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NewsModel implements Serializable {

    @SerializedName("_query_time")
    @Expose
    private double queryTime;
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("_count_query_time")
    @Expose
    private double countQueryTime;
    @SerializedName("items")
    @Expose
    private List<NewsItem> items = null;

    public double getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(double queryTime) {
        this.queryTime = queryTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getCountQueryTime() {
        return countQueryTime;
    }

    public void setCountQueryTime(double countQueryTime) {
        this.countQueryTime = countQueryTime;
    }

    public List<NewsItem> getItems() {
        return items;
    }

    public void setItems(List<NewsItem> items) {
        this.items = items;
    }

}
