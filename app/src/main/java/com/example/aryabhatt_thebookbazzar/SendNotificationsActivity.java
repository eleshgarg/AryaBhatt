package com.example.aryabhatt_thebookbazzar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import io.paperdb.Paper;

public class SendNotificationsActivity extends AppCompatActivity {

    private EditText phonenum, title, content;
    private Button sendbtn;
    private DatabaseReference refrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notifications);

        setTitle("Send Notifications");

        phonenum = findViewById(R.id.send_phone);
        title = findViewById(R.id.send_titles);
        content = findViewById(R.id.send_contents);
        sendbtn = findViewById(R.id.send_button);

        Paper.init(this);


        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phonenum.getText().toString();
                String titles = title.getText().toString();
                String contents  = content.getText().toString();

                String phoneuser = Paper.book().read("phonenumber");

                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(SendNotificationsActivity.this, "Please enter phone number.", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(titles)){
                    Toast.makeText(SendNotificationsActivity.this, "Please enter title.", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(contents)){
                    Toast.makeText(SendNotificationsActivity.this, "Please enter content.", Toast.LENGTH_SHORT).show();
                }else{
                    refrence = FirebaseDatabase.getInstance().getReference().child("Notifications").child( "+91" + phone);

                    HashMap<String, Object> notimap = new HashMap<>();
                    notimap.put("titles", "Title: " + titles);
                    notimap.put("contents", "Message: " + contents);

                    refrence.child(phoneuser + titles).updateChildren(notimap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SendNotificationsActivity.this, "Successfully send.", Toast.LENGTH_SHORT).show();
                            }else{

                                String message = task.getException().toString();

                                Toast.makeText(SendNotificationsActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(SendNotificationsActivity.this, AdminActivity.class);
        startActivity(intent);

    }

}