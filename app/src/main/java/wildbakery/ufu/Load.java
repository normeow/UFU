package wildbakery.ufu;

/**
 * Created by DIKII PEKAR on 15.11.2016.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

import static wildbakery.ufu.R.id.imageView;


public class Load extends AppCompatActivity {

    final String TAG = "lifecycle";

    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load);
        Log.d(TAG,"Activity создано");
        cd = new ConnectionDetector(this);






        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.load_picture);


            }
        },2 * 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(cd.isConnected()){
                    Toast.makeText(Load.this, "Connected", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(Load.this, "No Connected", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Load.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 4 * 1000);

    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"Activity запущено");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"Activity видимо");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Activity удалено");
    }
}
