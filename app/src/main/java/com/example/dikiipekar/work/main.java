package com.example.dikiipekar.work;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation.OnTabSelectedListener;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.dikiipekar.work.Fragments.Job;
import com.example.dikiipekar.work.Fragments.News;
import com.example.dikiipekar.work.Fragments.Stock;

import static com.example.dikiipekar.work.R.id.fragment_job;
import static com.example.dikiipekar.work.R.id.fragment_news;
import static com.example.dikiipekar.work.R.id.fragment_stock;
import static com.example.dikiipekar.work.R.id.infoTextView;


public class Main extends AppCompatActivity implements OnTabSelectedListener {

       private AHBottomNavigation bottomNavigation;
        private TextView infoTextView;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        infoTextView = (TextView) findViewById(R.id.infoTextView);
        bottomNavigation= (AHBottomNavigation) findViewById(R.id.myBottomNavigation_ID);
        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();

        }

private void createNavItems()
        {
        //CREATE ITEMS
        AHBottomNavigationItem newsItem=new AHBottomNavigationItem("Новости",R.drawable.aa);
        AHBottomNavigationItem jobItem=new AHBottomNavigationItem("Работа",R.drawable.bb);
        AHBottomNavigationItem stockItem=new AHBottomNavigationItem("Акций",R.drawable.cc);

        //ADD THEM to bar
        bottomNavigation.addItem(newsItem);
        bottomNavigation.addItem(jobItem);
        bottomNavigation.addItem(stockItem);

        //set properties

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#2c2c2c"));

                bottomNavigation.setAccentColor(Color.parseColor("#FFFFFF"));
        //set current item
        bottomNavigation.setCurrentItem(0);

        }

@Override

public void onTabSelected(int position, boolean wasSelected) {
        //show fragment
        if (position==0)
        {
        News newsFragment = new News();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id,newsFragment).commit();
                infoTextView.setText(R.string.news);
        }else  if (position==1)
        {
        Job jobFragment=new Job();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id,jobFragment).commit();
                infoTextView.setText(R.string.job);
        }else  if (position==2)
        {
        Stock stockFragment=new Stock();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id,stockFragment).commit();
                infoTextView.setText(R.string.stock);
        }
     }


}
