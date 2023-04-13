package com.waqasahmad.remotecare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {
    ArrayList<userchat> ls;
    Context c;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    int pos = 0;
    String url1;
    String s1;

    public UsersAdapter(ArrayList<userchat> ls, Context c) {
        this.ls = ls;
        this.c = c;

        SharedPreferences sh = c.getSharedPreferences("MySharedPref", 0);
        s1 = sh.getString("Ip", "");
        url1 = "http://" + s1 + "/smd_project/get_images_with_email.php";

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(c).inflate(R.layout.messageall, parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
//                Toast.makeText(c,response.toString(),Toast.LENGTH_LONG).show();
                try {
                    JSONArray obj2 = new JSONArray(response);

                    for (int i = 0; i < obj2.length(); i++) {
                        JSONObject jsonObject = obj2.getJSONObject(i);
                        String email = jsonObject.getString("email");
                        String profile = jsonObject.getString("imageurl");
                        Log.d("profilleee", profile);
                        if (!email.equals("null")) {
                            if (ls.get(holder.getAdapterPosition()).getOnlinestatus().equals("offline")) {
                                holder.name.setText(ls.get(holder.getAdapterPosition()).getName());
                                holder.lastseen.setText(ls.get(holder.getAdapterPosition()).getLastseen());
                                if (profile.trim().equals("null")) {

                                } else {
                                    Picasso.get().load("http://" + s1 + "/smd_project/" + profile).into(holder.profileimagechat);
                                }


                                holder.onlinestatus.setVisibility(View.GONE);
                            } else {
                                if (profile.trim().equals("null")) {

                                } else {
                                    Picasso.get().load("http://" + s1 + "/smd_project/" + profile).into(holder.profileimagechat);
                                }
                                holder.name.setText(ls.get(holder.getAdapterPosition()).getName());
                                holder.lastseen.setVisibility(View.GONE);
                                holder.onlinestatus.setText(ls.get(holder.getAdapterPosition()).getOnlinestatus());

                            }
                        } else {
                            if (ls.get(holder.getAdapterPosition()).getOnlinestatus().equals("offline")) {
                                holder.name.setText(ls.get(holder.getAdapterPosition()).getName());
                                holder.lastseen.setText(ls.get(holder.getAdapterPosition()).getLastseen());
                                holder.onlinestatus.setVisibility(View.GONE);
                            } else {
                                holder.name.setText(ls.get(holder.getAdapterPosition()).getName());
                                holder.lastseen.setVisibility(View.GONE);
                                holder.onlinestatus.setText(ls.get(holder.getAdapterPosition()).getOnlinestatus());

                            }
                        }
                    }
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(c, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                Log.d("hello11", ls.get(holder.getAdapterPosition()).getEmailadd());
                param.put("d_email", ls.get(holder.getAdapterPosition()).getEmailadd());


                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(c);
        queue.add(request);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, Activitychat.class);
                int i = holder.getAdapterPosition();
                intent.putExtra("name", ls.get(i).getName());
                intent.putExtra("uid", ls.get(i).getUid());
                intent.putExtra("p_id", ls.get(i).getP_id());
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
        CircleImageView profileimagechat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameid);
            onlinestatus = itemView.findViewById(R.id.onlinestatus);
            lastseen = itemView.findViewById(R.id.lastseen);
            profileimagechat = itemView.findViewById(R.id.messageimg);

        }
    }
}