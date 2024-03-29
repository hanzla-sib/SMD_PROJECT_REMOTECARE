//package com.waqasahmad.remotecare;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Doc_Patient_Details extends AppCompatActivity {
//
//    DrawerLayout doc_drawerLayout;
//    ImageView Menu,logo;
//
//    //RV
//    RecyclerView rv;
//    List<Doc_Appointment_Model> ls2 =new ArrayList<>();
//    ImageView accept_app;
//
//    //Fire store
//    FirebaseFirestore db;
//    FirebaseAuth mAuth;
//
//    //
//    List<String> list = new ArrayList<>();
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.doc_patient_details);
//
//        doc_drawerLayout = findViewById(R.id.doc_patient_details);
//        Menu = findViewById(R.id.menu);
//        logo=findViewById(R.id.rclogo);
//
//        //RV
//        rv=findViewById(R.id.doc_rv_appointments);
//        accept_app= findViewById(R.id.accept_appointment);
//
//        //
//        db = FirebaseFirestore.getInstance();
//        mAuth= FirebaseAuth.getInstance();
//
//        //
//        String useremail = mAuth.getCurrentUser().getEmail();
//        Log.d("useremail" , useremail);
//
//
//        db.collection("users").
//                document(useremail).
//                    collection("Appointments").
//                        get().
//                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task)
//            {
//                if (task.isSuccessful())
//                {
//                    for (QueryDocumentSnapshot document : task.getResult())
//                    {
//                        list.add(document.getId());
//                        Log.d("wwwwwwwwwww", document.getId());
//                    }
//                }
//                else
//                {
//                    Log.d("Error", "Error getting documents: ", task.getException());
//                }
//                //----------------------//
//                for(int i=0; i<list.size(); i++)
//                {
//
//                    String currentX = list.get(i);
//
//                    db.collection("users").
//                            document(currentX).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//                                    DocumentSnapshot document = task.getResult();
//
//                                    JSONObject obj;
//                                    obj = new JSONObject(document.getData());
//
//
//                                    try
//                                    {
//                                        String patient_name = obj.getString("Name");
//                                        String patient_email = obj.getString("Email");
//
//                                        Doc_Appointment_Model doc_model = new Doc_Appointment_Model();
//                                        doc_model.setName_patient(patient_name);
//                                        doc_model.setEmail_patient(patient_email);
//                                        ls2.add(doc_model);
//
//
//                                        Doc_Appointment_Adapter adapter = new Doc_Appointment_Adapter (ls2 , Doc_Patient_Details.this);
//                                        RecyclerView.LayoutManager lm = new LinearLayoutManager(Doc_Patient_Details.this);
//                                        rv.setLayoutManager(lm);
//                                        rv.setAdapter(adapter);
//
//                                    }
//
//                                    catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                }
//                            });
//                }
//            }
//        });
//
//
//        Menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                doc_drawerLayout.openDrawer(GravityCompat.START);
//
//            }
//        });
//        logo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (doc_drawerLayout.isDrawerOpen(GravityCompat.START)) {
//
//                    doc_drawerLayout.closeDrawer(GravityCompat.START);
//                }
//            }
//        });
//
//    }
//
//
//    public void ClickPatientDetails (View view){
//
//        if (doc_drawerLayout.isDrawerOpen(GravityCompat.START)) {
//
//            doc_drawerLayout.closeDrawer(GravityCompat.START);
//        }
//    }
//
//    //next 3 functions are different for the doctor/patient
//    public void ClickPro (View view) {
//
//        Intent intent = new Intent(this, Doctor1.class);
//        startActivity(intent);
//    }
//    public void ClickProfile (View view){
//
//        Intent intent = new Intent(this, Doc_Profile.class);
//        startActivity(intent);
//    }
//    public void ClickPrescriptionDetailsDoc (View view){
//
//        Intent intent = new Intent(this, Doc_Prescription.class);
//        startActivity(intent);
//    }
//
//}
//
//
