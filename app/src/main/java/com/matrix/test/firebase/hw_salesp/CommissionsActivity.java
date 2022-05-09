package com.matrix.test.firebase.hw_salesp;

import android.app.Person;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.matrix.test.firebase.hw_salesp.Connections.HttpRequests;
import com.matrix.test.firebase.hw_salesp.Connections.JsonParser;
import com.matrix.test.firebase.hw_salesp.adapters.ComissionsRecyclerView;
import com.matrix.test.firebase.hw_salesp.adapters.SalesPersonsAdapter;
import com.matrix.test.firebase.hw_salesp.utils.Comission;
import com.matrix.test.firebase.hw_salesp.utils.DataHandler;
import com.matrix.test.firebase.hw_salesp.utils.Region;
import com.matrix.test.firebase.hw_salesp.utils.SalesPerson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CommissionsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText edtMonth,edtYear;
    ProgressDialog pDialog;
    int personID;
    ComissionsRecyclerView adapter;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commissions);

        recyclerView=findViewById(R.id.recyclerviewCommissions);



        edtMonth=findViewById(R.id.edtMonth);
        edtYear=findViewById(R.id.edtYear);
        btnSearch=findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=HttpRequests.getCommissionsByDate();
                new GetCommissionsByDate().execute(url);
            }
        });

        Intent i=getIntent();
        personID=i.getIntExtra(SalesPerson.PERSON_ID,0);

        String url= HttpRequests.getCommissions(String.valueOf(personID));
        new GetCommissions().execute(url);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    class GetCommissions extends AsyncTask<String,Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CommissionsActivity.this);
            pDialog.setCancelable(false);

            pDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... url) {
            List<NameValuePair> params=new ArrayList<>();
            params.add(new BasicNameValuePair("id",String.valueOf(personID)));

            JsonParser jsonParser=new JsonParser();
            JSONArray data=jsonParser.makeHttpRequestArray(url[0],"GET",params);
            return data;
        }

        @Override
        protected void onPostExecute(JSONArray jsonObject) {
            pDialog.hide();
            if(jsonObject!=null) {
                Log.d("data",jsonObject.toString());
                extractAndViewItems(jsonObject);

            }else{
            }
        }
    }

    class GetCommissionsByDate extends AsyncTask<String,Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CommissionsActivity.this);
            pDialog.setCancelable(false);

            pDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... url) {
            List<NameValuePair> params=new ArrayList<>();
            params.add(new BasicNameValuePair(SalesPerson.PERSON_ID,String.valueOf(personID)));
            params.add(new BasicNameValuePair(SalesPerson.MONTH,edtMonth.getText().toString()));
            params.add(new BasicNameValuePair(SalesPerson.YEAR,edtYear.getText().toString()));

            JsonParser jsonParser=new JsonParser();
            JSONArray data=jsonParser.makeHttpRequestArray(url[0],"POST",params);
            return data;
        }

        @Override
        protected void onPostExecute(JSONArray jsonObject) {
            pDialog.hide();
            if(jsonObject!=null || jsonObject.length()>0) {
                //hide refresh button
                //  btnRefresh.setVisibility(View.GONE);
                Toast.makeText(CommissionsActivity.this,"data",Toast.LENGTH_LONG).show();
                extractAndViewItems(jsonObject);

            }else{
                Toast.makeText(CommissionsActivity.this,"emty",Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void extractAndViewItems(JSONArray commisssionsArray) {

        try {

                List<Comission> commissionsList = new ArrayList<>();

                for (int i = 0; i < commisssionsArray.length(); i++) {

                    JSONObject s=commisssionsArray.getJSONObject(i);

                    int commID = s.getInt(Comission.COMISSION_ID);
                    int year=s.getInt(SalesPerson.YEAR);
                    int montn=s.getInt(SalesPerson.MONTH);
                    double comm=s.getDouble(Comission.COMM);

                    Comission comission=new Comission(commID,montn,year,comm);

                    commissionsList.add(comission);
                }

                viewCommissions(commissionsList);

                DataHandler.getInstance().setCommisssions(commissionsList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void viewCommissions(List<Comission> commissionsList) {
        adapter = new ComissionsRecyclerView(this, commissionsList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
