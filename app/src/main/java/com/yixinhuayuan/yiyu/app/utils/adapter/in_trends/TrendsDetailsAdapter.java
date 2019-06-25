package com.yixinhuayuan.yiyu.app.utils.adapter.in_trends;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dim.widget.LinearLayout;
import com.dim.widget.RelativeLayout;
import com.yixinhuayuan.yiyu.R;
import com.yixinhuayuan.yiyu.app.utils.adapter.TrendsAdapter;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_trends.TrendsAttentionsActivity;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_trends.TrendsDetailsActivity;

/**
 * 因为动态详情页是用RecyclerView展示的,所以这个适配器就是为这个RecyclerView适配数据得
 * 这个RecyclerView只有一个条目用于展示动态详情
 */
public class TrendsDetailsAdapter extends RecyclerView.Adapter<TrendsDetailsAdapter.TrendsDetailsHolder> {

    private Context context;
    private LayoutInflater inflater;

    public TrendsDetailsAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public TrendsDetailsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = this.inflater.inflate(R.layout.layout_detailsitem_trends, viewGroup, false);
        TrendsDetailsHolder holder = new TrendsDetailsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrendsDetailsHolder holder, int i) {
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, TrendsAttentionsActivity.class));
            }
        });


        // 设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        return 1;
    }

    protected class TrendsDetailsHolder extends RecyclerView.ViewHolder {
        public RelativeLayout layout;

        public TrendsDetailsHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView.findViewById(R.id.rl2_trendsitem);
        }
    }

    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }

    private TrendsAdapter.OnItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(TrendsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
