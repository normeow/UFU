package wildbakery.ufu.Interfaces;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import wildbakery.ufu.Model.Event.EventModel;
import wildbakery.ufu.Model.Job.JobsModel;
import wildbakery.ufu.Model.News.NewsModel;
import wildbakery.ufu.Model.Sale.SaleModel;

import static wildbakery.ufu.Constants.HTTP.BASE_URL;


/**
 * Created by DIKII PEKAR on 01.12.2016.
 */

public interface APIservice {

    @GET("v1/jobs.json")
    Call<JobsModel> getAllJob();

    @GET("v1/news.json")
    Call<NewsModel> getAllNews();

    @GET("v1/events.json")
    Call<EventModel> getAllEvent();

    @GET("v1/sales.json")
    Call<SaleModel> getAllSale();

    class Factory {
        private  static  APIservice service;
        public static  APIservice getInstance(){
            if (service == null){
                Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                service = retrofit.create(APIservice.class);
                return service;
            }
            else {
                return service;
            }
        }
    }


}