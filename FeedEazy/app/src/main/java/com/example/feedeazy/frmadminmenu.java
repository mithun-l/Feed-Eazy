package com.example.feedeazy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class frmadminmenu extends AppCompatActivity {

    private Button  btn_usermanager,btn_om,btn_menucard,btn_salestrack,btn_logout,btn_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmadminmenu);

        btn_usermanager = findViewById(R.id.btn_um);
        btn_om = findViewById(R.id.btn_om);
        btn_menucard = findViewById(R.id.btn_menucard);
        btn_salestrack = findViewById(R.id.btn_salestrack);
        btn_category = findViewById(R.id.btn_category);
        btn_logout = findViewById(R.id.btn_logout);

        btn_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmadminmenu.this,frmcategory.class);
                startActivity(intent);
            }
        });

        btn_usermanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmadminmenu.this,frmusermanager.class);
                startActivity(intent);
            }
        });

        btn_om.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmadminmenu.this,frmoperationmanager.class);
                startActivity(intent);
            }
        });

        btn_menucard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globalvariable.loginrole = "admin";
                Intent intent =  new Intent(frmadminmenu.this,frmmenucard.class);
                startActivity(intent);
            }
        });

        btn_salestrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmadminmenu.this,frmadminmenu.class);
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmadminmenu.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}