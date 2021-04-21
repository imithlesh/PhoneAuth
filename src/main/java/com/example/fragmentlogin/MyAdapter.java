package com.example.fragmentlogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fragmentlogin.model.DataClass;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public void setDataClassArrayList(ArrayList<DataClass> dataClassArrayList) {
        this.dataClassArrayList = dataClassArrayList;
        notifyDataSetChanged();
    }

    ArrayList<DataClass> dataClassArrayList;
    Context context;

    public MyAdapter(ArrayList<DataClass> dataClassArrayList, Context context) {
        this.dataClassArrayList = dataClassArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rec_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.fname.setText(dataClassArrayList.get(position).getFname());
        holder.lname.setText(dataClassArrayList.get(position).getLname());
        holder.city.setText(dataClassArrayList.get(position).getCity());
        holder.nmbr.setText(dataClassArrayList.get(position).getAbout());
    }

    @Override
    public int getItemCount() {
        return dataClassArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fname, lname, city, nmbr;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fname = itemView.findViewById(R.id.firstIdn);
            lname = itemView.findViewById(R.id.secondIDn);
            city = itemView.findViewById(R.id.cityIDView);
            nmbr = itemView.findViewById(R.id.nmbrIDView);
        }
    }
}
