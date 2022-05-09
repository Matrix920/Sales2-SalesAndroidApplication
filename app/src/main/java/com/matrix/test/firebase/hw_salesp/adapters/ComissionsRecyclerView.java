package com.matrix.test.firebase.hw_salesp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matrix.test.firebase.hw_salesp.CommissionDetailsActivity;
import com.matrix.test.firebase.hw_salesp.R;
import com.matrix.test.firebase.hw_salesp.utils.Comission;
import com.matrix.test.firebase.hw_salesp.utils.CommissionViewHolder;

import java.util.List;

public class ComissionsRecyclerView extends RecyclerView.Adapter<CommissionViewHolder> {

    Context context;
    List<Comission>comissions;

    public ComissionsRecyclerView(Context context, List<Comission> comissions) {
        this.context=context;
        this.comissions=comissions;
    }

    @NonNull
    @Override
    public CommissionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.commission_item,viewGroup,false);

        return new CommissionViewHolder(view);
    }

    public void setComissions(List<Comission>comissions){
        this.comissions=comissions;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CommissionViewHolder c, int i) {
        Comission com=comissions.get(i);

        c.commID.setText(String.valueOf(com.comissionID));
        c.comm.setText(String.valueOf(com.comm));
        c.year.setText(String.valueOf(com.year));
        c.month.setText(String.valueOf(com.month));

        final int id=com.comissionID;

        c.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, CommissionDetailsActivity.class);
                i.putExtra(Comission.COMISSION_ID,id);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comissions.size();
    }
}
