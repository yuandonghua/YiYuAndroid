package com.yixinhuayuan.yiyu.app.utils.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dim.widget.ImageView;
import com.dim.widget.TextView;
import com.yixinhuayuan.yiyu.R;
import com.yixinhuayuan.yiyu.app.utils.data.MyWorksData;

import java.util.ArrayList;
import java.util.List;

public class WorksListAdapter extends RecyclerView.Adapter<WorksListAdapter.WorksItemHolder> {

    private static final String TAG = WorksListAdapter.class.getSimpleName().toString();
    private Context context;
    // private int[] images = new int[]{R.drawable.art_work1, R.drawable.art_work2};
    //private String[] clas = new String[]{"山水", "人物", "花鸟"};
    //private int[] count = new int[]{2, 3, 4};
    // 用户数据
    private int user_id;
    // 请求到的 我的作品集的数据
    private List<MyWorksData> myWorksData;

    public WorksListAdapter(Context context, List<MyWorksData> myWorksData) {
        this.context = context;
        this.myWorksData = myWorksData;
    }

    @NonNull
    @Override
    public WorksItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        RecyclerView.ViewHolder holder = null;
        View works_item = LayoutInflater.from(context).inflate(R.layout.layout_workslist_item_my, null);
        holder = new WorksItemHolder(works_item);
        return (WorksItemHolder) holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorksItemHolder worksItemHolder, int i) {
        int num = i - 1;
        if (i == 0) {
            worksItemHolder.count.setVisibility(View.GONE);
            worksItemHolder.clas.setText("添加作品集");
            worksItemHolder.imag.setImageResource(R.drawable.art_work);

        } else {
            worksItemHolder.imag.setImageResource(R.drawable.art_work2);
            worksItemHolder.clas.setText(myWorksData.get(num).getClass_name() + "");
            worksItemHolder.count.setText("共" + myWorksData.get(num).getNumber() + "件");
        }
        worksItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(i);
                }
            }
        });
        worksItemHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    longClickListener.onLongClick(i, myWorksData.get(num).getId());
                }
                //return false;
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (myWorksData != null && myWorksData.size() > 0) {
            Log.d(TAG, "getItemCount: " + myWorksData.size());
            return myWorksData.size() + 1;
        }
        return 1;
    }


    class WorksItemHolder extends RecyclerView.ViewHolder {
        ImageView imag;
        TextView clas;
        TextView count;

        public WorksItemHolder(@NonNull View itemView) {
            super(itemView);
            this.imag = itemView.findViewById(R.id.iv_imag_works);
            this.clas = itemView.findViewById(R.id.tv_class_works);
            this.count = itemView.findViewById(R.id.tv_count_works);
        }
    }


    // 设置点击事件
    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // 设置长按事件
    public interface OnItemLongClickListener {
        void onLongClick(int position, int id);
    }

    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
}
