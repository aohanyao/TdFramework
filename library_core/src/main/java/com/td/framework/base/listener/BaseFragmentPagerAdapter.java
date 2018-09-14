package com.td.framework.base.listener;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by jc on 2016/12/30 0030.
 * <p>Gihub  </p>
 * <p>所有ViewPager的适配器</p>
 */

public class BaseFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private String[] mTitles;

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> mFragments, String[] mTitles) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles != null && mTitles.length > position ? mTitles[position] : "";
    }
}
