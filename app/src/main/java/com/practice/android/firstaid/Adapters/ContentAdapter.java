package com.practice.android.firstaid.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.practice.android.firstaid.Models.Content_Row_Item;
import com.practice.android.firstaid.R;

import java.util.List;

/**
 * Created by parven on 22-11-2017.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ARow> {

    public List<Content_Row_Item> content;

    public ContentAdapter(List<Content_Row_Item> content) {
        this.content = content;
    }

    @Override
    public ARow onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);


        return new ARow(itemView);
    }

    @Override
    public void onBindViewHolder(ARow holder, int position) {


        Content_Row_Item content_row_item = content.get(position);
        holder.imageView.setImageResource(content_row_item.getImage());
        holder.HtextView.setText(content_row_item.getHText());
        holder.textView1.setText(content_row_item.getSText1());
        holder.textView2.setText(content_row_item.getSText2());
        holder.textView3.setText(content_row_item.getSText3());

    }


    @Override
    public int getItemCount() {

        return content.size();
    }

    public class ARow extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView HtextView;
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;

        public ARow(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.biteImage);
            HtextView = itemView.findViewById(R.id.titleBite);
            textView1 = itemView.findViewById(R.id.text1);
            textView2 = itemView.findViewById(R.id.text2);
            textView3 = itemView.findViewById(R.id.text3);
        }
    }
}
