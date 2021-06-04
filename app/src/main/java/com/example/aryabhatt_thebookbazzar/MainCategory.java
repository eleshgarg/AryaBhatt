package com.example.aryabhatt_thebookbazzar;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainCategory extends AppCompatActivity{

    private CircleImageView dd1, dd2, dd3, dd4, dd5, dd6, dd7, dd8, dd9;

    String[] state = { "---Select a State---",
            "AP|Andhra Pradesh",
            "AR|Arunachal Pradesh",
            "AS|Assam",
            "BR|Bihar",
            "CT|Chhattisgarh",
            "GA|Goa",
            "GJ|Gujarat",
            "HR|Haryana",
            "HP|Himachal Pradesh",
            "JK|Jammu and Kashmir",
            "JH|Jharkhand",
            "KA|Karnataka",
            "KL|Kerala",
            "MP|Madhya Pradesh",
            "MH|Maharashtra",
            "MN|Manipur",
            "ML|Meghalaya",
            "MZ|Mizoram",
            "NL|Nagaland",
            "OR|Odisha",
            "PB|Punjab",
            "RJ|Rajasthan",
            "SK|Sikkim",
            "TN|Tamil Nadu",
            "TG|Telangana",
            "TR|Tripura",
            "UT|Uttarakhand",
            "UP|Uttar Pradesh",
            "WB|West Bengal",
            "AN|Andaman and Nicobar Islands",
            "CH|Chandigarh",
            "DN|Dadra and Nagar Haveli",
            "DD|Daman and Diu",
            "DL|Delhi",
            "LD|Lakshadweep",
            "PY|Puducherry",
            "Other"};


    String[] schools = { "---Select a School---" , "School" , "School1" , "School2"};
    String[] classes =  { "---Select a Class---" , "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th", "12th"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_category);

        dd1 = findViewById(R.id.dd1);
        dd2 = findViewById(R.id.dd2);
        dd3 = findViewById(R.id.dd3);
        dd4 = findViewById(R.id.dd4);
        dd5 = findViewById(R.id.dd5);
        dd6 = findViewById(R.id.dd6);
        dd7 = findViewById(R.id.dd7);
        dd8 = findViewById(R.id.dd8);
        dd9 = findViewById(R.id.dd9);

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

        final Spinner spin = findViewById(R.id.spinner);
        final Spinner spin1 = findViewById(R.id.spinner1);
        final Spinner spin2 = findViewById(R.id.spinner2);
        Button button = findViewById(R.id.school_wise);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spin.setVisibility(View.VISIBLE);
                spin1.setVisibility(View.VISIBLE);
                spin2.setVisibility(View.VISIBLE);
            }
        });



        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, state);
        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, schools);
        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, classes);
        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(arrayAdapter);
        spin1.setAdapter(arrayAdapter1);
        spin2.setAdapter(arrayAdapter2);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0) {
                    Toast.makeText(MainCategory.this, state[i], Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0) {
                    Toast.makeText(MainCategory.this, schools[i], Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0) {
                    Toast.makeText(MainCategory.this, classes[i], Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(MainCategory.this, HomeActivity.class);
        startActivity(intent);

    }

    public void goToActivity(String name){

        Intent intent = new Intent(MainCategory.this, CategoryMainProduct.class);
        intent.putExtra("category", name);
        startActivity(intent);

    }

}
