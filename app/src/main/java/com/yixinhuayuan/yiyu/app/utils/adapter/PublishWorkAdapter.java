package com.yixinhuayuan.yiyu.app.utils.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yixinhuayuan.yiyu.R;

public class PublishWorkAdapter extends RecyclerView.Adapter<PublishWorkAdapter.LayoutViewHolder> {
    private int[] layout;
    private Context context;

    public PublishWorkAdapter(int[] layout, Context context) {
        this.layout = layout;
        this.context = context;
    }


    @NonNull
    @Override
    public LayoutViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.layout_publishwork_item0, viewGroup, false);
        RecyclerView.ViewHolder holder = new LayoutViewHolder(view);
        return (LayoutViewHolder) holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LayoutViewHolder holder, int i) {
        holder.showView.addView(LayoutInflater.from(this.context).inflate(layout[i], null));
    }

    @Override
    public int getItemCount() {
        return layout.length;
    }

    protected class LayoutViewHolder extends RecyclerView.ViewHolder {
        protected FrameLayout showView;

        public LayoutViewHolder(@NonNull View itemView) {
            super(itemView);
            this.showView = itemView.findViewById(R.id.fl_view_publishwork);
        }
    }
}
