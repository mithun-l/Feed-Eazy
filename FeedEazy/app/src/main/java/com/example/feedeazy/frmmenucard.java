package com.example.feedeazy;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class frmmenucard extends AppCompatActivity {
    private Button btncreate, btnback, btn_image,menu_btndelete,menu_btn_refresh;
    private EditText txtitemname, txt_qty, txt_desc, txt_rate;
    private Spinner drop_category,spin_menucard;
    private ImageView img1;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private Uri filepath;
    private static final String STORAGE_PATH = "uploads/";

    ValueEventListener listener;
    ArrayList<String> list,list_menucard;
    ArrayAdapter<String> adapter,adapter_menucard;

    ActivityResultLauncher<String> nGetContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmmenucard);
        btncreate = findViewById(R.id.btncreate);
        btnback = findViewById(R.id.btnback);
        txtitemname = findViewById(R.id.txt_itemname);
        drop_category = findViewById(R.id.drop_category);
        spin_menucard = findViewById(R.id.spin_menucard);
        txt_qty = findViewById(R.id.txt_qty);
        txt_desc = findViewById(R.id.txt_desc);
        txt_rate = findViewById(R.id.txt_rate);
        img1 = findViewById(R.id.img1);
        btn_image = findViewById(R.id.btn_image);

        menu_btndelete =  findViewById(R.id.menu_btndelete);
        menu_btn_refresh =  findViewById(R.id.menu_btn_refresh);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("menucard");
        storageReference = FirebaseStorage.getInstance().getReference();

        nGetContent =  registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null)
                {
                    img1.setImageURI(result);
                    filepath =  result;
                }
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nGetContent.launch("image/*");
            }
        });

        list =  new ArrayList<String>();
        adapter =  new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        drop_category.setAdapter(adapter);
        fetchdata();

        list_menucard =  new ArrayList<String>();
        adapter_menucard =  new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list_menucard);
        spin_menucard.setAdapter(adapter_menucard);
        fetchmenucard();

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Globalvariable.loginrole == "admin")
                {
                    Intent intent = new Intent(frmmenucard.this, frmadminmenu.class);
                    startActivity(intent);
                }
            }
        });

        menu_btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_menucard.clear();
                fetchdata();
                adapter_menucard.notifyDataSetChanged();
            }
        });

        menu_btndelete.setOnClickListener(new View.OnClickListener()
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


        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nGetContent.launch("image/*");
//                ProgressDialog progressDialog = new ProgressDialog(frmmenucard.this);
//                progressDialog.setTitle("Image Loading in Progress ...");
//                progressDialog.show();
//                if (ContextCompat.checkSelfPermission(frmmenucard.this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                        == PackageManager.PERMISSION_GRANTED) {
//                    showFileChooser();
//                } else {
//                    ActivityCompat.requestPermissions(frmmenucard.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
               //}
            }
        });

        btncreate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                String s_id = reference.push().getKey();
                String s_txt_itemname = txtitemname.getText().toString();
                String s_category = drop_category.getSelectedItem().toString();
                String s_qty = txt_qty.getText().toString();
                String s_desc = txt_desc.getText().toString();
                String s_rate = txt_rate.getText().toString();

                if (filepath == null) {
                    Toast.makeText(frmmenucard.this, "Image File Path is empty", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!TextUtils.isEmpty(s_txt_itemname) && !TextUtils.isEmpty(s_category) && !TextUtils.isEmpty(s_qty) && !TextUtils.isEmpty(s_desc) && !TextUtils.isEmpty(s_rate) ) {
                    ProgressDialog progressDialog = new ProgressDialog(frmmenucard.this);
                    progressDialog.setTitle("New Item is Adding ...");
                    progressDialog.show();

                    Map<String, String> map = new HashMap<>();
                    map.put("s_id", s_id);
                    map.put("itemname", s_txt_itemname);
                    map.put("category", s_category);
                    map.put("qty", s_qty);
                    map.put("Item Description", s_desc);
                    map.put("Rate", s_rate);

                    if (filepath != null) {
                        String ipath = String.valueOf(System.currentTimeMillis());
                        StorageReference storageReference1 = storageReference.child(STORAGE_PATH +
                                ipath + "." + getExtension(filepath));

                        storageReference1.putFile(filepath)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                map.put("imagefilepath", uri.toString());
                                                reference.child(s_id).setValue(map)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(frmmenucard.this, "Success ! Item Details Added to Server", Toast.LENGTH_LONG).show();
                                                                    txtitemname.setText("");
                                                                    txt_qty.setText("");
                                                                    txt_desc.setText("");
                                                                    txt_rate.setText("");
                                                                } else {

                                                                }
                                                            }
                                                        });
                                            }
                                        });
                                    }
                                })

                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(frmmenucard.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });

                    } else {
                        map.put("imagefilepath", "Nil");
                    }

                } else {
                    Toast.makeText(frmmenucard.this, "Enter Valid Details - Some Fields Empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void deleterecord(){
        try {
            String itemname = spin_menucard.getSelectedItem().toString();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            Query applesQuery = ref.child("menucard").orderByChild("itemname").equalTo(itemname);

            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        appleSnapshot.getRef().removeValue();
                        Toast.makeText(frmmenucard.this, "Selected Menu Item Deleted Successfully", Toast.LENGTH_LONG).show();
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
            Toast.makeText(frmmenucard.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String getExtension(Uri filepath) {
        ContentResolver contentResolver =  getContentResolver();
        MimeTypeMap map = MimeTypeMap.getSingleton();
        return map.getExtensionFromMimeType(contentResolver.getType(filepath));
    }



    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(frmmenucard.this, "Granted", Toast.LENGTH_LONG).show();
        }
    }

    

    public void showFileChooser()
    {
        nGetContent.launch("image/*");
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent,"Select Image"),2);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==2 && resultCode == RESULT_OK && data!= null && data.getData()!=null)
        {
            filepath =  data.getData();
            try {
                Bitmap bitmap =  MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                img1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 }

    public void fetchdata()
    {
        listener  =   database.getReference("categorymaster").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mydata : snapshot.getChildren())
                {
                    list.add(mydata.child("catname").getValue().toString());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fetchmenucard()
    {
        listener  =   database.getReference("menucard").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mydata : snapshot.getChildren())
                {
                    list_menucard.add(mydata.child("itemname").getValue().toString());
                    adapter_menucard.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}