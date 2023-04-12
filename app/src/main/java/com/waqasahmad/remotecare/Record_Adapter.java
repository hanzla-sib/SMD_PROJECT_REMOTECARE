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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Record_Adapter extends RecyclerView.Adapter<Record_Adapter.MyViewHolder>
{
    Context activity;
    ArrayList<Record_Model> arrayList;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

//    private static final String delete_test_record="http://"+Ip_server.getIpServer()+"/smd_project/delete_test_record.php";

    public Record_Adapter(Context activity, ArrayList<Record_Model> arrayList)
    {
        this.activity = activity;
        this.arrayList = arrayList;
    }


    String currentemail;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record_row,parent,false);
                return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {


        //Initializing Firebase MAuth instance
        mAuth = FirebaseAuth.getInstance();

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();

        //getting email of logged in user
        currentemail = mAuth.getCurrentUser().getEmail();

        Record_Model model = arrayList.get(holder.getAdapterPosition());
        Integer rec = Integer.valueOf(holder.getAdapterPosition()+1);


//      holder.image_record.setImageURI(Picasso.get());
        SharedPreferences sh = activity.getSharedPreferences("MySharedPref", 0);
        String s1 = sh.getString("Ip", "");
        Picasso.get().load("http://"+s1+"/smd_project/"+model.getImage_url()).into(holder.image_record);
        holder.details.setText(model.getDetails());
        holder.record_num.setText("Record # " + (holder.getAdapterPosition()+1));


                holder
                .itemView
                .findViewById(R.id.delete_record)
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {


                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage("Do you want to delete this test record?")
                                .setTitle("Delete Test record");

                        // Add the buttons
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                // User clicked OK button

                                arrayList.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(), arrayList.size());

                                holder.itemView.setVisibility(View.GONE);

                                SharedPreferences sh = activity.getSharedPreferences("MySharedPref", 0);
                                String s1 = sh.getString("Ip", "");
                                String url1 ="http://"+s1+"/smd_project/delete_test_record.php";
                                StringRequest request=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response)
                                    {
                                        Toast.makeText(activity,response.toString(),Toast.LENGTH_LONG).show();
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

                                        param.put("p_email",currentemail);
                                        param.put("image_url",model.getImage_url());

                                        return param;
                                    }
                                };
                                RequestQueue queue= Volley.newRequestQueue(activity);
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
    public int getItemCount()
    {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView image_record,delete_record;
        TextView details,record_num;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            image_record = itemView.findViewById(R.id.image_record);
            details = itemView.findViewById(R.id.details_record);
            record_num=itemView.findViewById(R.id.record_num);
            delete_record=itemView.findViewById(R.id.delete_record);
        }
    }
}

