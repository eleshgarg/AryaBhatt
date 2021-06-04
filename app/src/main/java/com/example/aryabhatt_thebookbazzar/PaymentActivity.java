package com.example.aryabhatt_thebookbazzar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    private double amount = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);



    }

    @Override
    protected void onStart() {
        super.onStart();



        Checkout co = new Checkout();

        co.setKeyID("rzp_test_vuQ3oMWm79F65z");

        JSONObject options = new JSONObject();

        Activity activity = this;

        try {

            options.put("name", "AryaBhatta - The Book Bazzar");
            options.put("description", "Welcome to online payment");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");//pass amount in currency subunits
            options.put("prefill.email", "example.@example.com");
            options.put("prefill.contact","9876543210");
            co.open(activity, options);
        }catch (Exception e){

            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onPaymentSuccess(String s) {

        Toast.makeText(this, "Thankyou for shopping with us.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PaymentActivity.this, MyOrdersActivity.class);
        startActivity(intent);

    }

    @Override
    public void onPaymentError(int i, String s) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(PaymentActivity.this, MyOrdersActivity.class);
        startActivity(intent);

    }
}