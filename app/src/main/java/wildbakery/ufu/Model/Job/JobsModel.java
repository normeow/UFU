
package wildbakery.ufu.Model.Job;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import wildbakery.ufu.Model.QueryModel;

public class JobsModel  extends QueryModel implements Serializable {


    @SerializedName("items")
    @Expose
    private List<JobItem> items = null;

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
