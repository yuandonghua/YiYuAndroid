package com.yixinhuayuan.yiyu.app.utils.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yixinhuayuan.yiyu.R;

/**
 * 这个适配器用来给 动态 页面用来展示动态列表的RecyclerViewView适配数据
 */
public class TrendsAdapter extends RecyclerView.Adapter<TrendsAdapter.TrendsHolder> {

    private Context context;
    private LayoutInflater inflater;

    public TrendsAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);

    }

    @NonNull
    @Override
    public TrendsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.layout_item_trends, viewGroup, false);
        TrendsHolder trendsHolder = new TrendsHolder(view);
        return trendsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrendsHolder trendsHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    protected class TrendsHolder extends RecyclerView.ViewHolder {

        public TrendsHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
