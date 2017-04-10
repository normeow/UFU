
package wildbakery.ufu.Model.Sale;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import wildbakery.ufu.Model.QueryModel;

public class SaleModel extends QueryModel implements Serializable {

    @SerializedName("items")
    @Expose
    private List<SaleItem> items = null;

    public List<SaleItem> getItems() {
        return items;
    }

    public void setItems(List<SaleItem> items) {
        this.items = items;
    }

}
