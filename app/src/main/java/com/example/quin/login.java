package com.example.quin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quin.Model.Users;
import com.example.quin.Prevalent.Prevalent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

import static com.example.quin.Prevalent.Prevalent.UserPassKey;
import static com.example.quin.Prevalent.Prevalent.UserPhoneKey;

public class login extends AppCompatActivity {
    EditText edtPhone, edtPass;
    Button login,regact;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog mLogProgress;
    private String parentDbName="Users";
    private CheckBox checkBoxRemember;
    private TextView adminlnk, Notadminlnk,ForgotPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtPhone=(EditText)findViewById(R.id.login_phoneNo_input);
        edtPass=(EditText)findViewById(R.id.login_password_input);
        login=(Button)findViewById(R.id.button_login);
        regact=(Button) findViewById(R.id.button_sign_up_transfer);
        firebaseAuth=FirebaseAuth.getInstance();
        mLogProgress=new ProgressDialog(this);
        Paper.init(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=edtPhone.getText().toString();
                String password=edtPass.getText().toString();
                if (TextUtils.isEmpty(phone))
                {
                    Toast.makeText(login.this,"Please Enter phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(password)&& password.length()<6)
                {
                    Toast.makeText(login.this,"Please Enter password of atleast 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    mLogProgress.setTitle("Logging In");
                    mLogProgress.setMessage("Please wait while we log in your account !");
                    mLogProgress.setCanceledOnTouchOutside(false);
                    mLogProgress.show();
                    LoginUser(phone,password);
                }
            }
        });
        regact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regscreen=new Intent(login.this,RegisterPage.class);
                startActivity(regscreen);
                finish();
            }
        });
    }
    private void LoginUser(final String phone, final String password) {
        final DatabaseReference Rootref;
        Rootref= FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    Users userdata=dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if (userdata.getPhone().equals(phone))
                    {
                        if (userdata.getPass().equals(password))
                        {
                            if (parentDbName.equals("Admins"))
                            {
                                Toast.makeText(login.this, "Admin Logged in Successfully", Toast.LENGTH_SHORT).show();
                                mLogProgress.dismiss();
                                startActivity(new Intent(login.this, Home.class));
                                Paper.book().write(UserPhoneKey,phone);
                                Paper.book().write(UserPassKey,password);
                                finish();
                            }
                            else if (parentDbName.equals("Users"))
                            {
                                Paper.book().write(UserPhoneKey,phone);
                                Paper.book().write(UserPassKey,password);
                                Toast.makeText(login.this, "User Logged in Successfully", Toast.LENGTH_SHORT).show();
                                mLogProgress.dismiss();
                                Intent mainintent=new Intent(login.this, Home.class);
                                Prevalent.currentOnlineUsers=userdata;
                                startActivity(mainintent);
                                finish();

                            }
                        }
                        else
                        {
                            Toast.makeText(login.this, "password is incorrect", Toast.LENGTH_SHORT).show();
                            mLogProgress.dismiss();
                        }

                    }

                }
                else
                {
                    Toast.makeText(login.this, "Account with the given phone number doesn't exist", Toast.LENGTH_SHORT).show();
                    mLogProgress.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}