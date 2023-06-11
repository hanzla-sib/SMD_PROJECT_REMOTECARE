package com.waqasahmad.remotecare;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Steps_Adapter_Graph_Page extends RecyclerView.Adapter<Steps_Adapter_Graph_Page.MyViewHolder> {

    List<Steps_Model_Graph_Page> ls_doc;
    Context c_doc;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String currentemail = "";


    public Steps_Adapter_Graph_Page(List<Steps_Model_Graph_Page> ls_doc, Context c_doc) {
        this.ls_doc = ls_doc;
        this.c_doc = c_doc;
    }


    @NonNull
    @Override
    public Steps_Adapter_Graph_Page.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate the layout for each item in the RecyclerView
        View row = LayoutInflater.from(c_doc).inflate(R.layout.steps_row_graph_page, parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // This method binds the data to the views of each item in the RecyclerView

        // Check if the remaining steps is negative
        if (ls_doc.get(position).getRemaining_steps().charAt(0) == '-') {
            holder.doctor_name.setText(ls_doc.get(position).getD_email());
            holder.recomm_steps.setText(ls_doc.get(position).getRecommended_steps());
            holder.rem_steps.setText("Remaining Steps : 0  ");
            holder.message.setText("You have completed the goal");

        } else {
            holder.doctor_name.setText(ls_doc.get(position).getD_email());
            holder.recomm_steps.setText(ls_doc.get(position).getRecommended_steps());
            holder.rem_steps.setText("Remaining Steps: " + ls_doc.get(position).getRemaining_steps());
            holder.message.setText("Little more steps to achieve the goal");

        }
    }

    @Override
    public int getItemCount() {

        // Return the number of items in the RecyclerView
        return ls_doc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView doctor_name, recomm_steps, rem_steps, message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the views for each item in the RecyclerView
            doctor_name = itemView.findViewById(R.id.d_name_steps_screen);
            recomm_steps = itemView.findViewById(R.id.recommended_steps_steps_screen);
            rem_steps = itemView.findViewById(R.id.remaining_steps_steps_screen);
            message = itemView.findViewById(R.id.message);

        }
    }
}