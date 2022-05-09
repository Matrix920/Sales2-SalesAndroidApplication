package com.matrix.test.firebase.hw_salesp.utils;

import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.matrix.test.firebase.hw_salesp.MainActivity;
import com.matrix.test.firebase.hw_salesp.SecondActivity;

/**
 * Created by Matrix on 12/11/2018.
 */

public class LoginManager {

    private Context context;

    private SharedPreferences sharedPref;
    public static final String SHARED_PREF_NAME="sales";
    private static final int PRIVATE_MODE=0;

    public static final String IS_ADMIN="is admin";

    private static final String IS_LOGIN="login";

    SharedPreferences.Editor editor;

    private static LoginManager loginManager;

    private LoginManager(Context context){
        this.context=context;
        sharedPref=context.getSharedPreferences(SHARED_PREF_NAME,PRIVATE_MODE);
        editor=sharedPref.edit();
    }

    public static LoginManager getInstance(Context context){
        if(loginManager==null){
            loginManager=new LoginManager(context);
        }

        return loginManager;
    }

    //logout
    public void clearAndLogout(){
        editor.clear();
        editor.commit();

        //go to lgoin
        startLoginActivity();
    }

    public String getPlugedNumber(){
        return "";// sharedPref.getString(VehicleLog.PLUGED_NUMBER,"");
    }


    public void login(String personID,String regionID,boolean isAdmin,String regName,String pic,String name){
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(SalesPerson.PERSON_ID,personID);
        editor.putString(Region.REGION_NAME,regName);
        editor.putString(SalesPerson.FULL_NAME,name);
        editor.putString(Region.REGION_ID,regionID);
        editor.putString(SalesPerson.PICTURE,pic);
        editor.putBoolean(IS_ADMIN,isAdmin);
        editor.commit();
        startMainActivity();
    }

    public String getRegionName(){
        return sharedPref.getString(Region.REGION_NAME,"");
    }

    public String getPicure(){
        return sharedPref.getString(SalesPerson.PICTURE,"");
    }

    public String getname(){
        return sharedPref.getString(SalesPerson.FULL_NAME,"");
    }

    private void startLoginActivity(){
        Intent i=new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    private void startMainActivity(){

        Intent i=new Intent(context, SecondActivity.class);
        //i.putExtra(VehicleLog.PLUGED_NUMBER,getPlugedNumber());
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public  boolean isAdmin() {
        boolean isAdmin = sharedPref.getBoolean(IS_ADMIN, false);
        return isAdmin;
    }

    public  String getRegionID() {
        return  sharedPref.getString(Region.REGION_ID,"");
    }

    public  String getPersonNumber() {
        return  sharedPref.getString(SalesPerson.PERSON_ID,"");
    }

    public void ifUserLoggedIng(){
        boolean isLogin= sharedPref.getBoolean(IS_LOGIN,false);

        if(isLogin){
            startMainActivity();
        }
    }

    public void ifUserLoggedOut(){
        boolean isLogin= sharedPref.getBoolean(IS_LOGIN,false);

        if(! isLogin){
            startLoginActivity();
        }
    }

    public void loginAmin() {
        editor.putBoolean(IS_LOGIN,true);
        editor.putBoolean(IS_ADMIN,true);
        editor.commit();
        startMainActivity();
    }

    public void loggout() {
    }
}
