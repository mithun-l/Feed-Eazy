package com.example.feedeazy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class frmcashcollection extends AppCompatActivity {

    private TextView txt_no,txt_date,txt_id,txt_amount;
    private Button btnreceived,btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmcashcollection);

        txt_no = findViewById(R.id.txt_no);
        txt_date = findViewById(R.id.txt_date);
        txt_id = findViewById(R.id.txt_itemname);
        txt_amount = findViewById(R.id.txt_qty);
        btnreceived = findViewById(R.id.btncreate);
        btnback = findViewById(R.id.btnback);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmcashcollection.this,frmcashiermenu.class);
                startActivity(intent);
            }
        });
    }
}