package com.waqasahmad.remotecare;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class P_accepted_appointments_adapter extends RecyclerView.Adapter<P_accepted_appointments_adapter.MyViewHolder> {
    List<Appointment_Model> ls_doc;
    Context c_doc;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String currentemail = "";
    String currentname = "";
    String doc_email;
    String url1 = "";

    public P_accepted_appointments_adapter(List<Appointment_Model> ls_doc, Context c_doc) {
        this.ls_doc = ls_doc;
        this.c_doc = c_doc;
        doc_email = "";

        // Retrieve the IP address from SharedPreferences
        SharedPreferences sh = c_doc.getSharedPreferences("MySharedPref", 0);
        String s1 = sh.getString("Ip", "");
        url1 = "http://" + s1 + "/smd_project/delete_pending_patient.php";

    }

    // Method to set the filtered list for filtering appointments
    public void setfilterlist(List<Appointment_Model> filteredlist) {
        this.ls_doc = filteredlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public P_accepted_appointments_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate the layout for the appointment row
        View row = LayoutInflater.from(c_doc).inflate(R.layout.patient_row_accept_pending, parent, false);
        return new P_accepted_appointments_adapter.MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull P_accepted_appointments_adapter.MyViewHolder holder, int position) {

        //Initializing Firebase MAuth instance
        mAuth = FirebaseAuth.getInstance();

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();

        //getting email of logged-in user
        currentemail = mAuth.getCurrentUser().getEmail();

        // Set the appointment details for each row
        holder.doctor_name.setText("Dr. " + ls_doc.get(position).getName_doc());
        holder.doctor_time.setText(ls_doc.get(position).getTime_doc());
        holder.doctor_date.setText(ls_doc.get(position).getDate_doc());
        holder.doc_prof.setText(ls_doc.get(position).getDoc_type());

        // Load the doctor's image using Picasso library
        SharedPreferences sh = c_doc.getSharedPreferences("MySharedPref", 0);
        String s1 = sh.getString("Ip", "");
        if (ls_doc.get(position).getImage_doc().equals("null")) {

        } else {
            Picasso.get().load("http://" + s1 + "/smd_project/" + ls_doc.get(position).getImage_doc()).into(holder.img);
        }

        // Set click listener for the cross icon to cancel the appointment
        holder.cross1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Display confirmation dialog before canceling the appointment
                AlertDialog.Builder builder = new AlertDialog.Builder(c_doc);
                builder.setMessage("Do you want to cancel this appointment?")
                        .setTitle("Cancel Appointment");

                // Add the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        doc_email = ls_doc.get(holder.getAdapterPosition()).email_doc;

                        // Remove the row from the list and update the UI
                        int i = holder.getAdapterPosition();
                        ls_doc.remove(i);
                        notifyItemRemoved(i);

                        // Make a request to delete the appointment
                        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                // Set the parameters for the request
                                Map<String, String> param = new HashMap<String, String>();
                                param.put("p_email", currentemail);
                                param.put("d_email", doc_email);

                                return param;
                            }
                        };

                        // Add the request to the queue
                        RequestQueue queue = Volley.newRequestQueue(c_doc);
                        queue.add(request);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });

                // Create and display the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return ls_doc.size();
    }

    // View holder class for the appointment row
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView doctor_name, doctor_email, doctor_date, doctor_time, doc_prof;
        CircleImageView img;
        ImageView cross1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doctor_name = itemView.findViewById(R.id.doc_name2);
            doctor_date = itemView.findViewById(R.id.doc_date);
            doctor_time = itemView.findViewById(R.id.doc_time);
            doc_prof = itemView.findViewById(R.id.doc_prof);
            img = itemView.findViewById(R.id.doc_img);
            cross1 = itemView.findViewById(R.id.cross1);

        }
    }

}
