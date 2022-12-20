package com.waqasahmad.remotecare;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Doc_Appointments_Accepted_Adapter extends RecyclerView.Adapter<Doc_Appointments_Accepted_Adapter.MyViewHolder>
{

    List<Doc_Appointment_Model> ls_doc2;
    Context c_doc2;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    //private

    private static final String doctor_appointment_accepted="http://"+Ip_server.getIpServer()+"/smd_project/doctor_appointment_accepted.php";

    public Doc_Appointments_Accepted_Adapter(List<Doc_Appointment_Model> ls_doc2, Context c_doc2) {
        this.ls_doc2 = ls_doc2;
        this.c_doc2 = c_doc2;
    }

    String currentemail;

    @NonNull
    @Override
    public Doc_Appointments_Accepted_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(c_doc2).inflate(R.layout.patient_row_accepted_appointments, parent, false);
        return new Doc_Appointments_Accepted_Adapter.MyViewHolder(row);
    }


    @Override
    public void onBindViewHolder(@NonNull Doc_Appointments_Accepted_Adapter.MyViewHolder holder, int position) {

        //Initializing Firebase MAuth instance
        mAuth = FirebaseAuth.getInstance();

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();

        //getting email of logged in user
        currentemail = mAuth.getCurrentUser().getEmail();

        holder.patient_name.setText(ls_doc2.get(position).getName_patient());
        holder.patient_email.setText(ls_doc2.get(position).getEmail_patient());
        int i=position;

    }

    @Override
    public int getItemCount() {
        return ls_doc2.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView patient_name,patient_email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            patient_name=itemView.findViewById(R.id.patient_name_accepted);
            patient_email=itemView.findViewById(R.id.patient_email_accepted);
        }
    }
}







