package com.example.quin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quin.Prevalent.Prevalent;
import com.example.quin.fragments.AddPostFragment;
import com.example.quin.fragments.HomeFragment;
import com.example.quin.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    Button logout;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    String sName;
    TextView Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        mFirebaseAuth = FirebaseAuth.getInstance();
        Username=findViewById(R.id.user_name);
        sName= Prevalent.currentOnlineUsers.getName();
        Username.setText(Prevalent.currentOnlineUsers.getName());
        Toast.makeText(this, "Welcome back \n"+Prevalent.currentOnlineUsers.getName(), Toast.LENGTH_SHORT).show();
        bottomNavigationView = findViewById(R.id.bottom_nav);
        frameLayout = findViewById(R.id.frameLayout);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new HomeFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomMethod);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener bottomMethod =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    switch (item.getItemId())
                    {
                        case R.id.bottom_nav_home:
                            fragment=new HomeFragment();
                            break;
                        case R.id.bottom_nav_event:
                            fragment=new SearchFragment();
                            break;
                        case R.id.bottom_nav_issue:
                            fragment=new AddPostFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();

                    return true;
                }
            };
}