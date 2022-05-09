package com.matrix.test.firebase.hw_salesp.utils;

import java.util.Date;

public class Comission {
    public static final String COMISSION="Comission";
    public static final String PERSON_ID="PersonID";
    public static final String COMISSION_ID="ComissionID";
    public static final String COMMISION_ID="CommisionID";
    public static final String DATE="Date";
    public static final String COMM="Comm";

    public int comissionID;
    public Date date;
    public int month;
    public int year;
    public double comm;

    public Comission(int comissionID, int month,int year, double comm) {
        this.comissionID = comissionID;
        this.month=month;
        this.year=year;
        this.comm = comm;
    }
}
