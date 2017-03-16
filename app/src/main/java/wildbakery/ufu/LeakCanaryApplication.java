package wildbakery.ufu;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by DIKII PEKAR on 09.03.2017.
 */

public class LeakCanaryApplication extends Application {



    private RefWatcher refWatcher;

    @Override public void onCreate() {
        super.onCreate();

       refWatcher =  LeakCanary.install(this);
        // Normal app init code...
    }

    public static RefWatcher getRefWatcher(Context context) {
        LeakCanaryApplication application = (LeakCanaryApplication) context.getApplicationContext();
        return application.refWatcher;
    }
}
