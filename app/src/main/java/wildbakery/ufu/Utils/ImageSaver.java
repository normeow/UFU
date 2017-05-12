package wildbakery.ufu.Utils;

/**
 * Created by Tatiana on 03/05/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class ImageSaver{
    private Context context;

    public ImageSaver(Context context){
        this.context = context;
    }

    /**
     * synchronously download image
     * @param url image url
     * @return image
     */
    public Bitmap downloadImage(String url){
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new URL(url).openStream();   // Download Image from URL
            bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
            inputStream.close();
        } catch (Exception e) {
            Log.d(getClass().getCanonicalName(), "can't download image");
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * save bitmap to internal storage
     * @param image
     * @return path to the image
     */
    public String saveImage(Bitmap image, String imageName){
        //todo replace if exists flag
        FileOutputStream foStream;
        try {
            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.JPEG, 100, foStream);
            foStream.flush();
            foStream.close();
        } catch (Exception e) {
            Log.d(getClass().getCanonicalName(), "can't save image");
            e.printStackTrace();
        }
        return context.getFileStreamPath(imageName).getAbsolutePath();
    }

    /**
     * synchronously download and save image from url to internal storage
     * @param imageUrl
     * @return path to image
     */
    public String downloadAndSaveImage(String imageUrl, String imageName){
        return saveImage(downloadImage(imageUrl), imageName);
    }
}
