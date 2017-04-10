
package wildbakery.ufu.Model.Job;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class JobsModel implements Serializable {

    @SerializedName("_query_time")
    @Expose
    private double queryTime;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("_count_query_time")
    @Expose
    private double countQueryTime;
    @SerializedName("items")
    @Expose
    private List<JobItem> items = null;





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
    public String getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(String count) {
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
    public List<JobItem> getItems() {
        return items;
    }

    /**
     * 
     * @param items
     *     The items
     */
    public void setItems(List<JobItem> items) {
        this.items = items;
    }

}
