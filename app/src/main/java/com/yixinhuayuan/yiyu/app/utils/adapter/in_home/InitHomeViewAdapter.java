package com.yixinhuayuan.yiyu.app.utils.adapter.in_home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dim.widget.FrameLayout;
import com.dim.widget.LinearLayout;
import com.yixinhuayuan.yiyu.R;
import com.yixinhuayuan.yiyu.mvp.ui.activity.MainActivity;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.in_home.HomeSlideshowFragment;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.in_home.HomeWorkItemsFragment;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.in_home.HomeWorksListFragment;

import java.util.ArrayList;

/**
 * 用来初始化 首页 的界面,由于首页的布局是一个可滑动的布局采用了RecyclerView加载不同布局来实现,这个适配器就是讲适配两个布局在同一个RecyclerView展示出来.
 */
public class InitHomeViewAdapter extends RecyclerView.Adapter<InitHomeViewAdapter.HomeViewHolder> {


    private Context context;
    private LayoutInflater inflate;
    private static ArrayList<Fragment> items = new ArrayList<>();

    /**
     * 初始化 两个条目
     */static {
        InitHomeViewAdapter adapter = new InitHomeViewAdapter();
        adapter.initWorksViewData();
        items.add(new HomeSlideshowFragment());
        items.add(HomeWorksListFragment.newInstance(adapter.classify, adapter.tabTitles));
    }

    private View view;

    /* // ViewPager要展示的作品分类Fragment
    private List<Fragment> classify;
    // TabLayout的标题
    private String[] tabTitles;
    // Fragment管理器
    private FragmentManager fragmentManager;

    // 轮播图要展示的ImageView
    private ArrayList<ImageView> imageViews;
    // 轮播图资源图片ID
    private int[] imgs;
    // 轮播图标题
    private String[] titles;
    //
    private int previousSelectedPosition = 0;*/

    public InitHomeViewAdapter(Context context) {
        this.context = context;
        this.inflate = LayoutInflater.from(this.context);
        //initWorksViewData();
    }

    public InitHomeViewAdapter() {
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
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = this.inflate.inflate(R.layout.layout_homeview_home, viewGroup, false);
        HomeViewHolder holder = new HomeViewHolder(view);
        return holder;
    }

    /**
     * 用来初始化每个模板上个控件的数据并进行一些操作
     *
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder viewHolder, int i) {
        FragmentTransaction transaction1 = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
        transaction1.add(R.id.fl_rvitem_homeview1, items.get(1));
        //transaction1.add(R.id.fl_rvitem_homeview2, items.get(0));
        transaction1.commit();
/*
        switch (i) {
            case 1:
                //MyViewHolder1 holder1 = (MyViewHolder1) viewHolder;
                this.imgs = new int[]{R.drawable.point1, R.drawable.point2, R.drawable.point1};
                this.titles = new String[]{"轮播图一", "轮播图二", "轮播图三"};
                this.imageViews = new ArrayList<ImageView>();
                ImageView imageView;
                View pointView;
                LinearLayout.LayoutParams layoutParams;
                for (int j = 0; j < imgs.length; j++) {
                    // 初始化要显示的图片对象
                    imageView = new ImageView(context);
                    imageView.setBackgroundResource(imgs[j]);
                    imageViews.add(imageView);
                    // 加小白点指示器
                    pointView = new View(context);
                    pointView.setBackgroundResource(R.drawable.point2);
                    layoutParams = new android.widget.LinearLayout.LayoutParams(5, 5);
                    if (j != 0) {
                        layoutParams.leftMargin = 10;
                    }
                    pointView.setEnabled(false);
                    ((MyViewHolder1) viewHolder).point.addView(pointView, layoutParams);
                }
                // 设置事件
                ((MyViewHolder1) viewHolder).slidesshow.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        // 新的条目被选中时调用
                        int newPosition = i % imageViews.size();
                        // 设置文本
                        ((MyViewHolder1) viewHolder).title.setText(titles[newPosition]);
                        ((MyViewHolder1) viewHolder).point.getChildAt(previousSelectedPosition).setEnabled(false);
                        ((MyViewHolder1) viewHolder).point.getChildAt(newPosition).setEnabled(true);
                        // 记录之前的位置
                        previousSelectedPosition = newPosition;
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });
                ((MyViewHolder1) viewHolder).point.getChildAt(0).setEnabled(true);
                ((MyViewHolder1) viewHolder).title.setText(titles[0]);
                previousSelectedPosition = 0;

                // 设置适配器
                ((MyViewHolder1) viewHolder).slidesshow.setAdapter(new MyAdapter(imageViews));

                // 默认设置到中间的某个位置
                int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViews.size());
                // 2147483647 / 2 = 1073741823 - (1073741823 % 5)
                ((MyViewHolder1) viewHolder).slidesshow.setCurrentItem(5000000); // 设置到某个位置
                break;


        }*/


    }

    @Override
    public int getItemCount() {
        /*if (items != null && items.size() > 0) {
            return items.size();
        }*/
        return 1;
    }


    /**
     * 轮播图布局
     *//*
    protected class MyViewHolder1 extends RecyclerView.ViewHolder {
        // 图片容器
        public ViewPager slidesshow;
        // 标题
        public TextView title;
        // 圆点
        public LinearLayout point;

        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);
            this.slidesshow = itemView.findViewById(R.id.page_slideshow_home);
            this.title = itemView.findViewById(R.id.tv_title_home);
            this.point = itemView.findViewById(R.id.ll_point_home);
        }
    }
*/


    protected class HomeViewHolder extends RecyclerView.ViewHolder {

        //public FrameLayout layout1;
        //public FrameLayout layout2;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            //itemView.findViewById(R.id.fl_rvitem_homeview1);
           // itemView.findViewById(R.id.fl_rvitem_homeview2);
        }
    }

    /**
     * 不同标题对应的ViewPager里面装的内容
     */
    private ArrayList<Fragment> classify;
    /**
     * TabLayout对应的每个标题
     */
    private String[] tabTitles;

    private void initWorksViewData() {
        // 初始化内容
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeWorkItemsFragment());
        fragments.add(new HomeWorkItemsFragment());
        fragments.add(new HomeWorkItemsFragment());
        fragments.add(new HomeWorkItemsFragment());
        fragments.add(new HomeWorkItemsFragment());
        fragments.add(new HomeWorkItemsFragment());
        this.classify = fragments;
        // 初始化标题
        this.tabTitles = new String[]{
                "标题一",
                "标题二",
                "标题三",
                "标题四",
                "标题五",
                "标题六",
        };
    }

}
