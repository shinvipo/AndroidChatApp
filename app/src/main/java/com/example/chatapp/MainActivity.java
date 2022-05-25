package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword, edtEmail;
    private Button btnSubmit;
    private TextView txtLoginIn4;

    private boolean isSigningup = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtUsername = findViewById(R.id.edtUsername);
        btnSubmit = findViewById(R.id.btnSubmit);
        txtLoginIn4 = findViewById(R.id.txtLoginInfo);

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this,FriendsActivity.class));
            finish();
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()){
                    if(isSigningup && edtUsername.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this, "Đầu vào không hợp lệ",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                if(isSigningup){
                    handleSignup();
                }else {
                    handleLogin();
                }
            }
        });

        txtLoginIn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(isSigningup){
                    isSigningup = false;
                    edtUsername.setVisibility(View.GONE);
                    btnSubmit.setText("Đăng nhập");
                    txtLoginIn4.setText("Chưa có tài khoản? Đăng ký");
                }else{
                    isSigningup = true;
                    edtUsername.setVisibility(View.VISIBLE);
                    btnSubmit.setText("Đăng ký");
                    txtLoginIn4.setText("Đã có tài khoản? Đăng nhập");
                }
            }
        });
    }

    private void handleSignup(){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference("user/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new User(edtUsername.getText().toString(),edtEmail.getText().toString(), ""));

                    startActivity(new Intent(MainActivity.this,FriendsActivity.class));

                    Toast.makeText(MainActivity.this, "Đăng ký thành công",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handleLogin(){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this,FriendsActivity.class));

                    Toast.makeText(MainActivity.this, "Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}