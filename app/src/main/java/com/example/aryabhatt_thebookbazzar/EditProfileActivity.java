package com.example.aryabhatt_thebookbazzar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import io.paperdb.Paper;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView myimage;
    private EditText name, email, address, pincode, city, state;
    private Button update;
    private DatabaseReference reference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setTitle("Edit Profile");


        name = findViewById(R.id.edit_seller_reg_name);
        email = findViewById(R.id.edit_seller_reg_email);
        address = findViewById(R.id.edit_seller_reg_company_name);
        pincode = findViewById(R.id.edit_seller_reg_pan_card);
        city = findViewById(R.id.edit_seller_reg_city);
        state = findViewById(R.id.edit_seller_reg_state);
        update = findViewById(R.id.edit_seller_reg_button);



        Paper.init(this);

        String phoneuser = Paper.book().read("phonenumber");





        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Users").child(phoneuser).child("Personal Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String getname = snapshot.child("name").getValue().toString();
                    String getemail = snapshot.child("email address").getValue().toString();
                    String getaddress = snapshot.child("address").getValue().toString();
                    String getpincode = snapshot.child("pincode").getValue().toString();
                    String getstate = snapshot.child("state").getValue().toString();
                    String getcity = snapshot.child("city").getValue().toString();
                    //String images = snapshot.child("image").getValue().toString();
                    //String getimage = "Password: " + snapshot.child("password").getValue().toString();

                    name.setText(getname);
                    email.setText(getemail);
                    address.setText(getaddress);
                    city.setText(getcity);
                    pincode.setText(getpincode);
                    state.setText(getstate);
                    //Picasso.get().load(images).into(myimage);


                } else {
                    Toast.makeText(EditProfileActivity.this, "Please Edit First.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myname = name.getText().toString();
                String myemail = email.getText().toString();
                String myaddress = address.getText().toString();
                String mypincode = pincode.getText().toString();
                String mycity = city.getText().toString();
                String mystate = state.getText().toString();


                if (TextUtils.isEmpty(myname)){

                    Toast.makeText(EditProfileActivity.this, "Please enter name.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(myemail)){

                    Toast.makeText(EditProfileActivity.this, "Please enter email address.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(myaddress)){

                    Toast.makeText(EditProfileActivity.this, "Please enter address.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(mypincode)){

                    Toast.makeText(EditProfileActivity.this, "Please enter pin code.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(mycity)){

                    Toast.makeText(EditProfileActivity.this, "Please enter city.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(mystate)){

                    Toast.makeText(EditProfileActivity.this, "Please enter state.", Toast.LENGTH_SHORT).show();

                }else {








                    saveInFirebase();
                }
            }
        });


    }



    private void saveInFirebase() {

        final String myname = name.getText().toString();
        final String myemail = email.getText().toString();
        final String myaddress = address.getText().toString();
        final String mypincode = pincode.getText().toString();
        final String mycity = city.getText().toString();
        final String mystate = state.getText().toString();


        final String phoneuser = Paper.book().read("phonenumber");


        reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> editprofilemap = new HashMap<>();
        editprofilemap.put("name", myname);
        editprofilemap.put("email address", myemail);
        editprofilemap.put("address", myaddress);

        editprofilemap.put("pincode", mypincode);
        editprofilemap.put("state", mystate);
        editprofilemap.put("city", mycity);


        reference.child("Users").child(phoneuser).child("Personal Details").updateChildren(editprofilemap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            changeActivity();

                            Toast.makeText(EditProfileActivity.this, "Successfully edit!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Please retry.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(EditProfileActivity.this, SettingsActivity.class);
        startActivity(intent);

    }

    private void changeActivity() {

        Intent intent = new Intent(EditProfileActivity.this, SettingsActivity.class);
        startActivity(intent);

    }

}