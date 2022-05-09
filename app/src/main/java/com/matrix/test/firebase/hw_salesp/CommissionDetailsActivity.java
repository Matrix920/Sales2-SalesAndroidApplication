package com.matrix.test.firebase.hw_salesp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.matrix.test.firebase.hw_salesp.Connections.HttpRequests;
import com.matrix.test.firebase.hw_salesp.Connections.JsonParser;
import com.matrix.test.firebase.hw_salesp.utils.Comission;
import com.matrix.test.firebase.hw_salesp.utils.CommissionDetails;
import com.matrix.test.firebase.hw_salesp.utils.DataHandler;
import com.matrix.test.firebase.hw_salesp.utils.Region;
import com.matrix.test.firebase.hw_salesp.utils.SalesPerson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommissionDetailsActivity extends AppCompatActivity {

    int commID;
    ProgressDialog pDialog;
    TextView txtNumber,txtName,txtMonth,txtYeaer,txtRegDate,txtSouth,txtNorth,txtEast,txtCostal,txtLebanon,txtComm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission_details);

        txtNumber=findViewById(R.id.txtPersonID);
        txtName=findViewById(R.id.txtPersonName);
        txtMonth=findViewById(R.id.txtMonth);
        txtYeaer=findViewById(R.id.txtYear);
        txtRegDate=findViewById(R.id.txtRegisterDate);
        txtSouth=findViewById(R.id.txtSouth);
        txtNorth=findViewById(R.id.txtNorthern);
        txtEast=findViewById(R.id.txtEastern);
        txtCostal=findViewById(R.id.txtCostal);
        txtLebanon=findViewById(R.id.txtLebanon);
        txtComm=findViewById(R.id.txtComm);

        Intent i=getIntent();
        commID=i.getIntExtra(Comission.COMISSION_ID,1);

        String url= HttpRequests.getCommissionDetails(String.valueOf(commID));
        new GetCommission().execute(url);
    }

    class GetCommission extends AsyncTask<String,Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CommissionDetailsActivity.this);
            pDialog.setCancelable(false);

            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... url) {
            List<NameValuePair> params=new ArrayList<>();
            params.add(new BasicNameValuePair("id",String.valueOf(commID)));

            JsonParser jsonParser=new JsonParser();
            JSONObject data=jsonParser.makeHttpRequest(url[0],"GET",params);
            return data;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            pDialog.hide();
            if(jsonObject!=null) {

                extractAndViewItems(jsonObject);

            }else{

            }
        }
    }

    public void extractAndViewItems(JSONObject s) {

        Toast.makeText(this,s.toString(),Toast.LENGTH_LONG);
//Extract data
        try {
            int id = s.getInt(SalesPerson.PERSON_ID);
            String name = s.getString(SalesPerson.FULL_NAME);
            int year = s.getInt(SalesPerson.YEAR);
            int montn = s.getInt(SalesPerson.MONTH);
            String date = s.getString(SalesPerson.REGISTERATION_DATE);
            double south = s.getDouble(Region.SOUTH);
            double north = s.getDouble(Region.NORTH);
            double east = s.getDouble(Region.EAST);
            double costal = s.getDouble(Region.COSTAL);
            double lebanon = s.getDouble(Region.LEBANON);
            double totalComm = s.getDouble(Comission.COMM);

            txtComm.setText(String.valueOf(totalComm));
            txtCostal.setText(String.valueOf(costal));
            txtEast.setText(String.valueOf(east));
            txtNorth.setText(String.valueOf(north));
            txtLebanon.setText(String.valueOf(lebanon));
            txtSouth.setText(String.valueOf(south));
            txtRegDate.setText(String.valueOf(date));
            txtMonth.setText(String.valueOf(montn));
            txtYeaer.setText(String.valueOf(year));
            txtName.setText(String.valueOf(name));
            txtNumber.setText(String.valueOf(id));

            Toast.makeText(this,"shit",Toast.LENGTH_LONG);

        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"fuck",Toast.LENGTH_LONG);
        }

    }

}
