package wildbakery.ufu;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import wildbakery.ufu.Fragment.BaseFragment;
import wildbakery.ufu.Fragment.FragmentEvent;
import wildbakery.ufu.Fragment.FragmentJob;
import wildbakery.ufu.Fragment.FragmentNews;
import wildbakery.ufu.Fragment.FragmentSale;


public class MainActivity extends AppCompatActivity {

    final String TAG = "lifecycle1";

    BottomNavigationView bottomNavigationView;

    //This is our viewPager
    private ViewPager viewPager;
    private TextView infoTextView;

    //Fragments

    FragmentNews newsFragment;
    FragmentJob jobFragment;
    FragmentSale saleFragment;
    FragmentEvent eventFragment;
    BaseFragment activeFragment;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Activity создано");

        //Initializing viewPager


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        infoTextView = (TextView) findViewById(R.id.infoTextView);
        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.action_news:
                                infoTextView.setText(R.string.news);
                                viewPager.setCurrentItem(0);


                                break;
                            case R.id.action_job:
                                infoTextView.setText(R.string.job);
                                viewPager.setCurrentItem(1);

                                break;
                            case R.id.action_sale:
                                infoTextView.setText(R.string.sale);
                                viewPager.setCurrentItem(2);

                                break;
                            case R.id.action_event:
                                infoTextView.setText(R.string.event);
                                viewPager.setCurrentItem(3);

                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                switch (position) {
                    case 0:
                        activeFragment = newsFragment;
                        infoTextView.setText(R.string.news);
                        break;
                    case 1:
                        activeFragment = jobFragment;
                        infoTextView.setText(R.string.job);
                        break;
                    case 2:
                        activeFragment = saleFragment;
                        infoTextView.setText(R.string.sale);
                        break;
                    case 3:
                        activeFragment = eventFragment;
                        infoTextView.setText(R.string.event);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        newsFragment = new FragmentNews();
        jobFragment = new FragmentJob();
        saleFragment = new FragmentSale();
        eventFragment = new FragmentEvent();
        adapter.addFragment(newsFragment);
        adapter.addFragment(jobFragment);
        adapter.addFragment(saleFragment);
        adapter.addFragment(eventFragment);

         viewPager.setAdapter(adapter);
        activeFragment = newsFragment;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Activity запущено");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Activity видимо");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Activity приостановлено");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Activity остановлено");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity уничтожено");
    }

    @Override
    public void onBackPressed() {
        if (activeFragment.onBackPressed())
            super.onBackPressed();
    }

}
