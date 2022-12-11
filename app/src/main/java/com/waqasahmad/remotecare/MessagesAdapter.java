package com.waqasahmad.remotecare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Messages> messagesArrayList;
    int ITEM_SEND=1;
    int ITEM_RECEIVE=2;



    public MessagesAdapter(Context context, ArrayList<Messages> messagesArrayList) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;
    }

    public void setfilterlist(ArrayList<Messages> filteredlist){
        this.messagesArrayList=filteredlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==ITEM_SEND)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout_item,parent,false);
            return new SenderViewHOlder(view);

        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_layout_item,parent,false);
            return new ReceiverViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Messages messages = messagesArrayList.get(position);
        if(holder.getClass()==SenderViewHOlder.class)
        {
            SenderViewHOlder viewHOlder = (SenderViewHOlder) holder;
            viewHOlder.txtmessage.setText(messages.getMsg());
            viewHOlder.timestamp.setText( messages.getTimestamp());
        }
        else
        {
            ReceiverViewHolder viewHOlder = (ReceiverViewHolder) holder;
            viewHOlder.txtmessage.setText(messages.getMsg());
            viewHOlder.timestamp.setText( messages.getTimestamp());
        }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {

        Messages messages =messagesArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSuid()))
        {
            return ITEM_SEND;
        }
        else {
            return ITEM_RECEIVE;
        }
    }

    class SenderViewHOlder extends RecyclerView.ViewHolder {

        TextView txtmessage,timestamp;

        public SenderViewHOlder(@NonNull View itemView) {
            super(itemView);

            txtmessage = itemView.findViewById(R.id.txtMessages);
            timestamp =itemView.findViewById(R.id.time1);
        }
    }


    class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView txtmessage,timestamp;
        CircleImageView img;


        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            txtmessage = itemView.findViewById(R.id.txtMessages);
            timestamp =itemView.findViewById(R.id.time1);
            img=itemView.findViewById(R.id.img1);



        }
    }


}