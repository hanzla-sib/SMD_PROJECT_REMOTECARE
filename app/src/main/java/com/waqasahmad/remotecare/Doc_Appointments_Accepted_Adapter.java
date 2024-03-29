package com.waqasahmad.remotecare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Doc_Appointments_Accepted_Adapter extends RecyclerView.Adapter<Doc_Appointments_Accepted_Adapter.MyViewHolder> {
    List<Doc_Appointment_Model> ls_doc2;
    Context c_doc2;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String recommend_steps_string = "";
    String currentemail;

    public Doc_Appointments_Accepted_Adapter(List<Doc_Appointment_Model> ls_doc2, Context c_doc2) {
        this.ls_doc2 = ls_doc2;
        this.c_doc2 = c_doc2;
    }

    @NonNull
    @Override
    public Doc_Appointments_Accepted_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(c_doc2).inflate(R.layout.patient_row_accepted_appointments, parent, false);
        return new Doc_Appointments_Accepted_Adapter.MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull Doc_Appointments_Accepted_Adapter.MyViewHolder holder, int position) {
        SharedPreferences sh = c_doc2.getSharedPreferences("MySharedPref", 0);
        String s1 = sh.getString("Ip", "");
        String url1 = "http://" + s1 + "/smd_project/delete_appointed_appoint_from_doctorside.php";
        String url2 = "http://" + s1 + "/smd_project/doctor_recommended_steps.php";
        String url3 = "http://" + s1 + "/smd_project/fetch_alldata_from_user.php";

        //Initializing Firebase MAuth instance
        mAuth = FirebaseAuth.getInstance();

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();

        //getting email of logged in user
        currentemail = mAuth.getCurrentUser().getEmail();

        holder.patient_name.setText(ls_doc2.get(holder.getAdapterPosition()).getName_patient());
        holder.patient_email.setText(ls_doc2.get(holder.getAdapterPosition()).getEmail_patient());
        Doc_Appointment_Model model = ls_doc2.get(holder.getAdapterPosition());

        /////////////////////////////////

        // fetch all data
        StringRequest request = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(c_doc2,response.toString(),Toast.LENGTH_LONG).show();

                try {
                    JSONArray obj2 = new JSONArray(response);


                    for (int i = 0; i < obj2.length(); i++) {
                        JSONObject jsonObject = obj2.getJSONObject(i);
                        String image = jsonObject.getString("imageurl");
                        Log.d("image_a", image);
                        if (!image.trim().equals("null")) {
                            Picasso.get().load("http://" + s1 + "/smd_project/" + image).into(holder.patient_image);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(c_doc2, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("email", ls_doc2.get(holder.getAdapterPosition()).getEmail_patient());

                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(c_doc2);
        queue.add(request);
        //

        // recommend steps and add to holder
        holder.enter_recommend_steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(c_doc2);
                builder.setMessage("Do you want to recommend these steps?")
                        .setTitle("Enter Steps");

                // Add the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button

                        recommend_steps_string = holder.recommend_steps.getText().toString();
                        if (recommend_steps_string.equals("")) {
                            Log.d("recommended steps are null ", "null");


                        } else {
                            Log.d("recommend_steps_string ", recommend_steps_string);

                            /////////////////////////////////
                            StringRequest request = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//                                    Toast.makeText(c_doc2,response.toString(),Toast.LENGTH_LONG).show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(c_doc2, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> param = new HashMap<String, String>();

                                    param.put("d_email", currentemail);
                                    param.put("p_email", model.getEmail_patient());
                                    param.put("steps", recommend_steps_string);

                                    return param;
                                }
                            };
                            RequestQueue queue = Volley.newRequestQueue(c_doc2);
                            queue.add(request);

                            ////////////////////////////////////////////
//
//                            ls_doc2.remove(holder.getAdapterPosition());
//                            notifyItemRemoved(holder.getAdapterPosition());
//                            notifyItemRangeChanged(holder.getAdapterPosition(), ls_doc2.size());
//


                        }


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });

                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

        // delete appointment
        holder.deleteappoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(c_doc2);
                builder.setMessage("Do you want to delete this appointment?")
                        .setTitle("Delete Appointment");

                // Add the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        ls_doc2.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(), ls_doc2.size());
                        holder.itemView.setVisibility(View.GONE);

                        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                                Toast.makeText(c_doc2,response.toString(),Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(c_doc2, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> param = new HashMap<String, String>();

                                param.put("d_email", currentemail);
                                param.put("p_email", model.getEmail_patient());

                                return param;
                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(c_doc2);
                        queue.add(request);

                        /////////////////////////////////////
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });

                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

        holder.completeappoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(c_doc2);
                builder.setMessage("Do you want to complete this appointment?")
                        .setTitle("Complete Appointment");

                // Add the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        ls_doc2.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(), ls_doc2.size());

                        holder.itemView.setVisibility(View.GONE);

                        SharedPreferences sh = c_doc2.getSharedPreferences("MySharedPref", 0);
                        String s1 = sh.getString("Ip", "");
                        String url2 = "http://" + s1 + "/smd_project/completed_appointment_doctor_side.php";

                        /////////////////////////////////
                        StringRequest request = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                                Toast.makeText(c_doc2,response.toString(),Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(c_doc2, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> param = new HashMap<String, String>();
                                param.put("d_email", currentemail);
                                param.put("p_email", model.getEmail_patient());
                                return param;
                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(c_doc2);
                        queue.add(request);
                        //////////////////////////////////////////////
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
        return ls_doc2.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView patient_name, patient_email;
        Button deleteappoint;
        Button completeappoint;
        EditText recommend_steps;
        ImageView enter_recommend_steps;
        CircleImageView patient_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            patient_name = itemView.findViewById(R.id.patient_name_accepted);
            patient_email = itemView.findViewById(R.id.patient_email_accepted);
            deleteappoint = itemView.findViewById(R.id.deleteappoint);
            completeappoint = itemView.findViewById(R.id.completeappoint);
            recommend_steps = itemView.findViewById(R.id.recommend_steps);
            enter_recommend_steps = itemView.findViewById(R.id.enter_recommend_steps);
            patient_image = itemView.findViewById(R.id.patient_img);


        }
    }
}







