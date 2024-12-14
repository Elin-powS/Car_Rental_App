package com.example.carrentalapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Admin_panel_adapter extends RecyclerView.Adapter<Admin_panel_adapter.ViewHolder> {

    private static ClickListener clickListener;
    Context context;
    ArrayList<Admin_model> arrAdmin_model;

    public Admin_panel_adapter(Context context,ArrayList<Admin_model> arrAdmin_model)
    {
        this.context = context;
        this.arrAdmin_model=arrAdmin_model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.admin__panel_card,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Admin_model model = (Admin_model)  arrAdmin_model.get(position);

        holder.img.setImageResource(arrAdmin_model.get(position).img);
        holder.title.setText(arrAdmin_model.get(position).Tittle);
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
        return arrAdmin_model.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView img;

        public ViewHolder(View view)
        {
            super(view);

            title = view.findViewById(R.id.title);
            img = view.findViewById(R.id.img);
        }
    }
    public interface ClickListener{
        View.OnClickListener onItemClick(int position, View view);
    }

    public static void setOnItemClickLister(Admin_panel_adapter.ClickListener clickListener)
    {
        Admin_panel_adapter.clickListener = clickListener;
    }
}
