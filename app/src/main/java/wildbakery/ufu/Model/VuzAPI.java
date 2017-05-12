package wildbakery.ufu.Model;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import wildbakery.ufu.Model.ApiModels.EventItem;
import wildbakery.ufu.Model.ApiModels.JobItem;
import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.Model.ApiModels.QueryModel;
import wildbakery.ufu.Model.ApiModels.SaleItem;

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

        @Deprecated
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

        public static VuzAPI newInstance(okhttp3.OkHttpClient client){
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            return retrofit.create(VuzAPI.class);
        }
    }

}