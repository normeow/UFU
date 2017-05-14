package wildbakery.ufu.DataFetchers;

import android.os.AsyncTask;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import wildbakery.ufu.Constants;
import wildbakery.ufu.Model.ApiModels.QueryModel;
import wildbakery.ufu.Model.ApiModels.EventItem;
import wildbakery.ufu.Model.DAO.EventsDAO;
import wildbakery.ufu.Model.HelperFactory;
import wildbakery.ufu.Model.EventsModel;
import wildbakery.ufu.Model.VuzAPI;
import wildbakery.ufu.Utils.RestClient;

/**
 * Created by Tatiana on 13/05/2017.
 */

public class EventsFetcher {

    private FetcherCallbacksListener listener;
    private EventsDAO eventsDAO;

    private FetchDbTask dbTask;
    private RefreshDataTask refreshDataTask;
    private FetchBatchTask fetchBatchTask;
    
    private EventsModel eventsModel = EventsModel.getInstanse();
    
    public EventsFetcher(FetcherCallbacksListener listener){
        this.listener = listener;
        try {
            eventsDAO = HelperFactory.getHelper().getEventsDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void fetchDB(){
        dbTask = new FetchDbTask();
        dbTask.execute();
        
    }
    
    public void refreshData(int count){
        refreshDataTask = new RefreshDataTask();
        refreshDataTask.execute(count);
    }
    
    public void fetchBatch(int start, int limit){
        fetchBatchTask = new FetchBatchTask();
        fetchBatchTask.execute(start, limit);
    }

    /**
     * call when invoking component is destroying
     */
    public void cancel(){
        if (dbTask != null)
            dbTask.cancel(true);
        if (refreshDataTask != null)
            refreshDataTask.cancel(true);
        if(fetchBatchTask != null)
            fetchBatchTask.cancel(true);
    }

    /**
     * place all data from DB to model
     */
    private class FetchDbTask extends AsyncTask<Void, Void, List<EventItem>> {

        @Override
        protected List<EventItem> doInBackground(Void... voids) {
            try {

                List<EventItem> news = eventsDAO.getAllEvents();
                eventsModel.setItems(news);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<EventItem> items) {
            listener.onFetchDataFromDbFinished();
            super.onPostExecute(items);
        }

    }


    /**
     * replace data in database and model with new data. Fetch first N items
     */
    private class RefreshDataTask extends AsyncTask<Integer, Void, List<EventItem>>{
        private Exception e;
        @Override
        protected List<EventItem> doInBackground(final Integer... params) {
            List<EventItem> items = null;

            Map<String, String> queries = new HashMap<>();
            queries.put(Constants.QUERY_PARAMETERS.PARAM_ORDERBY, "id");
            queries.put("limit", Integer.toString(params[0]));

            Call<QueryModel<EventItem>> call = VuzAPI.Factory.newInstance(RestClient.getOkHttpClient(queries)).getEvents();

            try {
                items = call.execute().body().getItems();
                eventsDAO.deleteAllEvents();
                eventsDAO.insertEvents(items);
                synchronized (eventsModel) {
                    eventsModel.setItems(items);
                }
            } catch (Exception e) {
                this.e = e;
                e.printStackTrace();
            }
            return items;
        }


        @Override
        protected void onPostExecute(List<EventItem> items) {
            // and in model
            if (e != null) {
                listener.onRefreshFailed();
                return;
            }
            listener.onFetchDataFromServerFinished();
            super.onPostExecute(items);
        }
    }

    /**
     * append model with previous data
     */
    private class FetchBatchTask extends AsyncTask<Integer, Void, List<EventItem>>{

        private int start;
        private Exception e;
        @Override
        protected List<EventItem> doInBackground(final Integer... params) {
            start = eventsModel.getInstanse().getItems().size();
            List<EventItem> items = null;

            Map<String, String> queries = new HashMap<>();
            queries.put(Constants.QUERY_PARAMETERS.PARAM_ORDERBY, "id");
            queries.put(Constants.QUERY_PARAMETERS.PARAM_START, Integer.toString(params[0]));
            queries.put(Constants.QUERY_PARAMETERS.PARAM_LIMIT, Integer.toString(params[1]));

            Call<QueryModel<EventItem>> call = VuzAPI.Factory.newInstance(RestClient.getOkHttpClient(queries)).getEvents();

            try {
                items = call.execute().body().getItems();
                eventsDAO.insertEvents(items);
                synchronized (eventsModel){
                    eventsModel.addItems(items);
                }
            } catch (Exception e) {
                this.e = e;
                e.printStackTrace();
            }

            return items;
        }

        @Override
        protected void onPostExecute(List<EventItem> items) {
            if (e != null){
                listener.onLoadBatchFailed();
                return;
            }
            listener.onModelAppended(start);
            super.onPostExecute(items);
        }
    }



}
