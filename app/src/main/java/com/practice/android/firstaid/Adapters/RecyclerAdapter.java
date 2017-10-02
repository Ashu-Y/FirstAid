package com.practice.android.firstaid.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.practice.android.firstaid.Activities.Matter;
import com.practice.android.firstaid.R;

import java.util.List;



/**
 * Created by Ashutosh on 10/2/2017.
 */

public  class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>

{
    private List<Matter> matterlist;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title,sub,id;

        public MyViewHolder(View view) {

            super(view);
            title = (TextView) view.findViewById(R.id.title);
            sub = (TextView) view.findViewById(R.id.sub);
            id = view.findViewById(R.id.tv);
        }
    }

    public RecyclerAdapter (List<Matter> matterlist) {
        this.matterlist = matterlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Matter matter = matterlist.get(position);
        holder.title.setText(matter.getTitle());
        holder.sub.setText(matter.getSub());
        holder.id.setText(""+(position+1));
    }

    @Override
    public int getItemCount() {
        return matterlist.size();
    }


}
