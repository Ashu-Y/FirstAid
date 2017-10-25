package com.practice.android.firstaid.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.practice.android.firstaid.Interfaces.IMethodCaller;
import com.practice.android.firstaid.R;

import java.util.ArrayList;


/**
 * Created by Ashutosh on 10/2/2017.
 */

public class CityRecyclerAdapter extends RecyclerView.Adapter<CityRecyclerAdapter.CityViewHolder>

{
    private ArrayList<String> cityList;
    private String check;
    private Context mContext;

    public CityRecyclerAdapter(ArrayList<String> cityList, String check, Context context) {
        this.cityList = cityList;
        this.check = check;
        this.mContext = context;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_row_item, parent, false);

        return new CityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, final int position) {
        String city = cityList.get(position);
        holder.cityTv.setText(city);

        if (check.equals("ProfileFragment")) {
            holder.deleteCity.setVisibility(View.GONE);
        } else {
            holder.deleteCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    createAlertDialog(mContext, position);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (!cityList.isEmpty()) {
            return cityList.size();
        }
        return 0;
    }


    public void createAlertDialog(Context context, final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Do you want to delete this city?").setTitle("Confirm Deletion");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                removeCity(position);
                dialogInterface.dismiss();


            }

        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {


            }
        });


        dialog.show();


    }


    public void removeCity(int position) {

        FirebaseMessaging.getInstance().unsubscribeFromTopic(cityList.get(position));

        cityList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cityList.size());
        if (mContext instanceof IMethodCaller) {
            ((IMethodCaller) mContext).checkNoCity();
        }
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {

        public TextView cityTv;
        ImageView deleteCity;

        public CityViewHolder(View view) {

            super(view);
            cityTv = view.findViewById(R.id.cityTv);
            deleteCity = view.findViewById(R.id.delete_city);
        }
    }

}
