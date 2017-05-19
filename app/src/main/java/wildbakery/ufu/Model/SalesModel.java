package wildbakery.ufu.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import wildbakery.ufu.Model.ApiModels.SaleItem;

/**
 * Created by Tatiana on 13/05/2017.
 */

public class SalesModel {
    private static final String TAG = "SalesModel";

    private List<SaleItem> items = new ArrayList<>();

    private static SalesModel instanse;

    private SalesModel(){}

    public static SalesModel getInstanse() {
        if (instanse == null)
            instanse = new SalesModel();
        return instanse;
    }

    public List<SaleItem> getItems() {
        // returns copy of data
        return new ArrayList<>(items);
    }

    /**
     *
     * @param count count of items in batch
     * @return last items in list
     */

    public List<SaleItem> getBatchItems(int start, int count){
        if (items.isEmpty())
            return null;
        int end = (start + count) < items.size() ? start + count : items.size();
        if (end < start)
            start = end;

        Log.d("TEST", "getBatchItems: start = " + start + " end = " + end);
        List<SaleItem> subl = items.subList(start, end);
        return subl;
    }

    public void setItems(List<SaleItem> items) {
        Log.d(TAG, "setItems");
        this.items = items;
    }

    public void addItems(List<SaleItem> items) {
        if (items == null)
            return;
        if (this.items == null)
            this.items = new ArrayList<>();
        this.items.addAll(items);
    }

    public void clearModel(){
        Log.d(TAG, "clearModel: ");
        items = new ArrayList<>();
    }
}
