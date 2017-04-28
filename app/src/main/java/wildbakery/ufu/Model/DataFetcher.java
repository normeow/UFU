package wildbakery.ufu.Model;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import wildbakery.ufu.Model.ApiModels.EventItem;
import wildbakery.ufu.Model.ApiModels.JobItem;
import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.Model.ApiModels.QueryModel;
import wildbakery.ufu.Model.ApiModels.SaleItem;

/**
 * Server interaction logic
 */
@Deprecated
public class  DataFetcher{
    private static DataFetcher instance;

    public DataFetcher(){}



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


    private class FetchJobsTask extends AsyncTask<Void, Void, List<JobItem>>{

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
