package com.matrix.test.firebase.hw_salesp.utils;

import java.util.Date;

public class SalesPerson {
    public static final String SALES_PERSON="SalesPerson";
    public static final String PERSON_ID="PersonID";
    public static final String USERNAME="Username";
    public static final String PASSWORD="Password";
    public static final String FULL_NAME="FullName";
    public static final String PICTURE="Picture";
    public static final String REGION_ID="RegionID";
    public static final String MONTH="Month";
    public static final String YEAR="Year";
    public static final String REGISTERATION_DATE="regDate";

    public int personID;
    public String username;
    public String password;
    public String fullname;
    public int month;
    public int year;
    public String picture;
    public int regionID;
    public Date registerationDate;
    public String regionName;

    public SalesPerson(int personID, String username, String password, String fullname, int month, int year, String picture, int regionID, Date registerationDate) {
        this.personID = personID;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.month = month;
        this.year = year;
        this.picture = picture;
        this.regionID = regionID;
        this.registerationDate = registerationDate;
    }

    public SalesPerson(int personID, String username, String password, String fullname, int month, int year, String picture, int regionID, String regionName) {
        this.personID = personID;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.month = month;
        this.year = year;
        this.picture = picture;
        this.regionID = regionID;
        this.regionName=regionName;
    }
}
