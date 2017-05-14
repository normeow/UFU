package wildbakery.ufu.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

/**
 * Created by Tatiana on 14/05/2017.
 */

public class VerticalGradient implements Transformation {

    public VerticalGradient(){}

    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(
                source,
                0, 0,
                source.getWidth(),
                source.getHeight());


        Shader shader = new LinearGradient(0, 0, 0, height*2, Color.TRANSPARENT, Color.BLACK, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(new RectF(0, 0, width, height), paint);

        if (bitmap != source) {
            source.recycle();
        }
        return bitmap;
    }

    @Override
    public String key() {
        return "gradient";
    }
}
