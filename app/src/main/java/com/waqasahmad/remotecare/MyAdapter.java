package com.waqasahmad.remotecare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    List<MyModel> ls;
    Context c;

    public MyAdapter(List<MyModel> ls, Context c)
    {
        this.ls = ls;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = LayoutInflater.from(c).inflate(R.layout.row, parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.name.setText(ls.get(position).getName());
        holder.calorie.setText(ls.get(position).getCalorie());
    }

    @Override
    public int getItemCount()
    {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, calorie;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.name2);
            calorie=itemView.findViewById(R.id.calorie2);
        }
    }

}
