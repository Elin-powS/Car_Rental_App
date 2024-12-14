package com.example.carrentalapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Recycler_contact_Adapter extends RecyclerView.Adapter<Recycler_contact_Adapter.ViewHolder>  {

    private static ClickListener clickListener;
    Context context;
    ArrayList<contact_model> arrContact_model;
    Recycler_contact_Adapter(Context context, ArrayList<contact_model> arrContact_model){
        this.context = context;
        this.arrContact_model=arrContact_model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =  LayoutInflater.from(context).inflate(R.layout.vehicle_type_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        contact_model model = (contact_model) arrContact_model.get(position);

        holder.vehicle_img.setImageResource(arrContact_model.get(position).img);
        holder.vehicle_nam.setText(arrContact_model.get(position).vehicle_nam);
        holder.vehicle_size.setText(arrContact_model.get(position).vehicle_siz);

        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        clickListener.onItemClick(position,null);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return arrContact_model.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView vehicle_nam,vehicle_size;
        ImageView vehicle_img;
        LinearLayout vehicle_info;

        public ViewHolder(View view){
            super(view);

            vehicle_nam = view.findViewById(R.id.vehicle_type);
            vehicle_size = view.findViewById(R.id.vehicle_size);
            vehicle_img = view.findViewById(R.id.vehicle_image);
            vehicle_info = view.findViewById(R.id.vehicle_info);

        }
    }

    public interface ClickListener{
        View.OnClickListener onItemClick(int position, View view);
    }

    public static void setOnItemClickListener(ClickListener clickListener)
    {
        Recycler_contact_Adapter.clickListener=clickListener;
    }
}
