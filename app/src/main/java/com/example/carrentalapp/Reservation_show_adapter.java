package com.example.carrentalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Reservation_show_adapter extends RecyclerView.Adapter<Reservation_show_adapter.MyViewHolder> {

    private static LongClickListener longClickListener ;
    Context context;

    ArrayList<reservations_info_show>list;

    public Reservation_show_adapter(Context context,ArrayList<reservations_info_show> list) {
        this.context = context;
        this.list = list;
    }


    public reservations_info_show getItemAt(int pos){
        try {
            return list.get(pos);
        }catch (Exception e){
            return null;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.reservation_info_card,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {

        reservations_info_show user = list.get(position);

        holder.vehicle.setText(user.getVehicle());
        holder.vehicle_siz.setText(user.getVehicle_size());
        holder.name.setText(user.getName());
        holder.from.setText(user.getFrom_City());
        holder.to.setText(user.getTo_City());
        holder.from_ad.setText(user.getFrom_Address());
        holder.to_ad.setText(user.getTo_Address());
        holder.date.setText(user.getArrival_Date());
        holder.time.setText(user.getArrival_Time());
        holder.contact1.setText(user. getContact_Number());
        holder.contact2.setText(user.getAdditonal_Conatact_Number());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longClickListener.onItemClick(holder.getAdapterPosition(),null);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView vehicle,vehicle_siz,name,from,to,from_ad,to_ad,date,time,contact1,contact2;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            vehicle = itemView.findViewById(R.id.veh);
            vehicle_siz = itemView.findViewById(R.id.veh_size);
            name = itemView.findViewById(R.id.name);
            from = itemView.findViewById(R.id.From);
            from_ad = itemView.findViewById(R.id.From_Add);
            to = itemView.findViewById(R.id.To);
            to_ad = itemView.findViewById(R.id.To_Add);
            date= itemView.findViewById(R.id.Date);
            time = itemView.findViewById(R.id.Time);
            contact1= itemView.findViewById(R.id.contact1);
            contact2= itemView.findViewById(R.id.contact2);

        }
    }

    public interface LongClickListener{
        View.OnLongClickListener  onItemClick(int position, View view);
    }

    public void setOnItemClickListener(Reservation_show_adapter.LongClickListener longClickListener)
    {
        Reservation_show_adapter.longClickListener= longClickListener;
    }

}
