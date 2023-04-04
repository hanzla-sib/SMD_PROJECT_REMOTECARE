package com.waqasahmad.remotecare;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class P_accepted_appointments_adapter extends RecyclerView.Adapter<P_accepted_appointments_adapter.MyViewHolder>
{
    List<Appointment_Model> ls_doc;
    Context c_doc;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String currentemail="";
    String currentname="";



    public P_accepted_appointments_adapter(List<Appointment_Model> ls_doc, Context c_doc)
    {
        this.ls_doc = ls_doc;
        this.c_doc = c_doc;
    }
    public void setfilterlist(List<Appointment_Model> filteredlist)
    {
        this.ls_doc=filteredlist;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public P_accepted_appointments_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = LayoutInflater.from(c_doc).inflate(R.layout.patient_row_accept_pending, parent, false);
        return new P_accepted_appointments_adapter.MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull P_accepted_appointments_adapter.MyViewHolder holder, int position)
    {

        //Initializing Firebase MAuth instance
        mAuth=FirebaseAuth.getInstance();

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();

        //getting email of logged in user
        currentemail = mAuth.getCurrentUser().getEmail();

        //
        holder.doctor_name.setText("Dr. " + ls_doc.get(position).getName_doc());
        holder.doctor_time.setText(ls_doc.get(position).getTime_doc());
        holder.doctor_date.setText(ls_doc.get(position).getDate_doc());
        holder.doc_prof.setText(ls_doc.get(position).getDoc_type());
        SharedPreferences sh = c_doc.getSharedPreferences("MySharedPref", 0);
        String s1 = sh.getString("Ip", "");
        Picasso.get().load("http://"+s1+"/smd_project/"+ls_doc.get(position).getImage_doc()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return ls_doc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView doctor_name,doctor_email, doctor_date, doctor_time,doc_prof;
        CircleImageView img;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doctor_name=itemView.findViewById(R.id.doc_name2);
            doctor_date = itemView.findViewById(R.id.doc_date);
            doctor_time = itemView.findViewById(R.id.doc_time);
            doc_prof=itemView.findViewById(R.id.doc_prof);
            img=itemView.findViewById(R.id.doc_img);


        }
    }

}
