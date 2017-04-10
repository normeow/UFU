
package wildbakery.ufu.Model.Event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import wildbakery.ufu.Model.QueryModel;

public class EventModel extends QueryModel implements Serializable {

    @SerializedName("items")
    @Expose
    private List<EventItem> items = null;

    public List<EventItem> getItems() {
        return items;
    }

    public void setItems(List<EventItem> items) {
        this.items = items;
    }

}
