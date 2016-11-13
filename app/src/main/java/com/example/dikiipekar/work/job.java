package com.example.dikiipekar.work;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import static com.example.dikiipekar.work.R.id.action_job;

public class job extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job);
/*
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_news:
                                Intent i1 = new Intent(job.this, news.class);
                                startActivity(i1);

                                break;
                            case action_job:
                                Intent i2 = new Intent(job.this, job.class);
                                startActivity(i2);

                                break;
                            case R.id.action_stock:
                                Intent i3 = new Intent(job.this, stock.class);
                                startActivity(i3);

                                break;
                        }
                        return false;
                    }
                });*/




    }
}
