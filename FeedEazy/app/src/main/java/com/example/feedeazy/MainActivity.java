package com.example.feedeazy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button btn_student,btn_cashier,btn_foodcounter,btn_administrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_student = findViewById(R.id.btn_student);
        btn_cashier = findViewById(R.id.btn_cashier);
        btn_foodcounter = findViewById(R.id.btn_foodcounter);
        btn_administrator = findViewById(R.id.btn_administrator);

        btn_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this,frmuserlogin.class);
                startActivity(intent);
            }
        });

        btn_cashier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this,frmcashierlogin.class);
                startActivity(intent);
            }
        });

        btn_foodcounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this,frmfoodcounterlogin.class);
                startActivity(intent);
            }
        });

        btn_administrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this,frmadminlogin.class);
                startActivity(intent);
            }
        });
    }
}