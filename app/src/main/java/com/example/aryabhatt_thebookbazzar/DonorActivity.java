package com.example.aryabhatt_thebookbazzar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonorActivity extends AppCompatActivity {

    private CircleImageView dd1, dd2, dd3, dd4, dd5, dd6, dd7, dd8, dd9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);

        setTitle("Choose Category");

        dd1 = findViewById(R.id.dda1);
        dd2 = findViewById(R.id.dda2);
        dd3 = findViewById(R.id.dda3);
        dd4 = findViewById(R.id.dda4);
        dd5 = findViewById(R.id.dda5);
        dd6 = findViewById(R.id.dda6);
        dd7 = findViewById(R.id.dda7);
        dd8 = findViewById(R.id.dda8);
        dd9 = findViewById(R.id.dda9);

        dd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity("Art & Photography");
            }
        });

        dd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity("Romance");
            }
        });

        dd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity("Teens & Young Adults");
            }
        });

        dd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity("Sci-fi & Fantasy");
            }
        });

        dd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity("Mystery & Suspense");
            }
        });

        dd6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity("Litrature & Fiction");
            }
        });

        dd7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity("Biographies");
            }
        });

        dd8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity("Business & Investing");
            }
        });

        dd9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity("History");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(DonorActivity.this, HomeActivity.class);
        startActivity(intent);

    }

    public void goToActivity(String name){

        Intent intent = new Intent(DonorActivity.this, DonorAddProductActivity.class);
        intent.putExtra("category", name);
        startActivity(intent);

    }

}