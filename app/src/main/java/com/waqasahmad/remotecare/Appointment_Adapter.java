package com.waqasahmad.remotecare;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Appointment_Adapter extends RecyclerView.Adapter<Appointment_Adapter.MyViewHolder>
{
    List<Appointment_Model> ls_doc;
    Context c_doc;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String currentemail;

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


        holder.doctor_name.setText(ls_doc.get(position).getName_doc());
        holder.doctor_email.setText(ls_doc.get(position).getEmail_doc());
        int i=position;

        holder.itemView.findViewById(R.id.request_appointment).
                setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                Log.d("requesttttttt" , ls_doc.get(i).getName_doc());
//                Log.d("request" , ls_doc.get(i).getEmail_doc());

                Map<String, Object> appointment = new HashMap<>();
                appointment.put("Appointment Request", "Pending");


                db.collection("users").
                        document(currentemail).
                            collection("Appointments").
                                document(ls_doc.get(i).getEmail_doc()).set(appointment);


                db.collection("users").
                        document(ls_doc.get(i).getEmail_doc()).
                            collection("Appointments").
                                document(currentemail).set(appointment);

                //                Log.d("comment" ,currentemail);

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
