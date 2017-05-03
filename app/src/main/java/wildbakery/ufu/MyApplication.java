package wildbakery.ufu;


import android.app.Application;

import wildbakery.ufu.Model.HelperFactory;

/**
 * Created by Tatiana on 28/04/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        //todo will it called in release?
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
