package wildbakery.ufu.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wildbakery.ufu.Interfaces.APIservice;
import wildbakery.ufu.Model.Job.JobsModel;
import wildbakery.ufu.Model.News.Image;
import wildbakery.ufu.Model.News.NewsModel;
import wildbakery.ufu.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNews extends Fragment {


    final String TAG = "lifecycle";


    public FragmentNews() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Activity создано");

        View view = inflater.inflate(R.layout.fragment_news, container, false);


        return view;
    }

}