package wildbakery.ufu.FetchDataPackage;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import wildbakery.ufu.Models.EventItem;
import wildbakery.ufu.Models.JobItem;
import wildbakery.ufu.Models.NewsItem;
import wildbakery.ufu.Models.QueryModel;
import wildbakery.ufu.Models.SaleItem;

import static wildbakery.ufu.Constants.HTTP.BASE_URL;


/**
 * Created by DIKII PEKAR on 01.12.2016.
 */

public interface VuzAPI {

    @GET("v1/jobs.json")
    Call<QueryModel<JobItem>> getJobs();

    @GET("v1/news.json")
    Call<QueryModel<NewsItem>> getNews();

    @GET("v1/events.json")
    Call<QueryModel<EventItem>> getEvents();

    @GET("v1/sales.json")
    Call<QueryModel<SaleItem>> getSales();

    class Factory {
        private  static VuzAPI service;
        public static VuzAPI getInstance(){
            if (service == null){
                Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                service = retrofit.create(VuzAPI.class);
                return service;
            }
            else {
                return service;
            }
        }
    }


}