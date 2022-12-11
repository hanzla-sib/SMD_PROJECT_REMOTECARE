package com.waqasahmad.remotecare;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVadapterforoffline extends RecyclerView.Adapter<RVadapterforoffline.RVVIewHolderClass> {
   ArrayList<ModelClassoffline> objectModealclasses;

    public RVadapterforoffline(ArrayList<ModelClassoffline> objectModealclasses) {
        this.objectModealclasses = objectModealclasses;
    }

    @NonNull
    @Override
    public RVVIewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVVIewHolderClass(LayoutInflater.from(parent.getContext()).inflate(R.layout.imagesinglerowoffline,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RVVIewHolderClass holder, int position) {
    ModelClassoffline object=objectModealclasses.get(position);
    holder.email.setText(object.getEmail());
    holder.objectiamgeview.setImageBitmap(object.getImage());

    }

    @Override
    public int getItemCount() {
        return objectModealclasses.size();
    }

    public static class RVVIewHolderClass extends RecyclerView.ViewHolder{
        TextView email;
        ImageView objectiamgeview;

        public RVVIewHolderClass(@NonNull View itemView) {
            super(itemView);
            objectiamgeview=itemView.findViewById(R.id.imagetv);
            email=itemView.findViewById(R.id.imagedetailstxtview);
        }
    }
}
