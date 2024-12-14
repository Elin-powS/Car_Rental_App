package com.example.carrentalapp;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class A_User_Adapter extends RecyclerView.Adapter<A_User_Adapter.ViewHolder> {

    Context context;
    ArrayList<A_User_info_show>list;

    public A_User_Adapter(Context context, ArrayList<A_User_info_show>list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.a_user_profile_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        A_User_info_show user = list.get(position);

        holder.Name.setText(user.getname());
        holder.Username.setText(user.getUsername());
        holder.Email.setText(user.getemail());
        holder.address.setText(user.getAddress());
        holder.Phone_number.setText(user.getphone_number());
        holder.Birthday.setText(user.getBirthday());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        TextView Name,Username,Email,Phone_number,address,Birthday;

        public ViewHolder(View view){

            super(view);

            Name = view.findViewById(R.id.name);
            Email= view.findViewById(R.id.email);
            address= view.findViewById(R.id.address);
            Phone_number=view.findViewById(R.id.phone_number);
            Birthday = view.findViewById(R.id.birthday);
            Username= view.findViewById(R.id.username);

        }

    }

}
