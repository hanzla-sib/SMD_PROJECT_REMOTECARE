package com.waqasahmad.remotecare;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Messages> messagesArrayList;
    int ITEM_SEND=1;
    int ITEM_RECEIVE=2;

    String url1;
    String s1;

    public MessagesAdapter(Context context, ArrayList<Messages> messagesArrayList) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;
        SharedPreferences sh = context.getSharedPreferences("MySharedPref", 0);
        s1 = sh.getString("Ip", "");
        url1 ="http://"+s1+"/smd_project/get_image_for_chat_insidescreen.php";
    }

    public void setfilterlist(ArrayList<Messages> filteredlist){
        this.messagesArrayList=filteredlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==ITEM_SEND)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout_item,parent,false);
            return new SenderViewHOlder(view);

        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_layout_item,parent,false);
            return new ReceiverViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Messages messages = messagesArrayList.get(position);
        if(holder.getClass()==SenderViewHOlder.class)
        {
            SenderViewHOlder viewHOlder = (SenderViewHOlder) holder;
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
            DatabaseReference userNodeRef = usersRef.child(messages.getSuid());

            DatabaseReference emailNodeRef = userNodeRef.child("email");
            emailNodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String email = dataSnapshot.getValue(String.class);
                    Log.d("emaillll",email);


                    StringRequest request=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>()
                    {

                        @Override
                        public void onResponse(String response)
                        {
                            Log.d("picturee",response.toString());
                            String pic=response.toString();
                            if(pic.length()>=3){
                                Picasso.get().load("http://"+s1+"/smd_project/"+pic).into(viewHOlder.img);
                            }


                        }
                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
//                                Toast.makeText(c_doc2,error.toString(),Toast.LENGTH_LONG).show();
                        }
                    })
                    {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> param=new HashMap<String,String>();

                            param.put("d_email",email);


                            return param;
                        }
                    };
                    RequestQueue queue= Volley.newRequestQueue(context);
                    queue.add(request);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Handle any errors that occur
                }
            });

            viewHOlder.txtmessage.setText(messages.getMsg());
            viewHOlder.timestamp.setText( messages.getTimestamp());
        }
        else
        {
            ReceiverViewHolder viewHOlder = (ReceiverViewHolder) holder;
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
            DatabaseReference userNodeRef = usersRef.child(messages.getSuid());

            DatabaseReference emailNodeRef = userNodeRef.child("email");
            emailNodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String email = dataSnapshot.getValue(String.class);

                    StringRequest request=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>()
                    {

                        @Override
                        public void onResponse(String response)
                        {
            Log.d("picturee",response.toString());
            String pic=response.toString();
                            if(pic.length()>=3){
                                Picasso.get().load("http://"+s1+"/smd_project/"+pic).into(viewHOlder.img);
                            }


                        }
                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
//                                Toast.makeText(c_doc2,error.toString(),Toast.LENGTH_LONG).show();
                        }
                    })
                    {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> param=new HashMap<String,String>();

                            param.put("d_email",email);


                            return param;
                        }
                    };
                    RequestQueue queue= Volley.newRequestQueue(context);
                    queue.add(request);

                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });

            viewHOlder.txtmessage.setText(messages.getMsg());
            viewHOlder.timestamp.setText( messages.getTimestamp());
        }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {

        Messages messages =messagesArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSuid()))
        {
            return ITEM_SEND;
        }
        else {
            return ITEM_RECEIVE;
        }
    }

    class SenderViewHOlder extends RecyclerView.ViewHolder {

        TextView txtmessage,timestamp;
        CircleImageView img;
        public SenderViewHOlder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img1);
            txtmessage = itemView.findViewById(R.id.txtMessages);
            timestamp =itemView.findViewById(R.id.time1);
        }
    }


    class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView txtmessage,timestamp;
        CircleImageView img;


        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            txtmessage = itemView.findViewById(R.id.txtMessages);
            timestamp =itemView.findViewById(R.id.time1);
            img=itemView.findViewById(R.id.img1);



        }
    }


}