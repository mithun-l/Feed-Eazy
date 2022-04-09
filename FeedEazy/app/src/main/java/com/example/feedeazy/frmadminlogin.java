package com.example.feedeazy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class frmadminlogin extends AppCompatActivity {

    private TextView username,password;
    private Button abtn_login,abtn_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmadminlogin);

        username = findViewById(R.id.txt_username);
        password = findViewById(R.id.txt_pwd);
        abtn_login = findViewById(R.id.abtn_login);
        abtn_home = findViewById(R.id.abtn_home);

        abtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((TextUtils.isEmpty(username.getText().toString())) || (TextUtils.isEmpty(password.getText().toString()))) {
                    Toast.makeText(frmadminlogin.this, "Invalid Inputs",Toast.LENGTH_LONG).show();
                }
                else if(username.getText().toString().equals("admin"))
                {
                    if(password.getText().toString().equals("admin"))
                    {
                        Globalvariable.loginrole  = "admin";

                        Toast.makeText(frmadminlogin.this, "Login Success",Toast.LENGTH_LONG).show();
                        navigateadminmenu();
                    }
                    else
                    {
                        Toast.makeText(frmadminlogin.this, "Invalid Password",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(frmadminlogin.this, "Invalid Username/Password",Toast.LENGTH_LONG).show();
                }
            }
        });



        abtn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmadminlogin.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void navigateadminmenu()
    {
        Intent intent =  new Intent(this,frmadminmenu.class);
        startActivity(intent);
    }
}