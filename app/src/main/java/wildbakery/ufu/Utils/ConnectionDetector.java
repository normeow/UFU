package wildbakery.ufu.Utils;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import wildbakery.ufu.App;

/**
 * Created by DIKII PEKAR on 15.11.2016.
 */

public class ConnectionDetector {

    public static boolean isConnected(){
        ConnectivityManager connectivity =(ConnectivityManager)
                App.getContext().getSystemService(Service.CONNECTIVITY_SERVICE);
        if(connectivity !=null){
            NetworkInfo info = connectivity.getActiveNetworkInfo();
        if(info !=null){
            if(info.getState()==NetworkInfo.State.CONNECTED){
                return  true;
            }
        }

        }
        return  false;
    }
}
