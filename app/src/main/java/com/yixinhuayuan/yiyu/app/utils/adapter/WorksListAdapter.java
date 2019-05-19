package com.yixinhuayuan.yiyu.app.utils.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dim.widget.ImageView;
import com.dim.widget.TextView;
import com.yixinhuayuan.yiyu.R;

import butterknife.BindView;

public class WorksListAdapter extends RecyclerView.Adapter<WorksListAdapter.WorksItemHolder> {
    private Context context;
    private int[] images = new int[]{R.drawable.art_work1, R.drawable.art_work2, R.drawable.art_work3};
    private String[] clas = new String[]{"山水", "人物", "花鸟"};
    private int[] count = new int[]{2, 3, 4};

    public WorksListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public WorksItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        RecyclerView.ViewHolder holder = null;
        View works_item = LayoutInflater.from(context).inflate(R.layout.rv_works_item, null);
        holder = new WorksItemHolder(works_item);
        return (WorksItemHolder) holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorksItemHolder worksItemHolder, int i) {
        if (i == 0) {
            worksItemHolder.count.setVisibility(View.GONE);
            worksItemHolder.clas.setText("添加作品集");
            worksItemHolder.imag.setImageResource(R.drawable.art_work);
        } else {
            int num=i-1;
            worksItemHolder.imag.setImageResource(images[num]);
            worksItemHolder.clas.setText(clas[num] + "");
            worksItemHolder.count.setText("共" + count[num] + "件");
        }

    }

    @Override
    public int getItemCount() {
        return images.length+1;
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


}
