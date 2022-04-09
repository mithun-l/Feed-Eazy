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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class usersignup extends AppCompatActivity {

    private EditText stxt_email,stxt_pwd;
    private Button sbtn_login,sbtn_signup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersignup);

        stxt_email =  findViewById(R.id.stxt_email);
        stxt_pwd =  findViewById(R.id.stxt_pwd);
        sbtn_login =  findViewById(R.id.sbtn_login);
        sbtn_signup =  findViewById(R.id.sbtn_signup);
        firebaseAuth =  FirebaseAuth.getInstance();
        progressDialog =  new ProgressDialog(this);

        sbtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(usersignup.this,frmuserlogin.class);
                startActivity(intent);
                finish();
            }
        });

        sbtn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail = stxt_email.getText().toString();
                String spwd  =  stxt_pwd.getText().toString();

                if (!TextUtils.isEmpty(semail) &&  !TextUtils.isEmpty(spwd))
                {
                    progressDialog.setMessage("Registering Please Wait....");
                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(semail,spwd)
                            .addOnCompleteListener(usersignup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(usersignup.this,"Successfully Created",Toast.LENGTH_SHORT).show();

                                        firebaseAuth.getCurrentUser().sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful())
                                                        {
                                                            Toast.makeText(usersignup.this,"Successfully Account Created and Link sent to your Email Id." +
                                                                    "Please Verify your Login",Toast.LENGTH_SHORT).show();

                                                            Intent intent =  new Intent(usersignup.this,frmuserlogin.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }

                                                        else
                                                        {
                                                            Toast.makeText(usersignup.this,"Error in Sending Link, Try again",Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                        firebaseAuth.signOut();
                                    }
                                    else
                                    {
                                        Toast.makeText(usersignup.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                    progressDialog.dismiss();

                                }
                            });
                }

                else
                {
                    Toast.makeText(usersignup.this,"Email or Password cannot be Empty",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}