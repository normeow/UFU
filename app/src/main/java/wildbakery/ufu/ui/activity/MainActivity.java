package wildbakery.ufu.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import wildbakery.ufu.Model.HelperFactory;
import wildbakery.ufu.R;
import wildbakery.ufu.ui.fragments.MvpBaseFragment;
import wildbakery.ufu.ui.fragments.MvpEventsFragment;
import wildbakery.ufu.ui.fragments.MvpJobsFragment;
import wildbakery.ufu.ui.fragments.MvpNewsFragment;
import wildbakery.ufu.ui.fragments.MvpSalesFragment;


public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";

    BottomNavigationView bottomNavigationView;

    //This is our viewPager
    private ViewPager viewPager;
    private MvpBaseFragment currentFragment;
    private ViewPagerAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HelperFactory.setHelper(this);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Activity создано");

        //Initializing viewPager

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
                currentFragment = (MvpBaseFragment) adapter.getItem(viewPager.getCurrentItem());
                Log.d("page", "onPageSelected: " + position);
                currentFragment.hideArrowBack();

                setCurrentFragment();
                // current framgent changed
                currentFragment.showArrowBack();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setCurrentFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_refresh:
                currentFragment.refresh();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setCurrentFragment(){
        getSupportActionBar().setTitle(viewPager.getAdapter().getPageTitle(viewPager.getCurrentItem()));
        currentFragment = (MvpBaseFragment) adapter.getItem(viewPager.getCurrentItem());
        Log.d(TAG, "setCurrentFragment: curItem = " + viewPager.getCurrentItem());
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MvpNewsFragment(), getString(R.string.news));
        adapter.addFragment(new MvpJobsFragment(), getString(R.string.job));
        adapter.addFragment(new MvpSalesFragment(), getString(R.string.sale));
        adapter.addFragment(new MvpEventsFragment(), getString(R.string.event));
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
        HelperFactory.releaseHelper();
        super.onDestroy();
        Log.d(TAG, "Activity уничтожено");
    }

    @Override
    public void onBackPressed() {
        currentFragment.onBackPressed();

    }
}
