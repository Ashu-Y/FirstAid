package com.practice.android.firstaid.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.practice.android.firstaid.Activities.Matter;
import com.practice.android.firstaid.R;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Ashutosh on 10/2/2017.
 */

public  class CityRecyclerAdapter  extends RecyclerView.Adapter<CityRecyclerAdapter.CityViewHolder>

{
    private ArrayList<String> cityList;

    public class CityViewHolder extends RecyclerView.ViewHolder {

        public TextView cityTv;

        public CityViewHolder(View view) {

            super(view);
            cityTv = (TextView) view.findViewById(R.id.cityTv);
        }
    }

    public CityRecyclerAdapter (ArrayList<String> cityList) {
        this.cityList = cityList;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_row_item, parent, false);

        return new CityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        String city = cityList.get(position);
        holder.cityTv.setText(city);
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }


}
