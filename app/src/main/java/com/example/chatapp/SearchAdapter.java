package com.example.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.chatapp.model.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MessageHolder> {

    private ArrayList<Message> messages;
    private Context context;

    public SearchAdapter(ArrayList<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_holder,parent,false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        Message message = messages.get(position);
        holder.txtView.setText(message.getSender() + " : " + message.getContent());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder{
        TextView txtView;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);

            txtView = itemView.findViewById(R.id.txtResSrea);
        }
    }

    public void clear() {
        int size = messages.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                messages.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }
}
