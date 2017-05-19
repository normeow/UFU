package wildbakery.ufu.DataFetchers;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import wildbakery.ufu.Constants;
import wildbakery.ufu.Model.ApiModels.JobItem;
import wildbakery.ufu.Model.ApiModels.QueryModel;
import wildbakery.ufu.Model.DAO.JobsDAO;
import wildbakery.ufu.Model.HelperFactory;
import wildbakery.ufu.Model.JobsModel;
import wildbakery.ufu.Model.VuzAPI;
import wildbakery.ufu.Utils.RestClient;

/**
 * Created by Tatiana on 13/05/2017.
 */

public class JobsFetcher {

    private FetcherCallbacksListener listener;
    private JobsDAO jobsDAO;

    private FetchDbTask dbTask;
    private RefreshDataTask refreshDataTask;
    private FetchBatchTask fetchBatchTask;
    
    private JobsModel jobsModel = JobsModel.getInstanse();
    
    public JobsFetcher(FetcherCallbacksListener listener){
        this.listener = listener;
        try {
            jobsDAO = HelperFactory.getHelper().getJobsDAO();
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
    private class FetchDbTask extends AsyncTask<Void, Void, List<JobItem>> {

        @Override
        protected List<JobItem> doInBackground(Void... voids) {
            try {

                List<JobItem> news = jobsDAO.getAllJobs();
                jobsModel.setItems(news);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<JobItem> items) {
            listener.onFetchDataFromDbFinished();
            super.onPostExecute(items);
        }

    }


    /**
     * replace data in database and model with new data. Fetch first N items
     */
    private class RefreshDataTask extends AsyncTask<Integer, Void, List<JobItem>>{
        private Exception e;
        @Override
        protected List<JobItem> doInBackground(final Integer... params) {
            List<JobItem> items = null;

            Map<String, String> queries = new HashMap<>();
            queries.put(Constants.QUERY_PARAMETERS.PARAM_ORDERBY, "id");
            queries.put("limit", Integer.toString(params[0]));

            Call<QueryModel<JobItem>> call = VuzAPI.Factory.newInstance(RestClient.getOkHttpClient(queries)).getJobs();

            try {
                items = call.execute().body().getItems();
                jobsDAO.deleteAllJobs();
                jobsDAO.insertJobs(items);
                synchronized (jobsModel) {
                    jobsModel.setItems(items);
                }
            } catch (Exception e) {
                this.e = e;
                e.printStackTrace();
            }
            return items;
        }


        @Override
        protected void onPostExecute(List<JobItem> items) {
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
    private class FetchBatchTask extends AsyncTask<Integer, Void, List<JobItem>>{

        private int start;
        private Exception e;
        @Override
        protected List<JobItem> doInBackground(final Integer... params) {
            start = jobsModel.getInstanse().getItems().size();
            List<JobItem> items = null;

            Map<String, String> queries = new HashMap<>();
            queries.put(Constants.QUERY_PARAMETERS.PARAM_ORDERBY, "id");
            queries.put(Constants.QUERY_PARAMETERS.PARAM_START, Integer.toString(params[0]));
            queries.put(Constants.QUERY_PARAMETERS.PARAM_LIMIT, Integer.toString(params[1]));

            Call<QueryModel<JobItem>> call = VuzAPI.Factory.newInstance(RestClient.getOkHttpClient(queries)).getJobs();

            try {
                items = call.execute().body().getItems();
                jobsDAO.insertJobs(items);
                synchronized (jobsModel){
                    jobsModel.addItems(items);
                }
            } catch (Exception e) {
                this.e = e;
                e.printStackTrace();
            }

            return items;
        }

        @Override
        protected void onPostExecute(List<JobItem> items) {
            if (e != null){
                listener.onLoadBatchFailed();
            }
            else
                listener.onModelAppended(start);
            super.onPostExecute(items);
        }
    }



}
