package com.waqasahmad.remotecare;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Doc_Appointment_Pending_Adapter extends RecyclerView.Adapter<Doc_Appointment_Pending_Adapter.MyViewHolder>
{

    List<Doc_Appointment_Model> ls_doc2;
    Context c_doc2;
    private int mYear, mMonth, mDay, mHour, mMinute;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String date1="",Time1="";


    private static final String accept_doctor_appointment="http://"+Ip_server.getIpServer()+"/smd_project/accept_doctor_appointment.php";
    private static final String reject_doctor_appointment="http://"+Ip_server.getIpServer()+"/smd_project/reject_doctor_appointment.php";


    public Doc_Appointment_Pending_Adapter(List<Doc_Appointment_Model> ls_doc2, Context c_doc2) {
        this.ls_doc2 = ls_doc2;
        this.c_doc2 = c_doc2;
    }

    String currentemail;

    @NonNull
    @Override
    public Doc_Appointment_Pending_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(c_doc2).inflate(R.layout.patient_row, parent, false);
        return new Doc_Appointment_Pending_Adapter.MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull Doc_Appointment_Pending_Adapter.MyViewHolder holder, int position) {

        //Initializing Firebase MAuth instance
        mAuth = FirebaseAuth.getInstance();

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();

        //getting email of logged in user
        currentemail = mAuth.getCurrentUser().getEmail();

        holder.patient_name.setText(ls_doc2.get(position).getName_patient());
        holder.patient_email.setText(ls_doc2.get(position).getEmail_patient());
        int i=position;

        holder.itemView.findViewById(R.id.btn_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(c_doc2,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                              holder.txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                              date1=(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        holder.itemView.findViewById(R.id.btn_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(c_doc2,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                              holder.txtTime.setText(hourOfDay + ":" + minute);
                              Time1=(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });



        holder.itemView.findViewById(R.id.accept_appointment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Time1.equals("") || date1.equals("")){
                    Toast.makeText(c_doc2.getApplicationContext(), "EMPTYYYYY",Toast.LENGTH_SHORT).show();
                }
                else{
                    String pname= ls_doc2.get(i).getName_patient();
                    String pemail= ls_doc2.get(i).getEmail_patient();

                    ls_doc2.remove(i);
                    notifyItemRemoved(i);

                    ////////////////////////////////////////////

                    StringRequest request=new StringRequest(Request.Method.POST, accept_doctor_appointment, new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            Log.d("respons11111111" ,response );

                            Toast.makeText(c_doc2,response.toString(),Toast.LENGTH_LONG).show();


                        }
                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(c_doc2,error.toString(),Toast.LENGTH_LONG).show();
                        }
                    })
                    {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> param=new HashMap<String,String>();
                            param.put("p_name",pname);
                            param.put("p_email",pemail);
                            param.put("d_email",currentemail);
                            Log.d("dateeeeee",date1);
                            Log.d("dateeeeee",Time1);
                            param.put("date",date1);
                            param.put("time",Time1);

                            return param;
                        }
                    };
                    RequestQueue queue= Volley.newRequestQueue(c_doc2);
                    queue.add(request);
                }


                //////////////////////////////////////////////

            }
        });


        //reject///////////



        holder.itemView.findViewById(R.id.reject_appointment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pname= ls_doc2.get(i).getName_patient();
                String pemail= ls_doc2.get(i).getEmail_patient();

                StringRequest request=new StringRequest(Request.Method.POST, reject_doctor_appointment, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d("respons11111111" ,response );

                        Toast.makeText(c_doc2,response.toString(),Toast.LENGTH_LONG).show();


                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(c_doc2,error.toString(),Toast.LENGTH_LONG).show();
                    }
                })
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param=new HashMap<String,String>();
                        param.put("p_name",pname);
                        param.put("p_email",pemail);
                        param.put("d_email",currentemail);
                        return param;
                    }
                };
                RequestQueue queue= Volley.newRequestQueue(c_doc2);
                queue.add(request);


            }
        });

        ///////////////////////////////////

    }

    @Override
    public int getItemCount() {
        return ls_doc2.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView patient_name,patient_email;
        ImageView btnDatePicker, btnTimePicker;
        EditText txtDate, txtTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            patient_name=itemView.findViewById(R.id.patient_name2);
            patient_email=itemView.findViewById(R.id.patient_email2);
            btnDatePicker=itemView.findViewById(R.id.btn_date);
            btnTimePicker=itemView.findViewById(R.id.btn_time);
            txtDate=itemView.findViewById(R.id.in_date);
            txtTime=itemView.findViewById(R.id.in_time);
        }
    }
}




