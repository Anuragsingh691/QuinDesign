package com.example.quin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

import static com.example.quin.Prevalent.Prevalent.UserName;
import static com.example.quin.Prevalent.Prevalent.UserPassKey;
import static com.example.quin.Prevalent.Prevalent.UserPhoneKey;

public class RegisterPage extends AppCompatActivity {
    EditText edtPhone,edtpass,edtUsername;
    Button register,logact;
    private ProgressDialog mRegprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        edtPhone=(EditText)findViewById(R.id.signup_phoneNo_input);
        edtpass=(EditText)findViewById(R.id.signup_password_input);
        edtUsername=(EditText)findViewById(R.id.signup_Name_input);
        register=(Button)findViewById(R.id.button_register);
        mRegprogress=new ProgressDialog(this);
        logact=(Button)findViewById(R.id.button_sign_in_transfer);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=edtUsername.getText().toString();
                String phone=edtPhone.getText().toString();
                String pass=edtpass.getText().toString();
                if (TextUtils.isEmpty(name))
                {
                    Toast.makeText(RegisterPage.this, "Please input ur name", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(phone))
                {
                    Toast.makeText(RegisterPage.this, "Please input ur phone number", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(pass)&& pass.length()<6)
                {
                    Toast.makeText(RegisterPage.this, "Please input password of atleast 6 digits", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mRegprogress.setTitle("Create Account");
                    mRegprogress.setMessage("Please wait, while we check all the credentials");
                    mRegprogress.setCanceledOnTouchOutside(false);
                    mRegprogress.show();
                    ValidatePhoneNumber(name,phone,pass);

                }
            }
        });
        logact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regscreen=new Intent(RegisterPage.this,login.class);
                startActivity(regscreen);
                finish();
            }
        });
    }
    private void ValidatePhoneNumber(final String name, final String phone, final String pass) {
        final DatabaseReference Rootref;
        Rootref= FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child("Users").child(phone).exists())
                {
                    HashMap<String,Object> userdatamap=new HashMap<>();
                    userdatamap.put("phone",phone);
                    userdatamap.put("Name",name);
                    userdatamap.put("Pass",pass);
                    Rootref.child("Users").child(phone).updateChildren(userdatamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(RegisterPage.this, "Congratulations Your Account has been created", Toast.LENGTH_SHORT).show();
                                mRegprogress.dismiss();
                                startActivity(new Intent(RegisterPage.this, Home.class));
                                Paper.book().write(UserPhoneKey,phone);
                                Paper.book().write(UserName,name);
                                Paper.book().write(UserPassKey,pass);
                                finish();
                            }
                            else
                            {
                                mRegprogress.dismiss();
                                Toast.makeText(RegisterPage.this, "Sorry , Registration is not Complete ,Try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(RegisterPage.this, "Phone Number " + phone + " Exists Already", Toast.LENGTH_SHORT).show();
                    mRegprogress.dismiss();
                    Toast.makeText(RegisterPage.this, "Please Use different Phone number", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}