package com.yixinhuayuan.yiyu.app.utils.adapter.in_trends;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yixinhuayuan.yiyu.R;

/**
 * 这个适配器用于给展示动态点赞人数的RecyclerView,适配数据
 * 这个适配器有一个布局模板,里面包含一个ImageView和一个TextView前者展示头像后者展示昵称
 */
public class TrendsAttentionAdapter extends RecyclerView.Adapter<TrendsAttentionAdapter.TrendsAttentionHolder> {


    private Context context;
    private LayoutInflater inflater;

    public TrendsAttentionAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public TrendsAttentionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = this.inflater.inflate(R.layout.layout_attentions_trends, viewGroup, false);
        TrendsAttentionHolder holder = new TrendsAttentionHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrendsAttentionHolder trendsAttentionHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    protected class TrendsAttentionHolder extends RecyclerView.ViewHolder {
        public TrendsAttentionHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
