package wildbakery.ufu.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import wildbakery.ufu.Model.ApiModels.JobItem;

/**
 * Created by Tatiana on 13/05/2017.
 */

public class JobsModel {
    private static final String TAG = "JobsModel";

    private List<JobItem> items = new ArrayList<>();

    private static JobsModel instanse;

    private JobsModel(){}

    public static JobsModel getInstanse() {
        if (instanse == null)
            instanse = new JobsModel();
        return instanse;
    }

    public List<JobItem> getItems() {
        // returns copy of data
        return new ArrayList<>(items);
    }

    /**
     *
     * @param count count of items in batch
     * @return last items in list
     */

    public List<JobItem> getBatchItems(int start, int count){
        if (items.isEmpty())
            return null;
        int end = (start + count) < items.size() ? start + count : items.size();
        if (end < start)
            start = end;

        Log.d("TEST", "getBatchItems: start = " + start + " end = " + end);
        List<JobItem> subl = items.subList(start, end);
        return subl;
    }

    public void setItems(List<JobItem> items) {
        Log.d(TAG, "setItems");
        this.items = items;
    }

    public void addItems(List<JobItem> items) {
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
