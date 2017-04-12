package wildbakery.ufu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import wildbakery.ufu.Fragments.FragmentPage;
import wildbakery.ufu.Fragments.FragmentEvent;
import wildbakery.ufu.Fragments.FragmentJob;
import wildbakery.ufu.Fragments.FragmentNews;
import wildbakery.ufu.Fragments.FragmentSale;

/**
 * Created by Jaison on 23/10/16.
 */


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_ITEMS = 4;
    FragmentPage fragmentNews;
    FragmentPage fragmentJob;
    FragmentPage fragmentSale;
    FragmentPage fragmentEvent;

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