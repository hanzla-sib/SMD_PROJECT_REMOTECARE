package com.waqasahmad.remotecare;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class P_pending_appointments_adapter extends RecyclerView.Adapter<P_pending_appointments_adapter.MyViewHolder>
{
    List<Appointment_Model> ls_doc;
    Context c_doc;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String currentemail="";
    String currentname="";
    String url1="";

    String doc_email;


    public P_pending_appointments_adapter(List<Appointment_Model> ls_doc, Context c_doc)
    {
        this.ls_doc = ls_doc;
        this.c_doc = c_doc;
        doc_email="";
        SharedPreferences sh = c_doc.getSharedPreferences("MySharedPref", 0);
        String s1 = sh.getString("Ip", "");
        url1 ="http://"+s1+"/smd_project/delete_pending_patient.php";
    }
    public void setfilterlist(List<Appointment_Model> filteredlist){
        this.ls_doc=filteredlist;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public P_pending_appointments_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(c_doc).inflate(R.layout.patient_row_pending_appointment, parent, false);
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
        holder.doctor_name.setText("Dr. " + ls_doc.get(position).getName_doc());
        holder.doc_type.setText(ls_doc.get(position).getDoc_type());

        SharedPreferences sh = c_doc.getSharedPreferences("MySharedPref", 0);
        String s1 = sh.getString("Ip", "");
        if(ls_doc.get(position).getImage_doc().equals("null")){

        }
        else {
            Picasso.get().load("http://"+s1+"/smd_project/"+ls_doc.get(position).getImage_doc()).into(holder.img);
        }


        holder.cross1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(c_doc);
                builder.setMessage("Do you want to delete this pending appointment?")
                        .setTitle("Delete Pending Appointment");

                // Add the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // User clicked OK button


                        doc_email= ls_doc.get(holder.getAdapterPosition()).email_doc;

                        //removing row
                        int i=holder.getAdapterPosition();
                        ls_doc.remove(i);
                        notifyItemRemoved(i);
                        //

                        StringRequest request=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                Toast.makeText(c_doc,response.toString(),Toast.LENGTH_LONG).show();

                            }
                        }, new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                            }
                        })
                        {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> param=new HashMap<String,String>();

                                param.put("p_email",currentemail);
                                param.put("d_email",doc_email);

                                return param;
                            }
                        };
                        RequestQueue queue= Volley.newRequestQueue(c_doc);
                        queue.add(request);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });

                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
    }


    @Override
    public int getItemCount() {
        return ls_doc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView doctor_name,doc_type;
        CircleImageView img;
        RelativeLayout cross1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doctor_name=itemView.findViewById(R.id.doc_name2);
            img=itemView.findViewById(R.id.doc_img);
            doc_type=itemView.findViewById(R.id.doc_prof);
            cross1 = itemView.findViewById(R.id.cross);


        }
    }




}
