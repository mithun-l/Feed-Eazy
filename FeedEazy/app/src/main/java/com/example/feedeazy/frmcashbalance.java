package com.example.feedeazy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class frmcashbalance extends AppCompatActivity {

    private TextView txtid,txt_balamount;
    private Button btnbalance,btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmcashbalance);

        txtid = findViewById(R.id.txt_itemname);
        txt_balamount = findViewById(R.id.txt_qty);
        btnbalance = findViewById(R.id.btncreate);
        btnback = findViewById(R.id.btnback);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmcashbalance.this,frmcashiermenu.class);
                startActivity(intent);
            }
        });
    }
}