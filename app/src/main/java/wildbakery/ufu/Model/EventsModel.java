package wildbakery.ufu.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import wildbakery.ufu.Model.ApiModels.EventItem;

/**
 * Created by Tatiana on 13/05/2017.
 */

public class EventsModel {
    private static final String TAG = "EventsModel";

    private List<EventItem> items = new ArrayList<>();

    private static EventsModel instanse;

    private EventsModel(){}

    public static EventsModel getInstanse() {
        if (instanse == null)
            instanse = new EventsModel();
        return instanse;
    }

    public List<EventItem> getItems() {
        // returns copy of data
        return new ArrayList<>(items);
    }

    /**
     *
     * @param count count of items in batch
     * @return last items in list
     */

    public List<EventItem> getBatchItems(int start, int count){
        if (items.isEmpty())
            return null;
        int end = (start + count) < items.size() ? start + count : items.size();
        Log.d("TEST", "getBatchItems: start = " + start + " end = " + end);
        List<EventItem> subl = items.subList(start, end);
        return subl;
    }

    public void setItems(List<EventItem> items) {
        Log.d(TAG, "setItems");
        this.items = items;
    }

    public void addItems(List<EventItem> items) {
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
