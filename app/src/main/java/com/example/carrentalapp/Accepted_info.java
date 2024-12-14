package com.example.carrentalapp;

public class Accepted_info {

    public String Name,From_City,From_Address,To_City,To_Address,Arrival_Date,Arrival_Time,Additonal_Conatact_Number,Vehicle,Contact_Number,Vehicle_size;

        public Accepted_info()
        {

        }
        public Accepted_info(String Name,String From_City,String To_City,String From_Address,String To_Address,String Arrival_Date,String Arrival_Time,String Vehicle,String Vehicle_size,String Contact_Number,String Additonal_Conatact_Number)
        {
            this.Name = Name;
            this.From_City = From_City;
            this.From_Address = From_Address;
            this.To_City = To_City;
            this.To_Address = To_Address;
            this.Arrival_Date = Arrival_Date;
            this.Arrival_Time = Arrival_Time;
            this.Vehicle = Vehicle;
            this.Vehicle_size = Vehicle_size ;
            this.Contact_Number = Contact_Number;
            this.Additonal_Conatact_Number = Additonal_Conatact_Number;
        }


}
