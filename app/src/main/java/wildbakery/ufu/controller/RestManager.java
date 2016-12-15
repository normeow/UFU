package wildbakery.ufu.controller;

import android.app.job.JobService;
import android.provider.SyncStateContract;
import static wildbakery.ufu.Constants.HTTP.BASE_URL;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import wildbakery.ufu.Interfaces.APIservice;

/**
 * Created by DIKII PEKAR on 15.12.2016.
 */

public class RestManager {


        private   APIservice service;
        public  APIservice getIstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(APIservice.class);
                return service;
            } else{
                return service;
            }
        }

}
