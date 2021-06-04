package com.example.aryabhatt_thebookbazzar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button CreateAccountButton, verifybtn;
    private EditText InputName, InputPhoneNumber, Inputotp;
    private ProgressDialog loadingbar;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private String name, phone;
    private DatabaseReference Rootref, notiref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);

        mAuth = FirebaseAuth.getInstance();

        CreateAccountButton = findViewById(R.id.register_btn);
        InputName = findViewById(R.id.register_name_input);
        InputPhoneNumber = findViewById(R.id.register_phone_number);
        loadingbar = new ProgressDialog(this);
        verifybtn = findViewById(R.id.register_verify_btn);
        Inputotp = findViewById(R.id.register_otp);

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(MainActivity.this, "Invalid Phone Number" + e.toString(), Toast.LENGTH_SHORT).show();
                InputName.setVisibility(View.VISIBLE);
                Inputotp.setVisibility(View.INVISIBLE);
                InputPhoneNumber.setVisibility(View.VISIBLE);
                CreateAccountButton.setVisibility(View.VISIBLE);
                verifybtn.setVisibility(View.INVISIBLE);

                loadingbar.dismiss();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                mVerificationId = s;
                mResendToken = forceResendingToken;

                Toast.makeText(MainActivity.this, "Otp sent successfully", Toast.LENGTH_SHORT).show();

                InputName.setVisibility(View.INVISIBLE);
                Inputotp.setVisibility(View.VISIBLE);
                InputPhoneNumber.setVisibility(View.INVISIBLE);
                CreateAccountButton.setVisibility(View.INVISIBLE);
                verifybtn.setVisibility(View.VISIBLE);

                loadingbar.dismiss();
            }

        };

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = InputName.getText().toString();
                phone = InputPhoneNumber.getText().toString();

                if (TextUtils.isEmpty(name)){

                    Toast.makeText(MainActivity.this, "Please write your name...", Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(phone)){

                    Toast.makeText(MainActivity.this, "Please enter your phone number...", Toast.LENGTH_SHORT).show();

                }else{
                    phone = "+91" + InputPhoneNumber.getText().toString();

                    loadingbar.setTitle("Phone Verification");
                    loadingbar.setMessage("Please wait we are authenticating.");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phone,
                            60,
                            TimeUnit.SECONDS,
                            MainActivity.this,
                            callbacks
                    );

                }
            }
        });


        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String verificationcode = Inputotp.getText().toString();
                if (TextUtils.isEmpty(verificationcode)){
                    Toast.makeText(MainActivity.this, "Please enter otp", Toast.LENGTH_SHORT).show();
                }else{

                    loadingbar.setTitle("Verifying Otp");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationcode);
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });

        String UserPhoneKey = Paper.book().read("phonenumber");


            if (!TextUtils.isEmpty(UserPhoneKey)){
                AllowAccess(UserPhoneKey);

                loadingbar.setTitle("Already Logged in");
                loadingbar.setMessage("Please wait.....");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();

            }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            loadingbar.dismiss();
                            Toast.makeText(MainActivity.this, "Congratulations you are verified successfully.", Toast.LENGTH_SHORT).show();
                            gotohomeactivity();

                        } else {

                            String message = task.getException().toString();

                            Toast.makeText(MainActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void gotohomeactivity(){


        Rootref = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> userphonemap = new HashMap<>();

        userphonemap.put("Name",name);
        userphonemap.put("Phone",phone);

        Rootref.child("Users").child(phone).updateChildren(userphonemap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(MainActivity.this, "Welcome to AryaBhatt", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

        Paper.book().write("phonenumber",phone);

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);

    }

    private void AllowAccess(final String phone) {

        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(phone).exists()){

                    Toast.makeText(MainActivity.this, "Please wait, you are already logged in...", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);

                }else{
                    loadingbar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}