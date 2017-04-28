package wildbakery.ufu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import wildbakery.ufu.Fragments.BaseFragmentPage;
import wildbakery.ufu.Fragments.FragmentEvent;
import wildbakery.ufu.Fragments.FragmentJob;
import wildbakery.ufu.Fragments.FragmentNews;
import wildbakery.ufu.Fragments.FragmentSale;


public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";

    BottomNavigationView bottomNavigationView;

    //This is our viewPager
    private ViewPager viewPager;
    private BaseFragmentPage currentFragment;
    private ViewPagerAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Activity создано");

        //Initializing viewPager

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.action_news:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_job:
                                viewPager.setCurrentItem(1);

                                break;
                            case R.id.action_sale:
                                viewPager.setCurrentItem(2);

                                break;
                            case R.id.action_event:
                                viewPager.setCurrentItem(3);
                                break;

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
                currentFragment = (BaseFragmentPage) adapter.getItem(viewPager.getCurrentItem());
                Log.d("page", "onPageSelected: " + position);
                setCurrentFragment();
                toolbar.setTitle(viewPager.getAdapter().getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setCurrentFragment();
    }

    private void setCurrentFragment(){
        currentFragment = (BaseFragmentPage) adapter.getItem(viewPager.getCurrentItem());
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(FragmentNews.newInstance(), getString(R.string.news));
        adapter.addFragment(FragmentJob.newInstance(), getString(R.string.job));
        adapter.addFragment(FragmentSale.newInstance(), getString(R.string.sale));
        adapter.addFragment(FragmentEvent.newInstance(), getString(R.string.event));
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
