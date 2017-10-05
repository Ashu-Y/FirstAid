package com.practice.android.firstaid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.practice.android.firstaid.Models.HospitalListModel;
import com.practice.android.firstaid.R;

import java.util.ArrayList;

/**
 * Created by parven on 01-10-2017.
 */

public class HospitalListAdapter extends RecyclerView.Adapter<HospitalListAdapter.HospitalViewHolder> {

    ArrayList<HospitalListModel> mList;
    Context mContext;

    public HospitalListAdapter(ArrayList<HospitalListModel> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public HospitalListAdapter(ArrayList<HospitalListModel> mList) {
        this.mList = mList;
    }

    @Override
    public HospitalViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View row_itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_row_item, parent, false);

        return new HospitalViewHolder(row_itemView);
    }

    @Override
    public void onBindViewHolder(HospitalViewHolder holder, int position) {

        final HospitalListModel model = mList.get(position);

        holder.setData(model, position);

        holder.hospitalParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri gmmIntentUri = Uri.parse("geo:" + model.getLatitude() + "," + model.getLongitude()
                        + "?q="+ model.getName() + "" + model.getVicinity());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(mapIntent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HospitalViewHolder extends RecyclerView.ViewHolder {

        TextView hospitalName, hospitalVicinity;
        LinearLayout hospitalParent;

        public HospitalViewHolder(View itemView) {
            super(itemView);

            hospitalParent = itemView.findViewById(R.id.hospital_parent);

            hospitalName = itemView.findViewById(R.id.hospital_name);
            hospitalVicinity = itemView.findViewById(R.id.hospital_vicinity);
        }

        public void setData(HospitalListModel model, int pos){
            hospitalName.setText(model.getName());
            hospitalVicinity.setText(model.getVicinity());
        }
    }
}
