package com.example.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp.model.User;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersHolder> {

    private ArrayList<User> users;
    private Context context;
    private OnUserClickListener onUserClickListener;

    public UsersAdapter(ArrayList<User> users, Context context, OnUserClickListener onUserClickListener) {
        this.users = users;
        this.context = context;
        this.onUserClickListener = onUserClickListener;
    }

    interface OnUserClickListener{
        void onUserClicked(int position);
    }



    @NonNull
    @Override
    public UsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view   = LayoutInflater.from(context).inflate(R.layout.user_holder,parent,false);
        return new UsersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersHolder holder, int position) {
        holder.txtUsername.setText(users.get(position).getUsername());

        Glide.with(context).load(users.get(position).getProfilePicture()).error(R.drawable.account_img).placeholder(R.drawable.account_img).into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UsersHolder extends RecyclerView.ViewHolder {
        TextView txtUsername;
        ImageView imgView;
        public UsersHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onUserClickListener.onUserClicked(getAdapterPosition());
                }
            });
            txtUsername = itemView.findViewById(R.id.txtFrUserName);
            imgView = itemView.findViewById(R.id.img_frie_pro);
        }
    }
}
