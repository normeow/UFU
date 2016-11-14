package com.example.dikiipekar.work;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterViewAnimator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;

import static com.example.dikiipekar.work.R.id.action_job;

public class news extends AppCompatActivity {

    private TextView infoTextView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);

        infoTextView = (TextView) findViewById(R.id.infoTextView);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.action_news) {
                    infoTextView.setText(R.string.news);
                } else if (item.getItemId() == R.id.action_job) {
                    infoTextView.setText(R.string.job);
                } else if (item.getItemId() == R.id.action_stock) {
                    infoTextView.setText(R.string.stock);
                }

                return false;
            }
        });
    }
}
