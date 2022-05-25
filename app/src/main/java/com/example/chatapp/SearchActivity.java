package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatapp.model.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView txtChattingWith;
    private ProgressBar progressBar;
    private ImageView imgToolbar, imgSea;
    private ArrayList<Message> messages, resSea;
    private String chatRoomID;
    private EditText edtSearch;
    private String strSearch;
    private SearchAdapter searchAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        messages = new ArrayList<>();
        resSea = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerSea);
        txtChattingWith = findViewById(R.id.txtChattingWith1);
        progressBar = findViewById(R.id.progressSea);
        imgToolbar = findViewById(R.id.img_toolbar1);
        edtSearch = findViewById(R.id.edtSearch);
        imgSea = findViewById(R.id.imgSearch);

        txtChattingWith.setText(getIntent().getStringExtra("txtChatWith"));
        chatRoomID = getIntent().getStringExtra("chatRoomID");

        Glide.with(SearchActivity.this).load(getIntent().getStringExtra("img_of_roommate")).placeholder(R.drawable.account_img).error(R.drawable.account_img).into(imgToolbar);


        imgSea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAdapter.clear();
                getListMess(chatRoomID);
                strSearch = edtSearch.getText().toString();
                for(Message mess : messages){
                    if(mess.getContent().contains(strSearch)){
                        resSea.add(mess);
                    }
                }
            }
        });

        searchAdapter = new SearchAdapter(resSea,SearchActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchAdapter);
    }

    private void getListMess(String chatRoomID){
        FirebaseDatabase.getInstance().getReference("messages/"+ chatRoomID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    messages.add(dataSnapshot.getValue(Message.class));
                }
                //SearchAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(messages.size()-1);
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}