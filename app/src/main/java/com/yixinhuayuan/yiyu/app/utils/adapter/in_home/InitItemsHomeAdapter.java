package com.yixinhuayuan.yiyu.app.utils.adapter.in_home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.dim.widget.ImageView;
import com.dim.widget.RadioButton;
import com.dim.widget.TextView;
import com.yixinhuayuan.yiyu.R;

public class InitItemsHomeAdapter extends RecyclerView.Adapter<InitItemsHomeAdapter.MyViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    // 头像
    private int[] images;
    // 昵称
    private String[] nicks;
    // 签名
    private String[] signs;
    // 关注量
    private long[] attentions;
    // 留言量
    private long[] messages;
    // 分享量
    private long[] shares;

    public InitItemsHomeAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
    }

    /**
     * 通过构造方法初始化数据
     *
     * @param images
     * @param nicks
     * @param signs
     * @param attentions
     * @param messages
     * @param shares
     */
    public InitItemsHomeAdapter(Context context, int[] images, String[] nicks, String[] signs, long[] attentions, long[] messages, long[] shares) {
        this.context = context;
        this.images = images;
        this.nicks = nicks;
        this.signs = signs;
        this.attentions = attentions;
        this.messages = messages;
        this.shares = shares;
        this.inflater = LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.layout_worksitem_home, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

    }

    @Override
    public int getItemCount() {
      /*  if (nicks != null && nicks.length > 0) {
            return nicks.length;
        }*/
        return 10;
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {

        protected ImageView mainImage;
        protected ImageView headerImage;
        protected TextView workName;
        protected TextView workType;
        protected TextView userNick;
        protected TextView userSign;
        protected TextView messages;
        protected TextView shares;
        protected RadioGroup group;
        protected RadioButton radioButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mainImage = itemView.findViewById(R.id.iv_workimg_home);
            this.headerImage = itemView.findViewById(R.id.iv_header_trendsitem);
            this.workName = itemView.findViewById(R.id.tv_workname_home);
            this.workType = itemView.findViewById(R.id.tv_worktype_home);
            this.userNick = itemView.findViewById(R.id.tv_nick_trendsitem);
            this.userSign = itemView.findViewById(R.id.tv_sign_trendsitem);
            this.messages = itemView.findViewById(R.id.tv_message_home);
            this.shares = itemView.findViewById(R.id.tv_share_home);
            this.group = itemView.findViewById(R.id.rg_attention_home);
            this.radioButton = itemView.findViewById(R.id.rb_attention_home);
        }
    }

}
