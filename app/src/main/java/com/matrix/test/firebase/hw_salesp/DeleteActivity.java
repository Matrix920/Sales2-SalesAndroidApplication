package com.matrix.test.firebase.hw_salesp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.matrix.test.firebase.hw_salesp.Connections.HttpRequests;
import com.matrix.test.firebase.hw_salesp.Connections.JsonParser;
import com.matrix.test.firebase.hw_salesp.utils.SalesPerson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DeleteActivity extends AppCompatActivity {

    int personID;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Intent i=getIntent();
        personID=i.getIntExtra(SalesPerson.PERSON_ID,1);
        Toast.makeText(DeleteActivity.this,"User ID :"+personID,Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        String url= HttpRequests.deletePerson();
        new DeleteSalesPerson().execute(url);


    }

    class DeleteSalesPerson extends AsyncTask<String,Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DeleteActivity.this);
            pDialog.setCancelable(false);

            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... url) {
            List<NameValuePair> params=new ArrayList<>();

            params.add(new BasicNameValuePair(SalesPerson.PERSON_ID,String.valueOf(personID)));
            //params.add(new BasicNameValuePair(SalesPerson.PERSON_ID,"1008"));

            JsonParser jsonParser=new JsonParser();
            JSONObject data=jsonParser.makeHttpRequest(url[0],"POST",params);
            return data;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

          pDialog.hide();
            try {
                if (jsonObject.getBoolean("Error")) {
                    Toast.makeText(DeleteActivity.this,jsonObject.getString("Message"),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(DeleteActivity.this,"deleted successfully",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                e.printStackTrace();

            }
           finish();
        }
    }
}
