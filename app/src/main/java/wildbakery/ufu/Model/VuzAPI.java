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
        //now every query can contains different parameters
        private  static VuzAPI service;
        private static final String PARAMETER_LIMIT = "limit";
        private static final String PARAMETER_ORDERBYDESC = "orderbydesc";
        private static final String PARAMETER_START = "start";
        private static final String PARAMETER_TILLNOW = "tillNow";
        private static final String KEY_NEWSWHEN = "newsWhen";


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

        /**
         *
         * @param start
         * @param limit number of items in batch
         * @return
         */
        // todo single client builder class
        public static okhttp3.OkHttpClient getNewsClient(final int start, final int limit){

            okhttp3.OkHttpClient client = new okhttp3.OkHttpClient
                    .Builder()
                    .addInterceptor(new okhttp3.Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            okhttp3.Request request = chain.request();
                            okhttp3.HttpUrl url = request.url()
                                    .newBuilder()
                                    .addQueryParameter(PARAMETER_ORDERBYDESC, KEY_NEWSWHEN)
                                    .addQueryParameter(PARAMETER_LIMIT, Integer.toString(limit))
                                    .addQueryParameter(PARAMETER_START, Integer.toString(start))
                                    .build();

                            request = request.newBuilder()
                                    .url(url)
                                    .build();

                            return chain.proceed(request);
                        }
                    })
                    .build();
            return client;
        }
    }



}