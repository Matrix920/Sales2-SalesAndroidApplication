package com.matrix.test.firebase.hw_salesp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.matrix.test.firebase.hw_salesp.Connections.HttpRequests;
import com.matrix.test.firebase.hw_salesp.Connections.JsonParser;
import com.matrix.test.firebase.hw_salesp.adapters.SalesPersonsAdapter;
import com.matrix.test.firebase.hw_salesp.utils.DataHandler;
import com.matrix.test.firebase.hw_salesp.utils.Region;
import com.matrix.test.firebase.hw_salesp.utils.SalesPerson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SalesPersonActivity extends AppCompatActivity {

    ListView listViewSalesPersons;
    ProgressBar pBar;
    SalesPersonsAdapter adapter;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_person);

        pBar=new ProgressBar(this);

        listViewSalesPersons=findViewById(R.id.listSalesPersons);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new GetSalesPersons().execute(HttpRequests.getSalesPersons());
    }

    class GetSalesPersons extends AsyncTask<String,Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SalesPersonActivity.this);
            pDialog.setCancelable(false);

            pDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... url) {
            List<NameValuePair> params=new ArrayList<>();
            params.add(new BasicNameValuePair("x",""));

            JsonParser jsonParser=new JsonParser();
            JSONArray data=jsonParser.makeHttpRequestArray(url[0],"GET",params);
            return data;
        }

        @Override
        protected void onPostExecute(JSONArray jsonObject) {
            pDialog.hide();
            if(jsonObject!=null) {
                //hide refresh button
                //  btnRefresh.setVisibility(View.GONE);
                extractAndViewItems(jsonObject);

            }else{
                //   viewRefreshButton();

            }
        }
    }



    public void extractAndViewItems(JSONArray salesPersonsArray) {

//Extract data
        try {
            //check error

                List<SalesPerson> salesPersonList = new ArrayList<>();

                for (int i = 0; i < salesPersonsArray.length(); i++) {

                    JSONObject s=salesPersonsArray.getJSONObject(i);

                    int id = s.getInt(SalesPerson.PERSON_ID);
                    String name=s.getString(SalesPerson.FULL_NAME);
                    int year=s.getInt(SalesPerson.YEAR);
                    int montn=s.getInt(SalesPerson.MONTH);
                    int regionID=s.getInt(SalesPerson.PERSON_ID);
                    String username=s.getString(SalesPerson.USERNAME);
                    String password=s.getString(SalesPerson.PASSWORD);
                    String regionName=s.getString(Region.REGION_NAME);
                    String picture=s.getString(SalesPerson.PICTURE);

                    SalesPerson person=new SalesPerson(id,username,password,name,montn,year,picture,regionID,regionName);

                    salesPersonList.add(person);
                }

                viewSalesPersons(salesPersonList);
                DataHandler.getInstance().setSalesPersons(salesPersonList);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void viewSalesPersons(List<SalesPerson> salesPersonList){
        if(adapter==null){
            adapter=new SalesPersonsAdapter(salesPersonList,this);
            adapter.notifyDataSetChanged();
        }
        listViewSalesPersons.setAdapter(adapter);




    }

}
