package wildbakery.ufu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.jsoup.Connection;

import java.util.ArrayList;
import java.util.List;

import wildbakery.ufu.Fragment.BaseFragment;
import wildbakery.ufu.Fragment.FragmentEvent;
import wildbakery.ufu.Fragment.FragmentJob;
import wildbakery.ufu.Fragment.FragmentNews;
import wildbakery.ufu.Fragment.FragmentSale;

/**
 * Created by Jaison on 23/10/16.
 */


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_ITEMS = 4;
    BaseFragment fragmentNews;
    BaseFragment fragmentJob;
    BaseFragment fragmentSale;
    BaseFragment fragmentEvent;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        fragmentNews = FragmentNews.newInstance();
        fragmentEvent = FragmentEvent.newInstance();
        fragmentJob = FragmentJob.newInstance();
        fragmentSale = FragmentSale.newInstance();
    }
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return fragmentNews;
            case 1:
                return fragmentJob;
            case 2:
                return fragmentSale;
            case 3:
                return fragmentEvent;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}