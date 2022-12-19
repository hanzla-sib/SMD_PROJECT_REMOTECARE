package com.waqasahmad.remotecare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class P_pending_appointments_adapter extends RecyclerView.Adapter<P_pending_appointments_adapter.MyViewHolder>
{
    List<Appointment_Model> ls_doc;
    Context c_doc;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String currentemail="";
    String currentname="";



    public P_pending_appointments_adapter(List<Appointment_Model> ls_doc, Context c_doc)
    {
        this.ls_doc = ls_doc;
        this.c_doc = c_doc;
    }

    @NonNull
    @Override
    public P_pending_appointments_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(c_doc).inflate(R.layout.patient_row_accept_pending, parent, false);
        return new P_pending_appointments_adapter.MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull P_pending_appointments_adapter.MyViewHolder holder, int position)
    {

        //Initializing Firebase MAuth instance
        mAuth=FirebaseAuth.getInstance();

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();

        //getting email of logged in user
        currentemail = mAuth.getCurrentUser().getEmail();

        //
        holder.doctor_name.setText(ls_doc.get(position).getName_doc());
        holder.doctor_email.setText(ls_doc.get(position).getEmail_doc());
    }

    @Override
    public int getItemCount() {
        return ls_doc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView doctor_name,doctor_email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doctor_name=itemView.findViewById(R.id.doc_name2);
            doctor_email=itemView.findViewById(R.id.doc_email2);
        }
    }




}
