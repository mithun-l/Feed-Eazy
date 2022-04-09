package com.example.feedeazy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class frmchangepassword extends AppCompatActivity {

    private Button btnchange,btnback;
    private TextView txt_old,txt_new,txt_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmchangepassword);

        btnchange = findViewById(R.id.btncreate);
        btnback = findViewById(R.id.btnback);
        txt_old = findViewById(R.id.txt_old);
        txt_new = findViewById(R.id.txt_new);
        txt_confirm = findViewById(R.id.txt_confirm);

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmchangepassword.this,frmusermenu.class);
                startActivity(intent);
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmchangepassword.this,frmusermenu.class);
                startActivity(intent);
            }
        });

    }
}