package com.waqasahmad.remotecare;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShowimagesActivity extends AppCompatActivity {
    private DatabaseHandler objectDatabasehandler;
    private RecyclerView rv;
    private RVadapterforoffline objectRVadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimages);

        try {
            rv = findViewById(R.id.shrv);
            objectDatabasehandler = new DatabaseHandler(this);
        } catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void getData(View view) {
        try {
            objectRVadapter = new RVadapterforoffline(objectDatabasehandler.getAllimagesdata());
            rv.setHasFixedSize(true);
            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setAdapter(objectRVadapter);

        } catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}