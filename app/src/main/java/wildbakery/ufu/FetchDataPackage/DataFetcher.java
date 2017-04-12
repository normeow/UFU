package wildbakery.ufu.FetchDataPackage;

import android.os.AsyncTask;
import android.provider.ContactsContract;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import wildbakery.ufu.Models.EventItem;
import wildbakery.ufu.Models.JobItem;
import wildbakery.ufu.Models.NewsItem;
import wildbakery.ufu.Models.QueryModel;
import wildbakery.ufu.Models.SaleItem;

/**
 * Server interaction logic
 */
public class DataFetcher {

    private final String TAG = "DataFetcher";
    private static DataFetcher instance;

    public DataFetcher(){ }

    public static DataFetcher getInstance() {
        if (instance == null)
            instance = new DataFetcher();
        return instance;
    }

    public List<NewsItem> getNews() throws ExecutionException, InterruptedException {
        FetchNewsTask task = new FetchNewsTask();
        task.execute();
        return task.get();
    }

    public List<EventItem> getEvents() throws ExecutionException, InterruptedException {
        FetchEventsTask task = new FetchEventsTask();
        task.execute();
        return task.get();
    }

    public List<SaleItem> getSales() throws ExecutionException, InterruptedException {
        FetchSalesTask task = new FetchSalesTask();
        task.execute();
        return task.get();
    }

    public List<JobItem> getJobs() throws ExecutionException, InterruptedException {
        FetchJobsTask task = new FetchJobsTask();
        task.execute();
        return task.get();
    }

    private static class FetchEventsTask extends AsyncTask<Void, Void, List<EventItem>> {


        @Override
        protected List<EventItem> doInBackground(Void... voids) {

            Call<QueryModel<EventItem>> call = VuzAPI.Factory.getInstance().getEvents();
            try {
                return  call.execute().body().getItems();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    private static class FetchJobsTask extends AsyncTask<Void, Void, List<JobItem>>{

        private final String TAG = "FetchEventsTask";

        @Override
        protected List<JobItem> doInBackground(Void... voids) {

            Call<QueryModel<JobItem>> call = VuzAPI.Factory.getInstance().getJobs();
            try {
                return  call.execute().body().getItems();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    private class FetchNewsTask extends AsyncTask<Void, Void, List<NewsItem>>{

        private final String TAG = "FetchNewsTask";

        @Override
        protected List<NewsItem> doInBackground(Void... voids) {

            Call<QueryModel<NewsItem>> call = VuzAPI.Factory.getInstance().getNews();
            try {
                return  call.execute().body().getItems();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    private class FetchSalesTask extends AsyncTask<Void, Void, List<SaleItem>>{

        private final String TAG = "FetchEventsTask";

        @Override
        protected List<SaleItem> doInBackground(Void... voids) {

            Call<QueryModel<SaleItem>> call = VuzAPI.Factory.getInstance().getSales();
            try {
                return  call.execute().body().getItems();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
