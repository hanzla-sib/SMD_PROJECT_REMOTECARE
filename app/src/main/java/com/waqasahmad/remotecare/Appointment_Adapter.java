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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Appointment_Adapter extends RecyclerView.Adapter<Appointment_Adapter.MyViewHolder>
{
    List<Appointment_Model> ls_doc;
    Context c_doc;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String currentemail="";
    String currentname="";

    private static final String patient_appointment="http://"+Ip_server.getIpServer()+"/smd_project/patient_appointment.php";


    public Appointment_Adapter(List<Appointment_Model> ls_doc, Context c_doc)
    {
        this.ls_doc = ls_doc;
        this.c_doc = c_doc;
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

        //Initializing Firebase MAuth instance
        mAuth=FirebaseAuth.getInstance();

        //Initializing Firebase MAuth instance
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

//                        Log.d("name11111111111", currentname);

                    }
                });




        holder.doctor_name.setText(ls_doc.get(position).getName_doc());
        holder.doctor_email.setText(ls_doc.get(position).getEmail_doc());
        int i=position;

        holder.itemView.findViewById(R.id.request_appointment).
                setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

               String dname= ls_doc.get(i).getName_doc();
               String demail= ls_doc.get(i).getEmail_doc();


                StringRequest request=new StringRequest(Request.Method.POST, patient_appointment, new Response.Listener<String>()
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
        TextView doctor_name,doctor_email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doctor_name=itemView.findViewById(R.id.doc_name2);
            doctor_email=itemView.findViewById(R.id.doc_email2);
        }
    }


}
