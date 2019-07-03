package com.yixinhuayuan.yiyu.app.utils.adapter.in_home;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 首页用来分类作品的顶部导航栏的适配器
 */
public class HomeWorksClassifyAdapter extends FragmentPagerAdapter {
    // ViewPager要展示的作品分类Fragment
    private ArrayList<Fragment> classify;
    // TabLayout的标题
    private String[] tabTitles;

    public HomeWorksClassifyAdapter(FragmentManager fm, ArrayList<Fragment> classify, String[] tabTitles) {
        super(fm);
        this.classify = classify;
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        if (classify != null && i < classify.size()) {
            fragment = classify.get(i);
        } else {
            fragment = classify.get(0);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        if (classify != null && classify.size() > 0) {
            return classify.size();
        }
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (tabTitles != null && tabTitles.length > 0) {
            return tabTitles[position];
        }
        return super.getPageTitle(position);
    }
/* // ViewPager要展示的作品分类Fragment
    private ArrayList<Fragment> classify;
    // TabLayout的标题
    private String[] tabTitles;

    public HomeWorksClassifyAdapter(FragmentManager fm, ArrayList<Fragment> classify, String[] tabTitles) {
        super(fm);
        this.classify = classify;
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (classify != null) {
            if (position < classify.size()) {
                fragment = classify.get(position);
            } else {
                fragment = classify.get(0);
            }
        }
        return fragment;
    }

    @Override
    public int getCount() {
        if (classify != null) {
            return classify.size();
        }
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (tabTitles != null && tabTitles.length > 0) {
            return tabTitles[position] + "";
        }
        return super.getPageTitle(position);
    }*/
}