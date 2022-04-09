package com.example.feedeazy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class frmusermenu extends AppCompatActivity {

    private Button btn_order,btn_fc,btn_history,btn_pwd,btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmusermenu);
        btn_order = findViewById(R.id.btn_cashcollection);
        btn_fc = findViewById(R.id.btn_cashbalance);
        btn_history = findViewById(R.id.btn_menucard);
        btn_pwd = findViewById(R.id.btn_salestrack);
        btn_logout = findViewById(R.id.btn_logout);

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmusermenu.this,frmusermenu.class);
                startActivity(intent);
            }
        });

        btn_fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmusermenu.this,frmusermenu.class);
                startActivity(intent);
            }
        });

        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmusermenu.this,frmusermenu.class);
                startActivity(intent);
            }
        });

        btn_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmusermenu.this,frmchangepassword.class);
                startActivity(intent);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmusermenu.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }
}