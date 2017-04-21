package wildbakery.ufu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import wildbakery.ufu.Fragments.BaseFragmentPage;
import wildbakery.ufu.Fragments.FragmentEvent;
import wildbakery.ufu.Fragments.FragmentJob;
import wildbakery.ufu.Fragments.FragmentNews;
import wildbakery.ufu.Fragments.FragmentSale;

/**
 * Created by Jaison on 23/10/16.
 */


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void addFragment(Fragment fragment, String title){
        fragments.add(fragment);
        titles.add(title);
    }


}