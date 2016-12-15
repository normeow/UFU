
package wildbakery.ufu.Model.Job;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import wildbakery.ufu.Model.Job.Item;

public class JobsModel {

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
    private List<Item> items = null;

    /**
     * 
     * @return
     *     The queryTime
     */
    public double getQueryTime() {
        return queryTime;
    }

    /**
     * 
     * @param queryTime
     *     The _query_time
     */
    public void setQueryTime(double queryTime) {
        this.queryTime = queryTime;
    }

    /**
     * 
     * @return
     *     The count
     */
    public int getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 
     * @return
     *     The countQueryTime
     */
    public double getCountQueryTime() {
        return countQueryTime;
    }

    /**
     * 
     * @param countQueryTime
     *     The _count_query_time
     */
    public void setCountQueryTime(double countQueryTime) {
        this.countQueryTime = countQueryTime;
    }

    /**
     * 
     * @return
     *     The items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * 
     * @param items
     *     The items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

}
