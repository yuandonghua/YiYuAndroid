package com.yixinhuayuan.yiyu.app.utils.adapter.in_home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dim.widget.ImageView;
import com.dim.widget.LinearLayout;
import com.dim.widget.TextView;
import com.yixinhuayuan.yiyu.R;
import com.yixinhuayuan.yiyu.app.utils.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.POST;

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

    // 轮播图要展示的ImageView
    private ArrayList<ImageView> imageViews;
    // 轮播图资源图片ID
    private int[] imgs;
    // 轮播图标题
    private String[] titles;
    //
    private int previousSelectedPosition = 0;

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
                break;
            case 2:
                View view2 = this.inflate.inflate(R.layout.layout_works_home, viewGroup, false);
                holder = new MyViewHolder2(view2);
                break;
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
            case 2:
                MyViewHolder2 holder2 = (MyViewHolder2) viewHolder;
                // 创建首页顶部导航栏的适配器
                HomeWorksClassifyAdapter adapter = new HomeWorksClassifyAdapter(this.fragmentManager, this.classify, this.tabTitles);
                // 将适配器设置给ViewPager
                ViewPager pager = holder2.pager;
                pager.setAdapter(adapter);
                // 将TabLayout跟ViewPager进行关联
                holder2.tab.setupWithViewPager(pager);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return 2;
    }

    /**
     * 轮播图布局
     */
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
