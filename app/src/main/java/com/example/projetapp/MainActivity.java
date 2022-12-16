package com.example.projetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    Button signin;
    Button register;
    DBHelper DB;
    TextView forgot;
    User currentuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.Username);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin);
        register =(Button)findViewById(R.id.register);
        forgot = (TextView) findViewById(R.id.btnforgot);
        currentuser = new User();

        DB = new DBHelper(this);


        signin.setOnClickListener(v -> {

            String user = username.getText().toString();
            String pass= password.getText().toString();

            if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass))
                Toast.makeText(MainActivity.this,"All fields Are Required",Toast.LENGTH_SHORT).show();
            else{
                Boolean checkuserpass = DB.checkusernamepassword(user,pass);
                currentuser = DB.getUserData(user);
                if(checkuserpass && currentuser != null){
                    Toast.makeText(MainActivity.this, "Login Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),BookActivity.class);
                    intent.putExtra("currentuser",currentuser);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //  opens  the registration activity
        register.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(intent);
        });

        // opens the reset password activity
        forgot.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
            startActivity(intent);
        });

    }
}