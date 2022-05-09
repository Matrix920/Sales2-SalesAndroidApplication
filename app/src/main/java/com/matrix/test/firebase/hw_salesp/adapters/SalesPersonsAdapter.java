package com.matrix.test.firebase.hw_salesp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.matrix.test.firebase.hw_salesp.CommissionDetailsActivity;
import com.matrix.test.firebase.hw_salesp.CommissionsActivity;
import com.matrix.test.firebase.hw_salesp.Connections.HttpRequests;
import com.matrix.test.firebase.hw_salesp.Connections.JsonParser;
import com.matrix.test.firebase.hw_salesp.DeleteActivity;
import com.matrix.test.firebase.hw_salesp.R;
import com.matrix.test.firebase.hw_salesp.SalesPersonActivity;
import com.matrix.test.firebase.hw_salesp.UpdateActivity;
import com.matrix.test.firebase.hw_salesp.utils.SalesPerson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SalesPersonsAdapter extends BaseAdapter {

    TextView txtId,txtName,txtYear,txtMonth,txtReginID,txtRegionName;
    Button btnUpdate,btnCommmissions,btnDelete;
    ImageView pic;

    Context mContext;
    List<SalesPerson>salesPersonList;
    LayoutInflater mInflater;

    public SalesPersonsAdapter(List<SalesPerson>salesPersonList,Context mContext){
        this.salesPersonList=salesPersonList;
        this.mContext=mContext;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return salesPersonList.size();
    }

    @Override
    public SalesPerson getItem(int position) {
        return salesPersonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=mInflater.inflate(R.layout.sales_person_item,null);

        txtId=view.findViewById(R.id.txtPersonID);
        txtMonth=view.findViewById(R.id.txtMonth);
        txtName=view.findViewById(R.id.txtFullName);
        txtYear=view.findViewById(R.id.txtYear);
        txtRegionName=view.findViewById(R.id.txtRegionName);
        btnCommmissions=view.findViewById(R.id.btn_view_commissions);
        btnUpdate=view.findViewById(R.id.btn_update);
        btnDelete=view.findViewById(R.id.btn_delete);

        final SalesPerson s=getItem(position);
        txtRegionName.setText(s.regionName);
        txtId.setText(String.valueOf(s.personID));
        txtName.setText(s.fullname);
        txtYear.setText(String.valueOf(s.year));
        txtMonth.setText(String.valueOf(s.month));

        final int personID=s.personID;

        btnCommmissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext, CommissionsActivity.class);
                i.putExtra(SalesPerson.PERSON_ID,personID);
                mContext.startActivity(i);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext, UpdateActivity.class);
                i.putExtra(SalesPerson.PERSON_ID,personID);
                mContext.startActivity(i);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext, DeleteActivity.class);
                i.putExtra(SalesPerson.PERSON_ID,personID);
                mContext.startActivity(i);
            }
        });

        return view;
    }
}
