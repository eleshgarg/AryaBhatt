package com.example.aryabhatt_thebookbazzar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class SellerEditInfoActivity extends AppCompatActivity {

    private EditText name, emailaddress, phone, companyname, pancardnumber, city, password;
    private Button registerbtn;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_edit_info);

        setTitle("Edit Info");

        Paper.init(this);

        name = findViewById(R.id.edit_seller_reg_name);
        emailaddress = findViewById(R.id.edit_seller_reg_email);
        phone = findViewById(R.id.edit_seller_reg_phone);
        companyname = findViewById(R.id.edit_seller_reg_company_name);
        pancardnumber = findViewById(R.id.edit_seller_reg_pan_card);
        city = findViewById(R.id.edit_seller_reg_city);
        password = findViewById(R.id.edit_seller_reg_password);
        registerbtn = findViewById(R.id.edit_seller_reg_button);

        String phoneuser = Paper.book().read("phonenumber");

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Seller Details").child(phoneuser).child("Personal Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String getname = snapshot.child("name").getValue().toString();
                    String getemail = snapshot.child("email address").getValue().toString();
                    String getphone = snapshot.child("phone number").getValue().toString();
                    String getcompany = snapshot.child("company name").getValue().toString();
                    String getpan = snapshot.child("pan card number").getValue().toString();
                    String getcity =  snapshot.child("city").getValue().toString();
                    String getpassword = snapshot.child("password").getValue().toString();

                    name.setText(getname);
                    pancardnumber.setText(getpan);
                    password.setText(getpassword);
                    city.setText(getcity);
                    companyname.setText(getcompany);
                    phone.setText(getphone);
                    emailaddress.setText(getemail);

                }else{
                    Toast.makeText(SellerEditInfoActivity.this, "Try Again Later.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sellname = name.getText().toString();
                String sellemail = emailaddress.getText().toString();
                String sellphone = phone.getText().toString();
                String sellcompany = companyname.getText().toString();
                String sellpancard = pancardnumber.getText().toString();
                String sellcity = city.getText().toString();
                String sellpassword = password.getText().toString();

                if (TextUtils.isEmpty(sellname)){

                    Toast.makeText(SellerEditInfoActivity.this, "Please enter name.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(sellemail)){

                    Toast.makeText(SellerEditInfoActivity.this, "Please enter email address.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(sellpassword)){

                    Toast.makeText(SellerEditInfoActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(sellphone)){

                    Toast.makeText(SellerEditInfoActivity.this, "Please enter phone number.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(sellcompany)){

                    Toast.makeText(SellerEditInfoActivity.this, "Please enter company name.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(sellpancard)){

                    Toast.makeText(SellerEditInfoActivity.this, "Please enter pan card number.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(sellcity)){

                    Toast.makeText(SellerEditInfoActivity.this, "Please enter city.", Toast.LENGTH_SHORT).show();

                }else{

                    saveInFirebase();

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(SellerEditInfoActivity.this, SellerCategoryChooseActivity.class);
        startActivity(intent);

    }

    private void saveInFirebase() {

        String sellname = name.getText().toString();
        String sellemail = emailaddress.getText().toString();
        String sellphone = phone.getText().toString();
        String sellcompany = companyname.getText().toString();
        String sellpancard = pancardnumber.getText().toString();
        String sellcity = city.getText().toString();
        String sellpassword = password.getText().toString();
        String phoneuser = Paper.book().read("phonenumber");



        reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> sellmap = new HashMap<>();
        sellmap.put("name",sellname);
        sellmap.put("email address",sellemail);
        sellmap.put("phone number",sellphone);
        sellmap.put("company name",sellcompany);
        sellmap.put("pan card number",sellpancard);
        sellmap.put("city",sellcity);
        sellmap.put("password",sellpassword);

        reference.child("Seller Details").child(phoneuser).child("Personal Details").updateChildren(sellmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            changeActivity();
                            Toast.makeText(SellerEditInfoActivity.this, "Successfully edit!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SellerEditInfoActivity.this, "Please retry.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void changeActivity() {

        Intent intent = new Intent(SellerEditInfoActivity.this, SellerCategoryChooseActivity.class);
        startActivity(intent);

    }

}