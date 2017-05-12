package wildbakery.ufu.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wildbakery.ufu.App;
import wildbakery.ufu.Model.ApiModels.NewsItem;
import wildbakery.ufu.Model.ApiModels.QueryModel;
import wildbakery.ufu.Model.HelperFactory;
import wildbakery.ufu.Model.VuzAPI;
import wildbakery.ufu.R;
import wildbakery.ufu.Utils.ImageSaver;
import wildbakery.ufu.ui.fragments.MvpNewsFragment;

/**
 * Created by Tatiana on 26/04/2017.
 */

public class MvpMainAcivity extends AppCompatActivity {

    ImageView imgView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.test_imvage_saver);
        HelperFactory.setHelper(this);
        setContentView(R.layout.detail_activity);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detailFrameLayout, new MvpNewsFragment())
                .commit();

        /*Call<QueryModel<NewsItem>> call = VuzAPI.Factory.newInstance(5, 1).getNews();
        call.enqueue(new Callback<QueryModel<NewsItem>>() {
            @Override
            public void onResponse(Call<QueryModel<NewsItem>> call, Response<QueryModel<NewsItem>> response) {
                List<NewsItem> items = response.body().getItems();
                Log.v(getClass().getCanonicalName(), "yeah");
            }

            @Override
            public void onFailure(Call<QueryModel<NewsItem>> call, Throwable t) {

            }
        });*/


        /*imgView = (ImageView)findViewById(R.id.test_iv);
        MyTask myTask = new MyTask();
        myTask.execute();
*/

    }

    @Override
    protected void onDestroy() {

        HelperFactory.releaseHelper();
        super.onDestroy();
    }

    private class MyTask extends AsyncTask<Void, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(Void... voids) {
            ImageSaver imageSaver = new ImageSaver(App.getContext());
            Bitmap bmp = imageSaver.downloadImage("http://www.android-examples.com/wp-content/uploads/2016/01/bitmap-image.jpg");
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //imgView.setImageBitmap(bitmap);
            String imgname = "my_image.png";
            ImageSaver imageSaver = new ImageSaver(App.getContext());
            String path = imageSaver.saveImage(bitmap, imgname);
            //Bitmap bmp = loadImageBitmap(App.getContext(), imgname);
            Picasso.with(App.getContext()).load(new File(path)).into(imgView);
            super.onPostExecute(bitmap);
        }
    }

    public Bitmap loadImageBitmap(Context context, String imageName) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream    = context.openFileInput(imageName);
            bitmap      = BitmapFactory.decodeStream(fiStream);
            fiStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }
}
