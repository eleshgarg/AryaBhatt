package com.example.aryabhatt_thebookbazzar;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SpalshActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread  thread = new Thread(){

            @Override
            public void run(){
                try {
                    sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(SpalshActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

        };

        thread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();

    }

}
