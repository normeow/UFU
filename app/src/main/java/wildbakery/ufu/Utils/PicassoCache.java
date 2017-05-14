package wildbakery.ufu.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.IOException;

import wildbakery.ufu.App;
import wildbakery.ufu.Constants;
import wildbakery.ufu.Model.ApiModels.Image;
import wildbakery.ufu.R;

/**
 * Created by Tatiana on 12/05/2017.
 */

public class PicassoCache {

    public static void loadImage(final String imagePath, final ImageView imageView){
        Log.d("TAG", "path = " + imagePath);
        final String path = Constants.HTTP.IMAGE_URL + imagePath;
        Picasso.with(imageView.getContext())
                .load(path)
                .fit()
                .centerCrop()
                .transform(new VerticalGradient())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(imageView.getContext())
                                .load(path)
                                .fit()
                                .centerCrop()
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .into(imageView);
                    }
                });
    }

    public static void loadImage(Image image, final ImageView imageView){
        if (image != null)
            loadImage(image.getPath(), imageView);
        else{
            Picasso.with(imageView.getContext())
                    .load(R.drawable.logo)
                    .transform(new VerticalGradient())
                    .fit()
                    .centerInside()
                    .into(imageView);
        }
    }

}
