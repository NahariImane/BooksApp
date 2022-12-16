package com.example.projetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResetActivity extends AppCompatActivity {

    TextView username;
    EditText pass, repass;
    Button confirm;

    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        // the actionbar have now an arrow to
        // return to the previous activity
       // Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        username  =  findViewById(R.id.username_reset_text);
        pass =  findViewById(R.id.password_reset);
        repass =  findViewById(R.id.repassword_reset);
        confirm =  findViewById(R.id.btnconfirm);

        DB = new DBHelper(this);

        Intent intent = getIntent();
        username.setText(intent.getStringExtra("username"));

        // confirm the new password changes
        confirm.setOnClickListener(v -> {
            String user = username.getText().toString();
            String password = pass.getText().toString();
            String repassword = repass.getText().toString();

            if(password.equals(repassword)) {
                Boolean checkpasswordupdate = DB.updatepassword(user, password);
                if (checkpasswordupdate) {
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent1);
                    Toast.makeText(ResetActivity.this, "Password updated succesfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ResetActivity.this, "Password not updated successfully", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ResetActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
            }
        });

    }
}