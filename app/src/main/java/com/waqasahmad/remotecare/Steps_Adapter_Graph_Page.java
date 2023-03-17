package com.waqasahmad.remotecare;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Steps_Adapter_Graph_Page extends RecyclerView.Adapter<Steps_Adapter_Graph_Page.MyViewHolder>
{

    List<Steps_Model_Graph_Page> ls_doc;
    Context c_doc;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String currentemail="";


    public Steps_Adapter_Graph_Page(List<Steps_Model_Graph_Page> ls_doc, Context c_doc)
    {
        this.ls_doc = ls_doc;
        this.c_doc = c_doc;
    }


    @NonNull
    @Override
    public Steps_Adapter_Graph_Page.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(c_doc).inflate(R.layout.steps_row_graph_page, parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //---------------

//        if(holder.rem_steps.getcharAt(0)=='-')
        Log.d("helllooo",String.valueOf(ls_doc.get(position).getRemaining_steps()));
        if(ls_doc.get(position).getRemaining_steps().charAt(0)=='-')
        {
           Log.d("helllooo","sadasd");
            holder.doctor_name.setText(ls_doc.get(position).getD_email());
            holder.recomm_steps.setText(ls_doc.get(position).getRecommended_steps());
            holder.rem_steps.setText("Remaining Steps : 0  \nYou have completed the goal");


        }
        else
        {
            holder.doctor_name.setText(ls_doc.get(position).getD_email());
            holder.recomm_steps.setText(ls_doc.get(position).getRecommended_steps());
            holder.rem_steps.setText("Remaining Steps: "+ls_doc.get(position).getRemaining_steps());
        }

        //









    }



    @Override
    public int getItemCount() {
        return ls_doc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView doctor_name,recomm_steps,rem_steps;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            doctor_name=itemView.findViewById(R.id.d_name_steps_screen);
            recomm_steps=itemView.findViewById(R.id.recommended_steps_steps_screen);
            rem_steps=itemView.findViewById(R.id.remaining_steps_steps_screen);
        }
    }




}
