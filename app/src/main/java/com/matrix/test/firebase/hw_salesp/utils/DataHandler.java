package com.matrix.test.firebase.hw_salesp.utils;

import android.util.Log;


import java.util.List;

/**
 * Created by Matrix on 11/28/2018.
 */

public class DataHandler {
    private static DataHandler dataHandler;
    private SalesPerson person;
    private List<SalesPerson>salesPersons;
    private List<Comission>commisssions;

    public SalesPerson getPerson(int personID){
        for(SalesPerson person:salesPersons){
            if(person.personID==personID)
                return person;
        }
        return null;
    }
    public static final String TAG=DataHandler.class.getSimpleName();

    private DataHandler(){

    }

    public SalesPerson getPerson() {
        return person;
    }

    public void setPerson(SalesPerson person) {
        this.person = person;
    }

    public boolean isSalesPersonsEmpty(){
        return salesPersons==null||salesPersons.isEmpty();
    }

    public static DataHandler getInstance(){
        if(dataHandler==null){
            Log.e(TAG,"initialize");
            dataHandler=new DataHandler();
            return dataHandler;

        }
        Log.e(TAG,"return");
        return dataHandler;
    }

    public List<Comission> getCommisssions() {
        return commisssions;
    }

    public void setSalesPersons(List<SalesPerson> salesPersons) {
        this.salesPersons = salesPersons;
    }

    public List<SalesPerson> getSalesPersons() {
        return salesPersons;
    }

    public void clear() {
        salesPersons.clear();
    }

    public void setCommisssions(List<Comission> commissionsList) {
        this.commisssions=commissionsList;
    }
}
