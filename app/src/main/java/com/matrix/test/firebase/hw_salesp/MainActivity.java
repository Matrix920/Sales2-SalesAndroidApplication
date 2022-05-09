package com.matrix.test.firebase.hw_salesp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.matrix.test.firebase.hw_salesp.Connections.HttpRequests;
import com.matrix.test.firebase.hw_salesp.Connections.JsonParser;
import com.matrix.test.firebase.hw_salesp.utils.Admin;
import com.matrix.test.firebase.hw_salesp.utils.DataHandler;
import com.matrix.test.firebase.hw_salesp.utils.LoginManager;
import com.matrix.test.firebase.hw_salesp.utils.Region;
import com.matrix.test.firebase.hw_salesp.utils.SalesPerson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LoginManager loginManger;
    ProgressDialog pDialog;
    EditText edtUsername, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin=findViewById(R.id.btn_login);
        edtUsername=findViewById(R.id.edttxt_UName);
        edtPassword=findViewById(R.id.edttxt_UPass);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginSync().execute();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginManger = LoginManager.getInstance(getApplicationContext());

        loginManger.ifUserLoggedIng();
    }

    class LoginSync extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setCancelable(false);

            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            String url = HttpRequests.getLogin();

            String username = edtUsername.getText().toString().trim().toLowerCase();
            String password = edtPassword.getText().toString();

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair(Admin.USERNAME, username));

            params.add(new BasicNameValuePair(Admin.PASSWORD, password));

            JsonParser jsonParser = new JsonParser();
            JSONObject data = jsonParser.makeHttpRequest(url, "POST", params);
            return data;

        }

        @Override
        protected void onPostExecute(JSONObject data) {
            pDialog.hide();
            try {
                boolean isLogin = data.getBoolean(Admin.LOGIN);
                if (isLogin) {
                    boolean isAdmin = data.getBoolean(Admin.ADMIN);
                    if (!isAdmin) {
                        int id=data.getInt(SalesPerson.PERSON_ID);
                        int regID=data.getInt(SalesPerson.REGION_ID);
                        String name=data.getString(SalesPerson.FULL_NAME);
                        String pic=data.getString(SalesPerson.PICTURE);
                        String regName=data.getString(Region.REGION_NAME);

                        String personID=String.valueOf(id);
                        String regionID=String.valueOf(regID);
                        DataHandler.getInstance().setPerson(new SalesPerson(id,"","",name,0,0,pic,regID,regName));
                        loginManger.login(personID,regionID, isAdmin,regName,pic,name);
                    }else{
                        loginManger.loginAmin();
                    }



                } else {
                    viewError("Login Authentication error");
                }
            } catch (Exception e) {
                viewError("error : " + e.getMessage());
            }
        }
    }

    private void viewError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}