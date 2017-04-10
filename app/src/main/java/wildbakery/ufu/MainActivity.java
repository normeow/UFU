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

    final String TAG = "MainActivity";

    BottomNavigationView bottomNavigationView;

    //This is our viewPager
    private ViewPager viewPager;
    private TextView infoTextView;
    private BaseFragment currentFragment;
    private ViewPagerAdapter adapter;
    //Fragments


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
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                currentFragment = (BaseFragment) adapter.getItem(viewPager.getCurrentItem());
                Log.d("page", "onPageSelected: " + position);
                setCurrentFragment();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(viewPager);
        setCurrentFragment();
    }

    private void setCurrentFragment(){
        currentFragment = (BaseFragment) adapter.getItem(viewPager.getCurrentItem());
    }

    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
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

        if (currentFragment.onBackPressed())
            super.onBackPressed();
    }

}
