package com.matrix.test.firebase.hw_salesp.Connections;


/**
 * Created by Matrix on 11/13/2018.
 */

public class HttpRequests {
    public static final String API="http://mwt.somee.com/";
    public static final String ERROR="Error";



    public static String getCommissionDetails(String id) {
        return API+"Admin/CommDetailsAndroid/"+id;
    }

    public static String getCommissions(String id) {
        return API+"Admin/GetPersonsComissionsAndroid/"+id;
    }

    public static String getCommissionsByDate() {
        return API+"Admin/GetComissionsByDateAndroid";
    }

    public static String ComputeCommisssion() {
        return API+"SalesPerson/CalculateComissionAndroid";
    }

    public static String CreateSalesPerson() {
        return API+"Admin/AddSalesPersonAndroid";
    }

    public static String updateSalesPerson() {
        return API+"Admin/EditAndroid";
    }

    public static String getSalesPersons() {
        return API+"Admin/GetSalesPersonsAndroid";
    }


    public static String deletePerson(){ return API+"Admin/DeletePersonAndroid"; }


    public static String editPerson(){
        return API+"Admin/EditAndroid";
    }

    public static String getLogin() {
        return API+"Login/LoginAndroid";
    }

    public static String getPicture(String picture) {
        return API+"/"+picture;
    }
}