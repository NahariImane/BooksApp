package com.example.projetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText username,email,password,repassword;
    Button signup;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        username =(EditText)findViewById(R.id.Username);
        password =(EditText)findViewById(R.id.password);
        email =(EditText) findViewById(R.id.email);
        repassword =(EditText)findViewById(R.id.repassword);
        signup = findViewById(R.id.signup);

        DB= new DBHelper(this);


        //
        signup.setOnClickListener(v -> {
            String user= username.getText().toString();
            String mail = email.getText().toString();
            String pass= password.getText().toString();
            String repass = repassword.getText().toString();

            if(TextUtils.isEmpty(user) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass) )
                Toast.makeText(RegisterActivity.this, "All fields Required",Toast.LENGTH_SHORT).show();
            else{
                if(pass.equals(repass)){
                    Boolean checkuser = DB.checkusername(user);
                    if(!mail.equals("") && Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                        if(!checkuser){
                            Boolean insert = DB.insertData(user,pass,mail);
                            if(insert){
                                Toast.makeText(RegisterActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this, "User already exists",Toast.LENGTH_SHORT).show();
                    }
                    } else{
                        Toast.makeText(RegisterActivity.this, "Invalid email adress!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "Passwords are not matching",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}