package com.example.carrentalapp;

public class User {

    public String name,email,birthday,phone_number,Username,Address;
    public User()
    {

    }
    public User(String Fullname,String Email,String DOB,String Contact_number,String username,String address)
    {
        this.name = Fullname;
        this.email=Email;
        this.birthday=DOB;
        this.phone_number=Contact_number;
        this.Username=username;
        this.Address = address;
    }
}
