package com.example.dikiipekar.work;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation.OnTabSelectedListener;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.dikiipekar.work.Fragments.Job;
import com.example.dikiipekar.work.Fragments.News;
import com.example.dikiipekar.work.Fragments.Stock;




public class Main extends AppCompatActivity implements OnTabSelectedListener {

        AHBottomNavigation bottomNavigation;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

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
        }else  if (position==1)
        {
        Job jobFragment=new Job();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id,jobFragment).commit();
        }else  if (position==2)
        {
        Stock stockFragment=new Stock();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id,stockFragment).commit();
        }
     }
}
