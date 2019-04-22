package com.yixinhuayuan.yiyu.app.utils.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dim.widget.ImageView;
import com.yixinhuayuan.yiyu.R;

import butterknife.BindView;

public class PcaFansListAdapter extends RecyclerView.Adapter<PcaFansListAdapter.FlViewHolder> {

    Context context;


    public PcaFansListAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public FlViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(this.context).inflate(R.layout.fanslist_item_layout
                , viewGroup
                , false);
        FlViewHolder holder = new FlViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FlViewHolder alViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    protected static class FlViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_flitem_avatar)
        ImageView flitemAvatar;
        @BindView(R.id.iv_flitem_tomessage)
        ImageView flitemToMessage;

        public FlViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
