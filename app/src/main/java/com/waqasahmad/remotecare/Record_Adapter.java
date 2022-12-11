package com.waqasahmad.remotecare;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Record_Adapter extends RecyclerView.Adapter<Record_Adapter.MyViewHolder>{
    Activity activity;
    ArrayList<Record_Model> arrayList;

    public Record_Adapter(Activity activity, ArrayList<Record_Model> arrayList){
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Record_Model model = arrayList.get(position);

//        holder.image_record.setImageURI(Picasso.get());
        Picasso.get().load("http://"+Ip_server.getIpServer()+"/smd_project/"+model.getImage_url()).into(holder.image_record);
        holder.details.setText(model.getDetails());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView image_record;
        TextView details;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_record = itemView.findViewById(R.id.image_record);
            details = itemView.findViewById(R.id.details_record);

        }
    }
}

