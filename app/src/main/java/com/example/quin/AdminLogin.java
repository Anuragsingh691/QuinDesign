package com.example.quin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quin.Model.Users;
import com.example.quin.Prevalent.Prevalent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class AdminLogin extends AppCompatActivity {
    FirebaseUser firebaseUser;
    private ProgressDialog mLogProgress;
    private Button joinNw,Login;
    private TextView Seller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        joinNw=(Button)findViewById(R.id.join_nw_btn);
        Login=(Button)findViewById(R.id.login_nw_btn);
        joinNw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLogin.this,RegisterPage.class));
                finish();
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLogin.this,login.class));
                finish();
            }
        });
        Paper.init(this);
        mLogProgress=new ProgressDialog(this);
        String UserPhoneKey=Paper.book().read(Prevalent.UserPhoneKey);
        String UserPassKey= Paper.book().read(Prevalent.UserPassKey);
        if(UserPhoneKey!=null && UserPassKey!=null)
        {
            Allowacess(UserPhoneKey,UserPassKey);
            mLogProgress.setTitle("Already Logged in");
            mLogProgress.setMessage("Please wait...!");
            mLogProgress.setCanceledOnTouchOutside(false);
            mLogProgress.show();
        }
    }
    private void Allowacess(final String phone, final String password) {
        final DatabaseReference Rootref;
        Rootref= FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(phone).exists())
                {
                    Users userdata=dataSnapshot.child("Users").child(phone).getValue(Users.class);
                    if (userdata.getPhone().equals(phone))
                    {
                        if (userdata.getPass().equals(password))
                        {
                            Toast.makeText(AdminLogin.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            mLogProgress.dismiss();
                            Intent startintent=new Intent(AdminLogin.this, Home.class);
                            Prevalent.currentOnlineUsers=userdata;
                            startActivity(startintent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(AdminLogin.this, "password is incorrect", Toast.LENGTH_SHORT).show();
                            mLogProgress.dismiss();
                        }

                    }

                }
                else
                {
                    Toast.makeText(AdminLogin.this, "Account with the given phone number doesn't exist", Toast.LENGTH_SHORT).show();
                    mLogProgress.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}