package com.example.aryabhatt_thebookbazzar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class OrdersActivity extends AppCompatActivity {

    private EditText name, email, address, pincode, city, state;
    private Button order;
    private DatabaseReference reference;
    private String namee ;
    private String despe ;
    private String pricee ;
    private String datee ;
    private String timee ;
    private String phonenumber ;
    private String imagee1 ;
    private String imagee2 ;
    private String imagee3 ;
    private String imagee4 ;
    private String quantity ;
    private String categorye;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        setTitle("Order Product");


        name = findViewById(R.id.edit_seller_reg_name);
        email = findViewById(R.id.edit_seller_reg_email);
        address = findViewById(R.id.edit_seller_reg_company_name);
        pincode = findViewById(R.id.edit_seller_reg_pan_card);
        city = findViewById(R.id.edit_seller_reg_city);
        state = findViewById(R.id.edit_seller_reg_state);
        order = findViewById(R.id.edit_seller_reg_button);

        Paper.init(this);

        String phoneuser = Paper.book().read("phonenumber");

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Orderss").child(phoneuser).child("Orders").child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String getname = snapshot.child("name").getValue().toString();
                    String getemail =  snapshot.child("email address").getValue().toString();
                    String getaddress =  snapshot.child("address").getValue().toString();
                    String getpincode = snapshot.child("pincode").getValue().toString();
                    String getstate =  snapshot.child("state").getValue().toString();
                    String getcity = snapshot.child("city").getValue().toString();
                    //String getimage = "Password: " + snapshot.child("password").getValue().toString();

                    name.setText(getname);
                    email.setText(getemail);
                    address.setText(getaddress);
                    city.setText(getcity);
                    pincode.setText(getpincode);
                    state.setText(getstate);

                }else{
                    Toast.makeText(OrdersActivity.this, "Please Edit first.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String myname = name.getText().toString();
                String myemail = email.getText().toString();
                String myaddress = address.getText().toString();
                String mypincode = pincode.getText().toString();
                String mycity = city.getText().toString();
                String mystate = state.getText().toString();

                if (TextUtils.isEmpty(myname)){

                    Toast.makeText(OrdersActivity.this, "Please enter name.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(myemail)){

                    Toast.makeText(OrdersActivity.this, "Please enter email address.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(myaddress)){

                    Toast.makeText(OrdersActivity.this, "Please enter address.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(mypincode)){

                    Toast.makeText(OrdersActivity.this, "Please enter pin code.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(mycity)){

                    Toast.makeText(OrdersActivity.this, "Please enter city.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(mystate)){

                    Toast.makeText(OrdersActivity.this, "Please enter state.", Toast.LENGTH_SHORT).show();

                }else{

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
        final HashMap<String, Object> editprofilemap = new HashMap<>();
        editprofilemap.put("name", myname);
        editprofilemap.put("email address", myemail);
        editprofilemap.put("address", myaddress);
        editprofilemap.put("pincode", mypincode);
        editprofilemap.put("state", mystate);
        editprofilemap.put("city", mycity);

        reference.child("Orderss").child(phoneuser).child("Orders").child("Orders").updateChildren(editprofilemap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                             namee = getIntent().getStringExtra("name");
                             despe = getIntent().getStringExtra("desp");
                             pricee = getIntent().getStringExtra("price");
                             datee = getIntent().getStringExtra("date");
                             timee = getIntent().getStringExtra("time");
                             phonenumber = getIntent().getStringExtra("phone");
//                             imagee1 = getIntent().getStringExtra("image1");
//                             imagee2 = getIntent().getStringExtra("image2");
//                             imagee3 = getIntent().getStringExtra("image3");
                             imagee4 = getIntent().getStringExtra("image4");
                             quantity = getIntent().getStringExtra("quantity");
                             categorye = getIntent().getStringExtra("category");


                            HashMap<String, Object> cartmap = new HashMap<>();
                            cartmap.put("productname", namee);
                            cartmap.put("description", despe);
                            cartmap.put("price", pricee);
                            cartmap.put("date", datee);
                            cartmap.put("time", timee);
                            cartmap.put("phonenumber", phonenumber);
//                            cartmap.put("image1", imagee1);
//                            cartmap.put("image2", imagee2);
//                            cartmap.put("image3", imagee3);
                            cartmap.put("image4", imagee4);
                            cartmap.put("quantity", quantity);
                            cartmap.put("name", myname);
                            cartmap.put("email address", myemail);
                            cartmap.put("address", myaddress);
                            cartmap.put("pincode", mypincode);
                            cartmap.put("state", mystate);
                            cartmap.put("city", mycity);
                            cartmap.put("phonenumberuser", phoneuser);
                            cartmap.put("category",categorye);

                            reference.child("SellerOrders").child(phonenumber).child(phoneuser + pricee + datee + timee).updateChildren(cartmap);

                            reference.child("Orders").child(phoneuser).child(phonenumber  + pricee + datee + timee).updateChildren(cartmap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                //Toast.makeText(OrdersActivity.this, "Order successfully.", Toast.LENGTH_SHORT).show();
                                            }else{
                                                String message = task.getException().toString();
                                                Toast.makeText(OrdersActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                            changeActivity();
                            //Toast.makeText(OrdersActivity.this, "Congratulations!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(OrdersActivity.this, "Please retry.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    private void changeActivity() {

        final String phoneuserr = Paper.book().read("phonenumber");

        AlertDialog.Builder dialog = new AlertDialog.Builder(OrdersActivity.this);
        dialog.setMessage("Select payment method.");
        dialog.setPositiveButton("COD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(OrdersActivity.this, MyOrdersActivity.class);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                HashMap<String, Object> map = new HashMap<>();
                map.put("paymethod", "COD");
                ref.child("SellerOrders").child(phonenumber).child(phoneuserr + pricee + datee + timee).updateChildren(map);
                ref.child("Orders").child(phoneuserr).child(phonenumber+ pricee + datee + timee).updateChildren(map);
                startActivity(intent);

            }
        });
        dialog.setNegativeButton("Pay Online", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(OrdersActivity.this, PaymentActivity.class);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                HashMap<String, Object> map = new HashMap<>();
                map.put("paymethod", "Online");
                ref.child("Orders").child(phoneuserr).child(phonenumber  + pricee + datee + timee).updateChildren(map);
                ref.child("SellerOrders").child(phonenumber).child(phoneuserr + pricee + datee + timee).updateChildren(map);
                startActivity(intent);
            }
        });
        dialog.create();
        dialog.show();

    }

}