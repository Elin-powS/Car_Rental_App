package com.example.carrentalapp;

import com.google.firebase.database.Exclude;

public class reservations_info_show {
    @Exclude
    String key;
    String Name,From_City,From_Address,To_City,To_Address,Arrival_Date,Arrival_Time,Additonal_Conatact_Number,Vehicle,Contact_Number,Vehicle_size,UserID;

    public String getName() {
        return Name;
    }

    public String getVehicle() {
        return Vehicle;
    }

    public String getVehicle_size() {
        return Vehicle_size;
    }

    public String getContact_Number() {
        return Contact_Number;
    }

    public String getAdditonal_Conatact_Number() {
        return Additonal_Conatact_Number;
    }

    public String getFrom_City() {
        return From_City;
    }

    public String getTo_City() {
        return To_City;
    }

    public String getFrom_Address() {
        return From_Address;
    }

    public String getTo_Address() {
        return To_Address;
    }
    public String getArrival_Date() {
        return Arrival_Date;
    }

    public String getArrival_Time() {
        return Arrival_Time;
    }

    public String getUserID() {
        return UserID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
