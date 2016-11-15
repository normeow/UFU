package com.example.dikiipekar.work;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


public class load extends AppCompatActivity {

    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load);

        cd = new ConnectionDetector(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(cd.isConnected()){
                    Toast.makeText(load.this, "Connected", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(load.this, "No Connected", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(load.this, news.class);
                startActivity(i);
                finish();
            }
        }, 2 * 1000);

    }

}