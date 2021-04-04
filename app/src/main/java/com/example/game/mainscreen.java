package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class mainscreen extends AppCompatActivity {
    public void onlineplay(View view){
        Intent intent=new Intent(getApplicationContext(),Signin.class);
        startActivity(intent);
    }

    public void offlineplay(View view){
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
    }
}
