package wildbakery.ufu.Model.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tatiana on 10/04/2017.
 */

public class QueryModel<T> implements Serializable {

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
    private List<T> items = null;

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

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }


}
