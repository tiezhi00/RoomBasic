package com.app.roombasic.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.roombasic.R;
import com.app.roombasic.room.Word;

import java.util.ArrayList;
import java.util.List;

/*
 * MyAdapter.java
 * 相当于RecyclerView的内容管理器
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Word> list = new ArrayList<>();
    private boolean isCardView = false;
    public MyAdapter(boolean isCardView) {
        this.isCardView = isCardView;
    }

    public void setList(List<Word> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建ViewHolder时调用，用于创建ViewHolder实例
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //根据isCardView的值选择不同的布局
        View itemView;
        if (isCardView) {
            itemView=inflater.inflate(R.layout.item_word_card, parent, false);
        } else {
            itemView=inflater.inflate(R.layout.item_word, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //绑定数据到ViewHolder时调用，用于设置item视图的内容
        Word word = list.get(position);
        holder.tv_id.setText(position + 1 + "");
        holder.tv_english.setText(word.getWord());
        holder.tv_chinese.setText(word.getChineseMeaning());
        //设置item的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转打开网页
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("https://m.youdao.com/dict?le=eng&q=" + word.getWord());
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //返回item的总数
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        //ViewHolder类，用于管理item视图
        TextView tv_id, tv_english, tv_chinese;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_english = itemView.findViewById(R.id.tv_english);
            tv_chinese = itemView.findViewById(R.id.tv_chinese);
        }

    }
}
