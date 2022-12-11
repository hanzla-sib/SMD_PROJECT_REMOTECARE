package com.waqasahmad.remotecare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {
    ArrayList<userchat> ls;
    Context c;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;


    public UsersAdapter (ArrayList<userchat> ls, Context c) {
        this.ls = ls;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(c).inflate(R.layout.messageall,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            if(ls.get(position).getOnlinestatus().equals("offline")){
                holder.name.setText(ls.get(position).getName());
                holder.lastseen.setText(ls.get(position).getLastseen());
                holder.onlinestatus.setVisibility(View.GONE);
            }
            else{
                holder.name.setText(ls.get(position).getName());
                holder.lastseen.setVisibility(View.GONE);
                holder.onlinestatus.setText(ls.get(position).getOnlinestatus());

            }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c , Activitychat.class);
                int i = holder.getAdapterPosition();
                intent.putExtra("name" , ls.get(i).getName());
                intent.putExtra("uid" , ls.get(i).getUid());
                intent.putExtra("p_id",ls.get(i).getP_id());
                c.startActivity(intent);
            }
        });





    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView onlinestatus;
        TextView lastseen;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.nameid);
            onlinestatus=itemView.findViewById(R.id.onlinestatus);
            lastseen=itemView.findViewById(R.id.lastseen);


        }
    }
}