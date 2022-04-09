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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class frmcashierlogin extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button btnlogin,btnsignup;
    private ProgressDialog progressDialog;
    private EditText txtemail,txtpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmcashierlogin);


        btnlogin = findViewById(R.id.btnlogin);
        btnsignup = findViewById(R.id.btnsignup);
        txtemail = findViewById(R.id.txtemail);
        txtpwd = findViewById(R.id.txtpwd);
        firebaseAuth =  FirebaseAuth.getInstance();
        progressDialog =  new ProgressDialog(this);

        if(firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().isEmailVerified())
        {
            Intent intent =  new Intent(frmcashierlogin.this,frmcashiermenu.class);
            startActivity(intent);
            finish();
        }

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(frmcashierlogin.this,frmcashiersignup.class);
                startActivity(intent);
                finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail = txtemail.getText().toString();
                String spwd  =  txtpwd.getText().toString();

                if (!TextUtils.isEmpty(semail) &&  !TextUtils.isEmpty(spwd))
                {
                    progressDialog.setMessage("User Account Checking....");
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(semail,spwd)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful())
                                    {
                                        if (firebaseAuth.getCurrentUser().isEmailVerified())
                                        {
                                            progressDialog.dismiss();
                                            Intent intent =  new Intent(frmcashierlogin.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                        else
                                        {
                                            Toast.makeText(frmcashierlogin.this,"Please verify your Email and Continue...",Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(frmcashierlogin.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            });

                }

            }
        });
    }
}