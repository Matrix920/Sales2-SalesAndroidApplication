package com.matrix.test.firebase.hw_salesp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.matrix.test.firebase.hw_salesp.Connections.HttpRequests;
import com.matrix.test.firebase.hw_salesp.Connections.JsonParser;
import com.matrix.test.firebase.hw_salesp.utils.Comission;
import com.matrix.test.firebase.hw_salesp.utils.DataHandler;
import com.matrix.test.firebase.hw_salesp.utils.LoginManager;
import com.matrix.test.firebase.hw_salesp.utils.Region;
import com.matrix.test.firebase.hw_salesp.utils.SalesPerson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class forth2Activity extends AppCompatActivity {

    EditText edtNorth,edtSouth,edtCostal,edtEast,edtLebanon,edtMonth,edtYear;
    TextView txtName,txtRegion;
    ImageView img;
    Button btnSave;
    ProgressBar pBar;
    String personID,regionID;
    LoginManager loginManager;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forth2);
        edtCostal=findViewById(R.id.edtCoastalRegion);
        edtNorth=findViewById(R.id.edtNorthernRegion);
        edtEast=findViewById(R.id.edtEasternRegion);
        edtSouth=findViewById(R.id.edtSouthRegion);
        edtLebanon=findViewById(R.id.editLebanon);

        txtName=findViewById(R.id.txtPersonName);
        txtRegion=findViewById(R.id.txtPersonRegion);
        img=findViewById(R.id.imgPerson);

        edtMonth=findViewById(R.id.editMonth);
        edtYear=findViewById(R.id.editYear);

        btnSave=findViewById(R.id.btnCommistion);

        pBar=new ProgressBar(this);

        loginManager=LoginManager.getInstance(getApplicationContext());

        personID= loginManager.getPersonNumber();
        regionID=loginManager.getRegionID();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url= HttpRequests.ComputeCommisssion();
                new ComputeCommission().execute(url);
            }
        });

        SalesPerson p=DataHandler.getInstance().getPerson();

        txtName.setText(loginManager.getname());
        txtRegion.setText(loginManager.getRegionName());
        String url=HttpRequests.getPicture(loginManager.getPicure());
        new DownloadImageTask(img).execute(url);
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(forth2Activity.this);
            pDialog.setCancelable(false);

            pDialog.show();
        }

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            pDialog.hide();
            bmImage.setImageBitmap(result);
        }
    }

    class ComputeCommission extends AsyncTask<String,Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(forth2Activity.this);
            pDialog.setCancelable(false);

            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... url) {
            List<NameValuePair> params=new ArrayList<>();
            params.add(new BasicNameValuePair(Region.COSTAL,edtCostal.getText().toString()));
            params.add(new BasicNameValuePair(Region.SOUTH,edtSouth.getText().toString()));
            params.add(new BasicNameValuePair(Region.NORTH,edtNorth.getText().toString()));
            params.add(new BasicNameValuePair(Region.EAST,edtEast.getText().toString()));
            params.add(new BasicNameValuePair(Region.LEBANON,edtLebanon.getText().toString()));
            params.add(new BasicNameValuePair(SalesPerson.MONTH,edtMonth.getText().toString()));
            params.add(new BasicNameValuePair(SalesPerson.YEAR,edtYear.getText().toString()));
            params.add(new BasicNameValuePair(Region.REGION_ID,regionID));
            params.add(new BasicNameValuePair(SalesPerson.PERSON_ID,personID));

            JsonParser jsonParser=new JsonParser();
            JSONObject data=jsonParser.makeHttpRequest(url[0],"POST",params);
            return data;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            pDialog.hide();
            try {
                if (jsonObject != null) {
                    if (!jsonObject.getBoolean("Error")) {
                        int commID=jsonObject.getInt(Comission.COMMISION_ID);
                        Intent i = new Intent(forth2Activity.this, CommissionDetailsActivity.class);
                        i.putExtra(Comission.COMISSION_ID,commID);
                        startActivity(i);
                        finish();
                    }

                } else {
                    Toast.makeText(forth2Activity.this,"error",Toast.LENGTH_LONG).show();

                }
            }catch (Exception e){

            }
        }
    }
}
