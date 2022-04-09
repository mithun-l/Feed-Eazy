package com.example.feedeazy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class frmcashiermenu extends AppCompatActivity {

    private Button btn_cashcollection,btn_cashbalance,btn_logout,btn_menucard2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmcashiermenu);

        btn_cashcollection = findViewById(R.id.btn_cashcollection);
        btn_cashbalance = findViewById(R.id.btn_cashbalance);
        btn_logout = findViewById(R.id.btn_logout);
        btn_menucard2 = findViewById(R.id.btn_menucard2);


        btn_cashcollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmcashiermenu.this,frmcashcollection.class);
                startActivity(intent);
            }
        });

        btn_cashbalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmcashiermenu.this,frmcashbalance.class);
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmcashiermenu.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btn_menucard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmcashiermenu.this,frmmenucard.class);
                startActivity(intent);
            }
        });
    }
}