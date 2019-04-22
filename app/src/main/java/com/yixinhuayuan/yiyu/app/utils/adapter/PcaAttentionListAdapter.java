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

public class PcaAttentionListAdapter extends RecyclerView.Adapter<PcaAttentionListAdapter.AlViewHolder> {

    Context context;


    public PcaAttentionListAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public AlViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(this.context).inflate(R.layout.attentionlist_item_layout
                , viewGroup
                , false);
        AlViewHolder holder = new AlViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlViewHolder alViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    protected static class AlViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_alitem_avatar)
        ImageView alitemAvatar;
        @BindView(R.id.iv_alitem_tomessage)
        ImageView alitemToMessage;

        public AlViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
