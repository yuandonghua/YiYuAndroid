package com.yixinhuayuan.yiyu.app.utils.adapter.in_my;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yixinhuayuan.yiyu.R;

/**
 * 这个适配器是用来给展示我的动态列表的RecyclerView适配数据的
 */
public class MyTrendsItemAdapter extends RecyclerView.Adapter<MyTrendsItemAdapter.MyTrendsItemHolder> {
    private Context context;
    private LayoutInflater inflater;

    public MyTrendsItemAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public MyTrendsItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = this.inflater.inflate(R.layout.layout_mytrendsitem_my, viewGroup, false);
        MyTrendsItemHolder holder = new MyTrendsItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyTrendsItemHolder myTrendsItemHolder, int i) {


        /**
         * click.4.给ViewHolder的itemView设置点击事件,然后在事件的onClick方法中判断listener是否为空
         * 当listener不为空的时候调用listener的onClick方法并将当前条目的position传进去
         */
        myTrendsItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    protected class MyTrendsItemHolder extends RecyclerView.ViewHolder {

        public MyTrendsItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    // 设置点击事件

    /**
     * click.1.定义一个接口,里面有一个onClick方法,方法里有一个int position参数
     */
    public interface OnItemClickListener {
        void onClick(int position);
    }

    /**
     * click.2.定义一个成员变量,类型刚才创建的接口
     */
    private OnItemClickListener listener;

    /**
     * click.3.定义一个公共的方法传入刚才定义接口为类型的参数,在方法体内让方法传进来的参数赋值给类型为刚才定义接口类型的成员变量
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
