package com.example.feedeazy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class frmfoodcounterlogin extends AppCompatActivity {

    private Button btnlogin,btnback1;
    private TextView txtemail,txtpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmfoodcounterlogin);

        btnlogin = findViewById(R.id.btnlogin);
        btnback1 = findViewById(R.id.btnback1);
        txtemail = findViewById(R.id.txtemail);
        txtpwd = findViewById(R.id.txtpwd);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmfoodcounterlogin.this,frmfoodcounterlogin.class);
                startActivity(intent);
            }
        });

        btnback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmfoodcounterlogin.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}