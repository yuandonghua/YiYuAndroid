package com.yixinhuayuan.yiyu.app.utils.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yixinhuayuan.yiyu.R;

import java.util.List;

/**
 * 用来初始化 首页 的界面,由于首页的布局是一个可滑动的布局采用了RecyclerView加载不同布局来实现,这个适配器就是讲适配两个布局在同一个RecyclerView展示出来.
 */
public class InitHomeViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private LayoutInflater inflate;
    // ViewPager要展示的作品分类Fragment
    private List<Fragment> classify;
    // TabLayout的标题
    private String[] tabTitles;
    // Fragment管理器
    private FragmentManager fragmentManager;


    public InitHomeViewAdapter(Context context) {
        this.context = context;
        this.inflate = LayoutInflater.from(this.context);
    }

    public InitHomeViewAdapter(Context context, FragmentManager fragmentManager, List<Fragment> classify, String[] tabTitles) {
        this.context = context;
        this.classify = classify;
        this.tabTitles = tabTitles;
        this.fragmentManager = fragmentManager;
        this.inflate = LayoutInflater.from(this.context);
    }

    /**
     * 根据RecyclerView的id在加载不同的id
     * 此方法个人感觉实在设置RecyclerView的id
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return 1;
            case 1:
                return 2;
            default:
                return super.getItemViewType(position);
        }

    }

    /**
     * 用来设置RecyclerView每个条目的模板
     * 这个适配的加载了两个模板,已达到RecyclerView加载不同布局的效果
     *
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        RecyclerView.ViewHolder holder = null;
        switch (i) {
            case 1:
                View view1 = this.inflate.inflate(R.layout.layout_slideshow_home, viewGroup, false);
                holder = new MyViewHolder1(view1);
                return holder;
            case 2:
                View view2 = this.inflate.inflate(R.layout.layout_works_home, viewGroup, false);
                holder = new MyViewHolder2(view2);
                return holder;
        }
        return holder;
    }

    /**
     * 用来初始化每个模板上个控件的数据并进行一些操作
     *
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        switch (i) {
            case 1:

                break;
            case 2:
                MyViewHolder2 holder = (MyViewHolder2) viewHolder;
                // 创建首页顶部导航栏的适配器
                HomeWorksClassifyAdapter adapter = new HomeWorksClassifyAdapter(this.fragmentManager, this.classify, this.tabTitles);
                // 将适配器设置给ViewPager
                ViewPager pager = holder.pager;
                pager.setAdapter(adapter);
                // 将TabLayout跟ViewPager进行关联
                holder.tab.setupWithViewPager(pager);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return 2;
    }

    protected class MyViewHolder1 extends RecyclerView.ViewHolder {
        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected class MyViewHolder2 extends RecyclerView.ViewHolder {
        protected TabLayout tab;
        protected ViewPager pager;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            this.tab = itemView.findViewById(R.id.tl_title_home);
            this.pager = itemView.findViewById(R.id.vp_items_home);
        }
    }

}
