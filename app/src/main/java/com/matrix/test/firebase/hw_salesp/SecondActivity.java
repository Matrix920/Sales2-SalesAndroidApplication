package com.matrix.test.firebase.hw_salesp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.matrix.test.firebase.hw_salesp.utils.LoginManager;
import com.matrix.test.firebase.hw_salesp.utils.SalesPerson;

public class SecondActivity extends AppCompatActivity {

    Button btnAdd,btnPerson,btnAdminPersons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btnAdd=findViewById(R.id.btnAdminAdd);
        btnAdminPersons=findViewById(R.id.btnAdminPersons);
        btnPerson=findViewById(R.id.btnSalesPerson);

        if(LoginManager.getInstance(getApplicationContext()).isAdmin()){
            btnAdminPersons.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.VISIBLE);
            btnPerson.setVisibility(View.GONE);
        }else{
            btnAdminPersons.setVisibility(View.GONE);
            btnAdd.setVisibility(View.GONE);
            btnPerson.setVisibility(View.VISIBLE);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SecondActivity.this,NewSalesPersonActivity.class);
                startActivity(i);
            }
        });

        btnPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SecondActivity.this,forth2Activity.class);
                startActivity(i);
            }
        });

        btnAdminPersons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SecondActivity.this, SalesPersonActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:{
                LoginManager.getInstance(getApplicationContext()).clearAndLogout();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
