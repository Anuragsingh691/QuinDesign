package com.example.quin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.quin.Prevalent.Prevalent;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private static  int SPLASH_SCREEN=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    Intent splash = new Intent(MainActivity.this, AdminLogin.class);
                    startActivity(splash);
                    finish();
            }
        },SPLASH_SCREEN);
    }
}