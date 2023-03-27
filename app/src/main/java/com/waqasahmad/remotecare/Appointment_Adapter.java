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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Appointment_Adapter extends RecyclerView.Adapter<Appointment_Adapter.MyViewHolder>
{
    List<Appointment_Model> ls_doc;
    Context c_doc;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String currentemail="";
    String currentname="";

    String url="";

//    private static final String patient_appointment="http://"+Ip_server.getIpServer()+"/smd_project/patient_appointment.php";


    public Appointment_Adapter(List<Appointment_Model> ls_doc, Context c_doc)
    {
        mAuth=FirebaseAuth.getInstance();

        //Initializing Firebase DB instance
        db = FirebaseFirestore.getInstance();

        //getting email of logged in user
        currentemail = mAuth.getCurrentUser().getEmail();

        db.collection("users")
                .document(currentemail)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        DocumentSnapshot document = task.getResult();
                        JSONObject obj;
                        obj = new JSONObject(document.getData());


                        try {
                            currentname = obj.getString("Name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        this.ls_doc = ls_doc;
        this.c_doc = c_doc;
    }
    public  void setfilterlist(List<Appointment_Model> filteredlist){
        this.ls_doc=filteredlist;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Appointment_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(c_doc).inflate(R.layout.doc_row, parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull Appointment_Adapter.MyViewHolder holder, int position)
    {





        SharedPreferences sh = c_doc.getSharedPreferences("MySharedPref", 0);
        String s2 = sh.getString("Ip", "");

        holder.doctor_name.setText( "Dr. " +ls_doc.get(position).getName_doc());
        holder.doc_profession.setText(ls_doc.get(position).getDoc_type());
//        holder.doctor_email.setText(ls_doc.get(position).getEmail_doc());
//        Picasso.get().load("http://"+Ip_server.getIpServer()+"/smd_project/"+ls_doc.get(position).getImage_doc()).into(holder.img);
        Picasso.get().load("http://"+s2+"/smd_project/"+ls_doc.get(position).getImage_doc()).into(holder.img);


        holder.itemView.findViewById(R.id.request_appointment).
                setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int i=holder.getAdapterPosition();
               String dname= ls_doc.get(i).getName_doc();
               String demail= ls_doc.get(i).getEmail_doc();

                ls_doc.remove(i);
                notifyItemRemoved(i);


                SharedPreferences sh = c_doc.getSharedPreferences("MySharedPref", 0);
                String s1 = sh.getString("Ip", "");
                url ="http://"+s1+"/smd_project/patient_appointment.php";


                StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d("respons11111111" ,response );

                        Toast.makeText(c_doc,response.toString(),Toast.LENGTH_LONG).show();


                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(c_doc,error.toString(),Toast.LENGTH_LONG).show();
                    }
                })
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param=new HashMap<String,String>();
                        param.put("p_name",currentname);
                        param.put("p_email",currentemail);
                        param.put("d_name",dname);
                        param.put("d_email",demail);
                        return param;
                    }
                };
                RequestQueue queue= Volley.newRequestQueue(c_doc);
                queue.add(request);

            }
        });
    }

    @Override
    public int getItemCount() {
        return ls_doc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView doctor_name,doc_profession;
        CircleImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doctor_name=itemView.findViewById(R.id.doc_name2);
            img=itemView.findViewById(R.id.doc_img);
            doc_profession=itemView.findViewById(R.id.doc_profession);
//            doctor_email=itemView.findViewById(R.id.doc_email2);

        }
    }


}
