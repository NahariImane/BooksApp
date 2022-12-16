package com.example.projetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




public class Profile extends AppCompatActivity {
    EditText username, email;
    Button editbtn;
    User currentuser;
    Boolean checkupdatedata;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username  = (EditText) findViewById(R.id.username_text);
        email = (EditText) findViewById(R.id.email_text);
        editbtn = (Button)  findViewById(R.id.editbtn);

        DB = new DBHelper(this);

        // recuperating the username from the login activity
        Intent intent = getIntent();
        currentuser = (User) intent.getSerializableExtra("currentuser");

        username.setText(currentuser.getUsername());
        email.setText(currentuser.getEmail());

        // updating the changes made on th username and email
        editbtn.setOnClickListener(view -> {
            String usernameTXT = username.getText().toString();
            String emailTXT = email.getText().toString();

            // checking if we are changing the username
            if(!usernameTXT.equals(currentuser.getUsername())){
                // checking if the new username already exists
                Boolean checkuser = DB.checkusername(usernameTXT);
                if(!checkuser){
                    // update the username and email if the user does not exist
                    checkupdatedata = DB.updateusernameemail(currentuser.getUsername(),usernameTXT, emailTXT);

                    // updating new values to the current user
                    currentuser.setUsername(usernameTXT);
                    currentuser.setEmail(emailTXT);

                    if (checkupdatedata) {
                        Toast.makeText(Profile.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(getApplicationContext(), BookActivity.class);
                        intent1.putExtra("currentuser",currentuser);
                        startActivity(intent1);
                    } else{
                        Toast.makeText(Profile.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Profile.this, "User already exists",Toast.LENGTH_SHORT).show();
                }
            } else {
                // updating the email with the same username
                checkupdatedata = DB.updateusernameemail(currentuser.getUsername(),usernameTXT, emailTXT);

                currentuser.setUsername(usernameTXT);
                currentuser.setEmail(emailTXT);
                    if (checkupdatedata) {
                        Toast.makeText(Profile.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(getApplicationContext(), BookActivity.class);
                        intent1.putExtra("currentuser",currentuser);
                        startActivity(intent1);
                    } else{
                        Toast.makeText(Profile.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();
                    }
            }
    });
    }
}