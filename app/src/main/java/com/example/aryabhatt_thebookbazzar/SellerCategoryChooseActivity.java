package com.example.aryabhatt_thebookbazzar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import de.hdodenhof.circleimageview.CircleImageView;

public class SellerCategoryChooseActivity extends AppCompatActivity {

    private CircleImageView dd1, dd2, dd3, dd4, dd5, dd6, dd7, dd8, dd9;
    private Button editprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_category_choose);

        setTitle("Choose Category");

        dd1 = findViewById(R.id.dd1);
        dd2 = findViewById(R.id.dd2);
        dd3 = findViewById(R.id.dd3);
        dd4 = findViewById(R.id.dd4);
        dd5 = findViewById(R.id.dd5);
        dd6 = findViewById(R.id.dd6);
        dd7 = findViewById(R.id.dd7);
        dd8 = findViewById(R.id.dd8);
        dd9 = findViewById(R.id.dd9);

        editprofile = findViewById(R.id.edit_profile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerCategoryChooseActivity.this, SellerEditInfoActivity.class);
                startActivity(intent);
            }
        });

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

        Intent intent = new Intent(SellerCategoryChooseActivity.this, HomeActivity.class);
        startActivity(intent);

    }

    public void goToActivity(String name){

        Intent intent = new Intent(SellerCategoryChooseActivity.this, SellerAddProductActivity.class);
        intent.putExtra("category", name);
        startActivity(intent);

    }

}