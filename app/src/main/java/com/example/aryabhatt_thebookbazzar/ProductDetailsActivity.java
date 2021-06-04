package com.example.aryabhatt_thebookbazzar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import io.paperdb.Paper;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView image1, image2, image3, image4;
    private TextView name, desp, price, date, time;
    private Button cartbtn;
    private ElegantNumberButton numberbtn;
    private DatabaseReference refrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        setTitle("Add to Cart");

        refrence = FirebaseDatabase.getInstance().getReference();

        Paper.init(this);

        final String UserPhoneKeyy = Paper.book().read("phonenumber");

//        image1 = findViewById(R.id.details_pro_image1);
//        image2 = findViewById(R.id.details_pro_image2);
//        image3 = findViewById(R.id.details_pro_image3);
        image4 = findViewById(R.id.details_pro_image4);
        name = findViewById(R.id.details_show_pro_name);
        desp = findViewById(R.id.details_show_pro_desp);
        price = findViewById(R.id.details_show_pro_price);
        date = findViewById(R.id.details_show_pro_date);
        time = findViewById(R.id.details_show_pro_time);
        cartbtn = findViewById(R.id.details_show_pro_btn);
        numberbtn = findViewById(R.id.number_btn);

        final String namee = getIntent().getStringExtra("name");
        final String despe = getIntent().getStringExtra("desp");
        final String pricee = getIntent().getStringExtra("price");
        final String datee = getIntent().getStringExtra("date");
        final String timee = getIntent().getStringExtra("time");
        final String phonenumber = getIntent().getStringExtra("phone");
//        final String imagee1 = getIntent().getStringExtra("image1");
//        final String imagee2 = getIntent().getStringExtra("image2");
//        final String imagee3 = getIntent().getStringExtra("image3");
        final String imagee4 = getIntent().getStringExtra("image4");
        final String categorye = getIntent().getStringExtra("category");

        name.setText(namee);
        desp.setText(despe);
        price.setText(pricee + "Rs");
        date.setText(datee);
        time.setText(timee);

//        Picasso.get().load(imagee1).into(image1);
//        Picasso.get().load(imagee2).into(image2);
//        Picasso.get().load(imagee3).into(image3);
        Picasso.get().load(imagee4).into(image4);

        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> cartmap = new HashMap<>();
                cartmap.put("productname", namee);
                cartmap.put("description", despe);
                cartmap.put("price", pricee);
                cartmap.put("date", datee);
                cartmap.put("time", timee);
                cartmap.put("phonenumber", phonenumber);
//                cartmap.put("image1", imagee1);
//                cartmap.put("image2", imagee2);
//                cartmap.put("image3", imagee3);
                cartmap.put("image4", imagee4);
                cartmap.put("quantity", numberbtn.getNumber());
                cartmap.put("category", categorye);

                refrence.child("Cart").child(UserPhoneKeyy).child(phonenumber + pricee + datee + timee).updateChildren(cartmap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ProductDetailsActivity.this, "Added successfully.", Toast.LENGTH_SHORT).show();
                                }else{
                                    String message = task.getException().toString();
                                    Toast.makeText(ProductDetailsActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



            }
        });

    }

}