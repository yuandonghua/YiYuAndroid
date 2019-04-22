package com.yixinhuayuan.yiyu.app.utils.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MffaTablOrPagerAdapter extends FragmentPagerAdapter {

    // 标题
    private String[] titles;
    // 标题对应的页面
    private List<Fragment> fragments;


    public MffaTablOrPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 通过构造器拿到 标题和标题对应的内容
     *
     * @param fm
     * @param fragments
     * @param titles
     */
    public MffaTablOrPagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment = null;
        if (i < fragments.size()) {
            fragment = this.fragments.get(i);
        } else {
            fragment = this.fragments.get(0);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (this.titles != null && this.titles.length > 0) {
            return this.titles[position];
        }
        return super.getPageTitle(position);
    }
}
