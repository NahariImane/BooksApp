package com.example.projetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PasswordActivity extends AppCompatActivity {

    EditText username;
    Button reset;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);


        username = (EditText) findViewById(R.id.username_reset);
        reset =(Button) findViewById(R.id.btnreset);
        DB = new DBHelper(this);

        // calling the reset activity
        reset.setOnClickListener(v -> {
            String user = username.getText().toString();

            Boolean checkuser = DB.checkusername(user);

            if(checkuser)
            {
                // creating an intent and setting the username as its extra
                // to transfer it to the reset activity
                Intent intent = new Intent(getApplicationContext(), ResetActivity.class);
                intent.putExtra("username",user);
                startActivity(intent);
            } else
            {
                // if the user does not exist show this toast message
                Toast.makeText(PasswordActivity.this,"User does not exists",Toast.LENGTH_SHORT).show();

            }
        });
    }
}