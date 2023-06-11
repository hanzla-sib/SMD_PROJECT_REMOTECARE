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

public class Steps_adapter_graph_doctor_side extends RecyclerView.Adapter<Steps_adapter_graph_doctor_side.MyViewHolder> {
    List<Steps_Model_doctor_side_formula> ls_doc;
    Context c_doc;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String currentemail = "";

    public Steps_adapter_graph_doctor_side() {
        this.ls_doc = ls_doc;
        this.c_doc = c_doc;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate the layout for each item in the RecyclerView
        View row = LayoutInflater.from(c_doc).inflate(R.layout.steps_row_graph_page, parent, false);
        return new Steps_adapter_graph_doctor_side.MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        // Return the number of items in the RecyclerView
        return 0;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView doctor_name, recomm_steps, rem_steps;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the views for each item in the RecyclerView
            doctor_name = itemView.findViewById(R.id.d_name_steps_screen);
            recomm_steps = itemView.findViewById(R.id.recommended_steps_steps_screen);
            rem_steps = itemView.findViewById(R.id.remaining_steps_steps_screen);
        }
    }
}