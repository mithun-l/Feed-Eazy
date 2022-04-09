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

public class frmusermanager extends AppCompatActivity {
    private EditText txtroll,um_name,um_email,um_mobile,um_balance,um_pwd;
    private Button btncreate,btnback;
    private Spinner drop_category;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String[] listitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmusermanager);
            try {
                    txtroll = findViewById(R.id.txtroll);
                    um_name = findViewById(R.id.um_name);
                    um_email = findViewById(R.id.um_email);
                    um_mobile = findViewById(R.id.txt_qty);
                    um_balance = findViewById(R.id.txt_rate);
                    um_pwd = findViewById(R.id.um_pwd);
                    drop_category = findViewById(R.id.drop_category);

                    btncreate = findViewById(R.id.btncreate);
                    btnback = findViewById(R.id.btnback);
                    database  =  FirebaseDatabase.getInstance();
                    reference =  database.getReference("usermanager");

//                    listitems = getResources().getStringArray(R.array.usercategory);
//                    ArrayAdapter adapter = new ArrayAdapter(frmusermanager.this, android.R.layout.simple_spinner_dropdown_item, listitems);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    drop_category.setAdapter(adapter);

                btnback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(frmusermanager.this, frmadminmenu.class);
                        startActivity(intent);
                        finish();
                    }
                });

                btncreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String id = reference.push().getKey();
                            String rollno = txtroll.getText().toString();
                            String userm_name = um_name.getText().toString();
                            String userm_category = drop_category.getSelectedItem().toString();
                            String userm_email = um_email.getText().toString();
                            String userm_mobile = um_mobile.getText().toString();
                            String userm_balance = um_balance.getText().toString();
                            String userm_pwd = um_pwd.getText().toString();

                            if (!TextUtils.isEmpty(rollno) && !TextUtils.isEmpty(userm_name) && !TextUtils.isEmpty(userm_category) && !TextUtils.isEmpty(userm_email) && !TextUtils.isEmpty(userm_mobile) && !TextUtils.isEmpty(userm_balance) && !TextUtils.isEmpty(userm_pwd))
                            {
                                ProgressDialog progressDialog = new ProgressDialog(frmusermanager.this);
                                progressDialog.setTitle("UserCreation in Progress");
                                progressDialog.show();

                                Map<String, String> map = new HashMap<>();
                                map.put("userid", id);
                                map.put("rollno", rollno);
                                map.put("username", userm_name);
                                map.put("category", userm_category);
                                map.put("email", userm_email);
                                map.put("mobile", userm_mobile);
                                map.put("accountbalance", userm_balance);
                                map.put("password", userm_pwd);

                              //  Toast.makeText(frmusermanager.this, id, Toast.LENGTH_LONG).show();

                                reference.child(id).setValue(map)
                                        .addOnCompleteListener(new OnCompleteListener<Void>()
                                        {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                if (task.isSuccessful())
                                                {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(frmusermanager.this, "Success ! New User Details Added to Server", Toast.LENGTH_LONG).show();
                                                    txtroll.setText("");
                                                    um_name.setText("");
                                                    um_email.setText("");
                                                    um_mobile.setText("");
                                                    um_balance.setText("");
                                                    um_pwd.setText("");
                                                    txtroll.setFocusableInTouchMode(true);
                                                    txtroll.setFocusable(true);
                                                }
                                                else {
                                                    Toast.makeText(frmusermanager.this, "Unable to add data", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                            }

                            else {
                                Toast.makeText(frmusermanager.this, "Enter Valid Details - Some Fields Empty", Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception ex) {
                            Toast.makeText(frmusermanager.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } catch (Exception ex) {
                Toast.makeText(frmusermanager.this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }

          }
}