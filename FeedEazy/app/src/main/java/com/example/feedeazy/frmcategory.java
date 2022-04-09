package com.example.feedeazy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class frmcategory extends AppCompatActivity {
    private EditText txt_catname;
    private Button cat_btn_save, cat_btn_menu,cat_btndelete,btn_refresh;
    Spinner spin_catname;
    String dr_catname;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    ValueEventListener listener;
    ArrayList<String> list;
    public ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frmcategory);
        txt_catname = findViewById(R.id.txt_catname);

        btn_refresh =  findViewById(R.id.menu_btn_refresh);
        cat_btn_save = findViewById(R.id.cat_btn_save);
        cat_btn_menu = findViewById(R.id.cat_btn_menu);
        cat_btndelete = findViewById(R.id.menu_btndelete);
        spin_catname =  findViewById(R.id.spin_menucard);

        database  =  FirebaseDatabase.getInstance();
        reference =  database.getReference("categorymaster");

        list =  new ArrayList<String>();
        adapter =  new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        spin_catname.setAdapter(adapter);
        fetchdata();

        cat_btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(frmcategory.this, frmadminmenu.class);
                startActivity(intent);
                finish();
            }
        });

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                fetchdata();
                adapter.notifyDataSetChanged();
            }
        });

        cat_btndelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {
                    deleterecord();
                }

                catch (Exception ex)
                {
                }
            }
        });

        cat_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              insertdata();
            }
        });
    }

    public void refreshdata()
    {
        list.clear();
        fetchdata();
        adapter.notifyDataSetChanged();
    }

    public void fetchdata()
    {
        listener  =   database.getReference("categorymaster").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mydata : snapshot.getChildren())
                {
                    list.add(mydata.child("catname").getValue().toString());
                }

                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void deleterecord(){
        try {
            String catname = spin_catname.getSelectedItem().toString();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            Query applesQuery = ref.child("categorymaster").orderByChild("catname").equalTo(catname);

            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        appleSnapshot.getRef().removeValue();
                        Toast.makeText(frmcategory.this, "Selected Category Deleted Successfully", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Tag", "onCancelled", databaseError.toException());
                }
            });
        }

        catch (Exception ex)
        {
            Toast.makeText(frmcategory.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void insertdata()
    {
        try {
            String id = reference.push().getKey();
            String catname = txt_catname.getText().toString().trim();
            if (!TextUtils.isEmpty(catname) ) {
                ProgressDialog progressDialog = new ProgressDialog(frmcategory.this);
                progressDialog.setTitle("Category Adding in Progress");
                progressDialog.show();

                Map<String, String> map = new HashMap<>();
                map.put("catid", id);
                map.put("catname", catname);
                reference.child(id).setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(frmcategory.this, "Success ! New Category Added to Server", Toast.LENGTH_LONG).show();
                                    txt_catname.setText("");
                                    list.clear();
                                    fetchdata();
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(frmcategory.this, "Unable to add category", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(frmcategory.this, "Enter Valid Details - Some Fields Empty", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(frmcategory.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}