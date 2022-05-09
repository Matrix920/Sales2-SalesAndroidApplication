package com.matrix.test.firebase.hw_salesp.utils;

import java.util.Date;

public class CommissionDetails {

    public int personNumber;
    public int personName;
    public int month;
    public int year;
    public Date registerationDate;
    public double south;
    public double costal;
    public double east;
    public double north;
    public double lebanon;
    public double monthlyCommission;

    public CommissionDetails(int personNumber, int personName, int month, int year, Date registerationDate, double south, double costal, double east, double north, double lebanon, double monthlyCommission) {
        this.personNumber = personNumber;
        this.personName = personName;
        this.month = month;
        this.year = year;
        this.registerationDate = registerationDate;
        this.south = south;
        this.costal = costal;
        this.east = east;
        this.north = north;
        this.lebanon = lebanon;
        this.monthlyCommission = monthlyCommission;
    }
}
