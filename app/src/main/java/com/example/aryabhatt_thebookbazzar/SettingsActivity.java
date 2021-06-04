package com.example.aryabhatt_thebookbazzar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class SettingsActivity extends AppCompatActivity {

    private Button editprofile, admin, contactus;
    private DatabaseReference reference;
    private TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("Settings");

        Paper.init(this);
        String phoneuser = Paper.book().read("phonenumber");

        reference = FirebaseDatabase.getInstance().getReference().child("Admins").child(phoneuser).child("Personal Details");

        editprofile = findViewById(R.id.settings_edit_profile_btn);
        admin = findViewById(R.id.settings_admin_btn);
        contactus = findViewById(R.id.settings_contactus_btn);
        welcome = findViewById(R.id.settings_welcome);

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,ContactUsActivity.class);
                startActivity(intent);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Intent intent = new Intent(SettingsActivity.this, AdminActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SettingsActivity.this, "You are not an admin.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        welcome.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(SettingsActivity.this, "Thanks for using AryaBhatta", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
        startActivity(intent);

    }

}