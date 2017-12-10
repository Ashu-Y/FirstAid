package com.practice.android.firstaid.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.practice.android.firstaid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parven on 27-11-2017.
 */

public class DescAdapter extends RecyclerView.Adapter<DescAdapter.DescViewHolder> {

    ArrayList<String> desc_list;

    public DescAdapter(List<String> desc_list) {
        this.desc_list = (ArrayList<String>) desc_list;
    }

    @Override
    public DescViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_row_item, parent, false);

        return new DescViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DescViewHolder holder, int position) {

        holder.desc.setText(desc_list.get(position));

    }

    @Override
    public int getItemCount() {
        return desc_list.size();
    }

    public class DescViewHolder extends RecyclerView.ViewHolder {

        public TextView desc;

        public DescViewHolder(View view) {

            super(view);
            desc = view.findViewById(R.id.desc);
        }
    }
}
