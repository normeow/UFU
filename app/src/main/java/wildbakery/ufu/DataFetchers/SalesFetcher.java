package wildbakery.ufu.DataFetchers;

import android.os.AsyncTask;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import wildbakery.ufu.Constants;
import wildbakery.ufu.Model.ApiModels.SaleItem;
import wildbakery.ufu.Model.ApiModels.QueryModel;
import wildbakery.ufu.Model.DAO.SalesDAO;
import wildbakery.ufu.Model.HelperFactory;
import wildbakery.ufu.Model.SalesModel;
import wildbakery.ufu.Model.VuzAPI;
import wildbakery.ufu.Utils.RestClient;

/**
 * Created by Tatiana on 13/05/2017.
 */

public class SalesFetcher {

    private FetcherCallbacksListener listener;
    private SalesDAO salesDAO;

    private FetchDbTask dbTask;
    private RefreshDataTask refreshDataTask;
    private FetchBatchTask fetchBatchTask;
    
    private SalesModel salesModel = SalesModel.getInstanse();
    
    public SalesFetcher(FetcherCallbacksListener listener){
        this.listener = listener;
        try {
            salesDAO = HelperFactory.getHelper().getSalesDAO();
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
    private class FetchDbTask extends AsyncTask<Void, Void, List<SaleItem>> {

        @Override
        protected List<SaleItem> doInBackground(Void... voids) {
            try {

                List<SaleItem> news = salesDAO.getAllSales();
                salesModel.setItems(news);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<SaleItem> items) {
            listener.onFetchDataFromDbFinished();
            super.onPostExecute(items);
        }

    }


    /**
     * replace data in database and model with new data. Fetch first N items
     */
    private class RefreshDataTask extends AsyncTask<Integer, Void, List<SaleItem>>{
        private Exception e;
        @Override
        protected List<SaleItem> doInBackground(final Integer... params) {
            List<SaleItem> items = null;

            Map<String, String> queries = new HashMap<>();
            queries.put(Constants.QUERY_PARAMETERS.PARAM_ORDERBY, "id");
            queries.put("limit", Integer.toString(params[0]));

            Call<QueryModel<SaleItem>> call = VuzAPI.Factory.newInstance(RestClient.getOkHttpClient(queries)).getSales();

            try {
                items = call.execute().body().getItems();
                salesDAO.deleteAllSales();
                salesDAO.insertSales(items);
                synchronized (salesModel) {
                    salesModel.setItems(items);
                }
            } catch (Exception e) {
                this.e = e;
                e.printStackTrace();
            }
            return items;
        }


        @Override
        protected void onPostExecute(List<SaleItem> items) {
            // and in model
            if (e != null) {
                listener.onRefreshFailed();
            }
            else
                listener.onFetchDataFromServerFinished();
            super.onPostExecute(items);
        }
    }

    /**
     * append model with previous data
     */
    private class FetchBatchTask extends AsyncTask<Integer, Void, List<SaleItem>>{

        private int start;
        private Exception e;
        @Override
        protected List<SaleItem> doInBackground(final Integer... params) {
            start = salesModel.getInstanse().getItems().size();
            List<SaleItem> items = null;

            Map<String, String> queries = new HashMap<>();
            queries.put(Constants.QUERY_PARAMETERS.PARAM_ORDERBY, "id");
            queries.put(Constants.QUERY_PARAMETERS.PARAM_START, Integer.toString(params[0]));
            queries.put(Constants.QUERY_PARAMETERS.PARAM_LIMIT, Integer.toString(params[1]));

            Call<QueryModel<SaleItem>> call = VuzAPI.Factory.newInstance(RestClient.getOkHttpClient(queries)).getSales();

            try {
                items = call.execute().body().getItems();
                salesDAO.insertSales(items);
                synchronized (salesModel){
                    salesModel.addItems(items);
                }
            } catch (Exception e) {
                this.e = e;
                e.printStackTrace();
            }

            return items;
        }

        @Override
        protected void onPostExecute(List<SaleItem> items) {
            if (e != null){
                listener.onLoadBatchFailed();
            }
            else
                listener.onModelAppended(start);
            super.onPostExecute(items);
        }
    }



}
