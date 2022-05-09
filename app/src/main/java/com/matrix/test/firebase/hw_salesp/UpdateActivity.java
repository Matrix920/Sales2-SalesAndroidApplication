package com.matrix.test.firebase.hw_salesp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.matrix.test.firebase.hw_salesp.Connections.HttpRequests;
import com.matrix.test.firebase.hw_salesp.Connections.JsonParser;
import com.matrix.test.firebase.hw_salesp.utils.DataHandler;
import com.matrix.test.firebase.hw_salesp.utils.Region;
import com.matrix.test.firebase.hw_salesp.utils.Sale;
import com.matrix.test.firebase.hw_salesp.utils.SalesPerson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    RadioButton radioSouth,radioEast,radioCostal,radioNorth,radioLebanon;
    EditText edtName,edtPassword,edtUsername,edtMonth,edtYear;
    TextView txtRegion;
    RadioGroup radioRegions;
    ProgressDialog pDialog;
    Button btnSave;
    int regionID;
    int personID;
    SalesPerson person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        radioSouth=findViewById(R.id.radioSouth);
        radioNorth=findViewById(R.id.radioNorth);
        radioCostal=findViewById(R.id.radioCostal);
        radioEast=findViewById(R.id.radioEast);
        radioLebanon=findViewById(R.id.radioLebanon);
        radioRegions=findViewById(R.id.radioGropRegions);

        edtName=findViewById(R.id.edtFullName);
        edtPassword=findViewById(R.id.edtPassword);
        edtUsername=findViewById(R.id.edtUsername);
        edtMonth=findViewById(R.id.edtMonth);
        edtYear=findViewById(R.id.edtYear);
        txtRegion=findViewById(R.id.txtRegionName);

        btnSave=findViewById(R.id.btnAddSalesPerson);

        Intent i=getIntent();
        personID=i.getIntExtra(SalesPerson.PERSON_ID,1);

        person= DataHandler.getInstance().getPerson(personID);
        setupData();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regionID=getRegionID();
                String url= HttpRequests.updateSalesPerson();
                new UpdateSalesPerson().execute(url);
            }
        });
    }

    private int getRegionID() {
        if(radioSouth.isChecked())
            return 1;

        if(radioSouth.isChecked())
            return 2;
        if(radioEast.isChecked())
            return 3;
        if(radioCostal.isChecked())
            return 4;
        return 5;
    }

    private void setupData(){
        edtMonth.setText(String.valueOf(person.month));
        edtYear.setText(String.valueOf(person.year));
        edtName.setText(person.fullname);
        txtRegion.setText(person.regionName);
        regionID=person.regionID;
        edtUsername.setText(person.username);
        edtPassword.setText(person.password);
    }

    class UpdateSalesPerson extends AsyncTask<String,Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateActivity.this);
            pDialog.setCancelable(false);

            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... url) {
            List<NameValuePair> params=new ArrayList<>();
            params.add(new BasicNameValuePair(Region.REGION_ID,String.valueOf(regionID)));
            params.add(new BasicNameValuePair(SalesPerson.MONTH,edtMonth.getText().toString()));
            params.add(new BasicNameValuePair(SalesPerson.YEAR,edtYear.getText().toString()));

            params.add(new BasicNameValuePair(SalesPerson.PERSON_ID,String.valueOf(personID)));
            params.add(new BasicNameValuePair(SalesPerson.USERNAME,edtUsername.getText().toString()));
            params.add(new BasicNameValuePair(SalesPerson.FULL_NAME,edtName.getText().toString()));
            params.add(new BasicNameValuePair(SalesPerson.PASSWORD,edtPassword.getText().toString()));

            JsonParser jsonParser=new JsonParser();
            JSONObject data=jsonParser.makeHttpRequest(url[0],"POST",params);
            return data;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            pDialog.hide();
            if(jsonObject!=null) {
                //hide refresh button
                //  btnRefresh.setVisibility(View.GONE);
                try{
                    if(!jsonObject.getBoolean("Error")){
                        Intent intent=new Intent(UpdateActivity.this,SalesPersonActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }else{
                //   viewRefreshButton();

            }
        }
    }
}
