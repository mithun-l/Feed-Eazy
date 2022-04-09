package com.example.feedeazy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class frmoperationmanager extends AppCompatActivity {

    private EditText txt_staffname,txt_mobile,txt_email,txt_loginname,txt_loginpwd;
    private Button btncreate,btnback;
    private Spinner drop_role;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String[] listitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmoperationmanager);
        try {
            txt_staffname = findViewById(R.id.txt_itemname);
            txt_mobile = findViewById(R.id.txt_mobile);
            txt_email = findViewById(R.id.txt_email);
            txt_loginname = findViewById(R.id.txt_loginname);
            txt_loginpwd = findViewById(R.id.txt_loginpwd);
            drop_role = findViewById(R.id.drop_role);

            btncreate = findViewById(R.id.btncreate);
            btnback = findViewById(R.id.btnback);
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("operationmanager");


            btnback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(frmoperationmanager.this, frmadminmenu.class);
                    startActivity(intent);
                    finish();
                }
            });

            btncreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String id = reference.push().getKey();
                        String staffname = txt_staffname.getText().toString();
                        String mobileno = txt_mobile.getText().toString();
                        String staffemail = txt_email.getText().toString();
                        String staffrole = drop_role.getSelectedItem().toString();
                        String staffloginname = txt_loginname.getText().toString();
                        String staffpwd = txt_loginpwd.getText().toString();

                        if (!TextUtils.isEmpty(staffname) && !TextUtils.isEmpty(mobileno) && !TextUtils.isEmpty(staffemail) && !TextUtils.isEmpty(staffrole) && !TextUtils.isEmpty(staffloginname) && !TextUtils.isEmpty(staffpwd)) {
                            ProgressDialog progressDialog = new ProgressDialog(frmoperationmanager.this);
                            progressDialog.setTitle("Operation Manager Creation in Progress");
                            progressDialog.show();

                            Map<String, String> map = new HashMap<>();
                            map.put("staffid", id);
                            map.put("staffname", staffname);
                            map.put("mobileno", mobileno);
                            map.put("staffemail", staffemail);
                            map.put("staffrole", staffrole);
                            map.put("staffloginname", staffloginname);
                            map.put("staffpwd", staffpwd);
                            reference.child(id).setValue(map)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                Toast.makeText(frmoperationmanager.this, "Success ! New Operation Manager Details Added to Server", Toast.LENGTH_LONG).show();
                                                txt_staffname.setText("");
                                                txt_mobile.setText("");
                                                txt_email.setText("");
                                                txt_loginname.setText("");
                                                txt_loginpwd.setText("");
                                                txt_staffname.requestFocus();
                                            } else {
                                                Toast.makeText(frmoperationmanager.this, "Unable to add data", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                        } else {
                            Toast.makeText(frmoperationmanager.this, "Enter Valid Details - Some Fields Empty", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(frmoperationmanager.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception ex) {
            Toast.makeText(frmoperationmanager.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}