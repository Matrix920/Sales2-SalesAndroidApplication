package com.matrix.test.firebase.hw_salesp.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.matrix.test.firebase.hw_salesp.R;

public class CommissionViewHolder extends RecyclerView.ViewHolder {
    public TextView commID,month,year,comm;
    public Button btnDetails;

    public  CommissionViewHolder(View view){
        super(view);

        comm=view.findViewById(R.id.txt_Comm);
        commID=view.findViewById(R.id.txt_CommID);
        month=view.findViewById(R.id.txt_Month);
        year=view.findViewById(R.id.txt_Year);
        btnDetails=view.findViewById(R.id.btnCommDetails);


    }
}
