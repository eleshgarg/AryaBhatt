package com.example.aryabhatt_thebookbazzar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class SellerAddProductActivity extends AppCompatActivity {

    private ImageView addImage1, addImage2, addImage3, addImage4;
    private EditText name, desp, price;
    private Button addpro, managepro, myorderspro;
    private String category, phoneuser;
    private DatabaseReference proref, proreff;
    private StorageReference imagerefrence;
    private ProgressDialog loadingbar;
    private Uri imageuri1, imageuri2, imageuri3, imageuri4;
    private String datetime;
    private String downloadimageurl1, downloadimageurl2, downloadimageurl3, downloadimageurl4;
    private String savecurrentdate, savecurrenttime;
    private static final int gallerypic1 = 1;
    private static final int gallerypic2 = 2;
    private static final int gallerypic3 = 3;
    private static final int gallerypic4 = 4;
    private String proname, prodesp, proprice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_product);

        setTitle("Add Products");

        Paper.init(this);


        //addImage1 = findViewById(R.id.add_new_image1);
        //addImage2 = findViewById(R.id.add_new_image2);
        //addImage3 = findViewById(R.id.add_new_image3);
        addImage4 = findViewById(R.id.add_new_image4);
        name = findViewById(R.id.seller_pro_name);
        desp = findViewById(R.id.seller_pro_desp);
        price = findViewById(R.id.seller_pro_price);
        addpro = findViewById(R.id.seller_pro_button);
        managepro = findViewById(R.id.seller_pro_manage_button);
        myorderspro = findViewById(R.id.seller_pro_myorders_button);
        //deletepro = findViewById(R.id.seller_pro_delete_button);

        managepro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerAddProductActivity.this, SellerManageProductActivity.class);
                startActivity(intent);
            }
        });

        myorderspro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerAddProductActivity.this, SellerMyOrdersActivity.class);
                startActivity(intent);
            }
        });

        category = getIntent().getStringExtra("category");
        loadingbar = new ProgressDialog(this);

        phoneuser = Paper.book().read("phonenumber");

        imagerefrence = FirebaseStorage.getInstance().getReference().child("Product Images");
        proref = FirebaseDatabase.getInstance().getReference();
        proreff = FirebaseDatabase.getInstance().getReference();

//        addImage1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OpenGallery1();
//            }
//        });
//
//        addImage2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OpenGallery2();
//            }
//        });

//        addImage3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OpenGallery3();
//            }
//        });

        addImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery4();
            }
        });

        addpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proname = name.getText().toString();
                prodesp = desp.getText().toString();
                proprice = price.getText().toString();

//                if (imageuri1 == null){
//                    Toast.makeText(SellerAddProductActivity.this, "Please select photo 1*", Toast.LENGTH_SHORT).show();
//                }else if (imageuri2 == null){
//                    Toast.makeText(SellerAddProductActivity.this, "Please select photo 2*", Toast.LENGTH_SHORT).show();
//                }else if (imageuri3 == null){
//                    Toast.makeText(SellerAddProductActivity.this, "Please select photo 3*", Toast.LENGTH_SHORT).show();
//                }else
               if (imageuri4 == null){
                    Toast.makeText(SellerAddProductActivity.this, "Please select photo", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(proname)){
                    Toast.makeText(SellerAddProductActivity.this, "Please enter name.", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(prodesp)){
                    Toast.makeText(SellerAddProductActivity.this, "Please enter despcription.", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(proprice)){
                    Toast.makeText(SellerAddProductActivity.this, "Please enter price.", Toast.LENGTH_SHORT).show();
                }else{
                    loadingbar.setTitle("Add new product");
                    loadingbar.setMessage("Please wait while we are adding new product.");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();
                    SaveProduct();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(SellerAddProductActivity.this, SellerCategoryChooseActivity.class);
        startActivity(intent);

    }

    private void SaveProduct() {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
        savecurrentdate = currentdate.format(calendar.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime = currenttime.format(calendar.getTime());

        datetime = savecurrentdate + savecurrenttime;

        final StorageReference filepath4 = imagerefrence.child(phoneuser + "Image By Seller" + imageuri4.getLastPathSegment() + datetime + ".jpg");
        final UploadTask uploadTask4 = filepath4.putFile(imageuri4);

        uploadTask4.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingbar.dismiss();
                String message = e.toString();
                Toast.makeText(SellerAddProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask1 = uploadTask4.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                            @Override
                                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                if (!task.isSuccessful()) {
                                                    throw task.getException();
                                                }

                                                return filepath4.getDownloadUrl();
                                            }
                                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){

                                                                                        //Toast.makeText(SellerAddProductActivity.this, "44.", Toast.LENGTH_SHORT).show();
                            downloadimageurl4 = task.getResult().toString();

                            Toast.makeText(SellerAddProductActivity.this, "Product uploaded successfully.", Toast.LENGTH_SHORT).show();
                            SavedataTofirebase();
                        }
                    }
                });
            }
        });



//        final StorageReference filepath1 = imagerefrence.child(phoneuser + "Image By Seller" + imageuri1.getLastPathSegment() + datetime + ".jpg");
//        final UploadTask uploadTask1 = filepath1.putFile(imageuri1);
//
//
//        uploadTask1.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                loadingbar.dismiss();
//                String message = e.toString();
//                Toast.makeText(SellerAddProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
//
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                final StorageReference filepath2 = imagerefrence.child(phoneuser + "Image By Seller" + imageuri2.getLastPathSegment() + datetime + ".jpg");
//                final UploadTask uploadTask2 = filepath2.putFile(imageuri2);
//
//                //Toast.makeText(SellerAddProductActivity.this, "1.", Toast.LENGTH_SHORT).show();
//
//                uploadTask2.addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        loadingbar.dismiss();
//                        String message = e.toString();
//                        Toast.makeText(SellerAddProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
//
//                    }
//                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        final StorageReference filepath3 = imagerefrence.child(phoneuser + "Image By Seller" + imageuri3.getLastPathSegment() + datetime + ".jpg");
//                        final UploadTask uploadTask3 = filepath3.putFile(imageuri3);
//                        //Toast.makeText(SellerAddProductActivity.this, "2.", Toast.LENGTH_SHORT).show();
//
//                        uploadTask3.addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                loadingbar.dismiss();
//                                String message = e.toString();
//                                Toast.makeText(SellerAddProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
//
//                            }
//                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                final StorageReference filepath4 = imagerefrence.child(phoneuser + "Image By Seller" + imageuri4.getLastPathSegment() + datetime + ".jpg");
//                                final UploadTask uploadTask4 = filepath4.putFile(imageuri4);
//                                //Toast.makeText(SellerAddProductActivity.this, "3.", Toast.LENGTH_SHORT).show();
//
//                                uploadTask4.addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        loadingbar.dismiss();
//                                        String message = e.toString();
//                                        Toast.makeText(SellerAddProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
//
//                                    }
//                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                    @Override
//                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                                        //Toast.makeText(SellerAddProductActivity.this, "Images Uploaded Successfully...", Toast.LENGTH_SHORT).show();
//
//                                        Task<Uri> uriTask1 = uploadTask1.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                                            @Override
//                                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                                                if (!task.isSuccessful()) {
//                                                    throw task.getException();
//                                                }
//
//                                                return filepath1.getDownloadUrl();
//                                            }
//                                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Uri> task) {
//                                                if (task.isSuccessful()){
//
//                                                    //Toast.makeText(SellerAddProductActivity.this, "11.", Toast.LENGTH_SHORT).show();
//
//                                                    downloadimageurl1 = task.getResult().toString();
//
//                                                    Task<Uri> uriTask2 = uploadTask2.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                                                        @Override
//                                                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                                                            if (!task.isSuccessful()) {
//                                                                throw task.getException();
//                                                            }
//
//                                                            return filepath2.getDownloadUrl();
//                                                        }
//                                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Uri> task) {
//                                                            if (task.isSuccessful()){
//
//                                                                //Toast.makeText(SellerAddProductActivity.this, "22.", Toast.LENGTH_SHORT).show();
//                                                                downloadimageurl2 = task.getResult().toString();
//
//                                                                Task<Uri> uriTask3 = uploadTask3.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                                                                    @Override
//                                                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                                                                        if (!task.isSuccessful()) {
//                                                                            throw task.getException();
//                                                                        }
//
//                                                                        return filepath3.getDownloadUrl();
//                                                                    }
//                                                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                                                    @Override
//                                                                    public void onComplete(@NonNull Task<Uri> task) {
//                                                                        if (task.isSuccessful()){
//
//                                                                            //Toast.makeText(SellerAddProductActivity.this, "33.", Toast.LENGTH_SHORT).show();
//                                                                            downloadimageurl3 = task.getResult().toString();
//
//                                                                            Task<Uri> uriTask4 = uploadTask4.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                                                                                @Override
//                                                                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                                                                                    if (!task.isSuccessful()) {
//                                                                                        throw task.getException();
//                                                                                    }
//
//                                                                                    return filepath4.getDownloadUrl();
//                                                                                }
//                                                                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                                                                @Override
//                                                                                public void onComplete(@NonNull Task<Uri> task) {
//                                                                                    if (task.isSuccessful()){
//
//                                                                                        //Toast.makeText(SellerAddProductActivity.this, "44.", Toast.LENGTH_SHORT).show();
//                                                                                        downloadimageurl4 = task.getResult().toString();
//
//                                                                                        Toast.makeText(SellerAddProductActivity.this, "Product uploaded successfully.", Toast.LENGTH_SHORT).show();
//                                                                                        SavedataTofirebase();
//                                                                                    }
//                                                                                }
//                                                                            });
//                                                                        }
//                                                                    }
//                                                                });
//                                                            }
//                                                        }
//                                                    });
//                                                }
//                                            }
//                                        });
//
//
//
//                                    }
//                                });
//                            }
//                        });
//                    }
//                });
//            }
//        });

    }

    private void SavedataTofirebase() {

        final HashMap<String, Object> productmap = new HashMap<>();
        productmap.put("date", savecurrentdate);
        productmap.put("time", savecurrenttime);
        productmap.put("productname", proname);
        productmap.put("description", prodesp);
        productmap.put("price", proprice);
        productmap.put("category", category);
//        productmap.put("image1", downloadimageurl1);
//        productmap.put("image2", downloadimageurl2);
//        productmap.put("image3", downloadimageurl3);
        productmap.put("image4", downloadimageurl4);
        productmap.put("pid", datetime);
        productmap.put("phonenumber", phoneuser);


        proref.child("Products").child(phoneuser).child(datetime).updateChildren(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    proreff.child("ProductsAll").child(phoneuser+ proprice + category).updateChildren(productmap);
                    proreff.child(category).child(phoneuser+ proprice + category).updateChildren(productmap);
                    Intent intent = new Intent(SellerAddProductActivity.this, SellerCategoryChooseActivity.class);
                    startActivity(intent);
                    loadingbar.dismiss();
                    Toast.makeText(SellerAddProductActivity.this, "Bravo!", Toast.LENGTH_SHORT).show();
                }else{
                    loadingbar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(SellerAddProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

//    private void SavedataTofirebase() {
//
//        HashMap<String, Object> productmap = new HashMap<>();
//        productmap.put("date", savecurrentdate);
//        productmap.put("time", savecurrenttime);
//        productmap.put("productname", name);
//        productmap.put("description", desp);
//        productmap.put("price", price);
//        productmap.put("category", category);
//        productmap.put("image1", downloadimageurl1);
//        productmap.put("image2", downloadimageurl2);
//        productmap.put("image3", downloadimageurl3);
//        productmap.put("image4", downloadimageurl4);
//        productmap.put("pid", datetime);
//
//        proref.child(datetime).updateChildren(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//                    Intent intent = new Intent(SellerAddProductActivity.this, SellerCategoryChooseActivity.class);
//                    startActivity(intent);
//                    loadingbar.dismiss();
//                    Toast.makeText(SellerAddProductActivity.this, "Product is added successfully on firebase...", Toast.LENGTH_SHORT).show();
//                }else{
//                    loadingbar.dismiss();
//                    String message = task.getException().toString();
//                    Toast.makeText(SellerAddProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }

//    private void OpenGallery1() {
//
//        Intent galleryintent = new Intent();
//        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
//        galleryintent.setType("image/*");
//        startActivityForResult(galleryintent, gallerypic1);
//
//    }
//
//    private void OpenGallery2() {
//
//        Intent galleryintent = new Intent();
//        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
//        galleryintent.setType("image/*");
//        startActivityForResult(galleryintent, gallerypic2);
//
//    }
//
//    private void OpenGallery3() {
//
//        Intent galleryintent = new Intent();
//        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
//        galleryintent.setType("image/*");
//        startActivityForResult(galleryintent, gallerypic3);
//
//    }

    private void OpenGallery4() {

        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, gallerypic4);

//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setAspectRatio(1,1)
//                .start(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == gallerypic1 && resultCode == RESULT_OK && data != null){
//            imageuri1 = data.getData();
//            addImage1.setImageURI(imageuri1);
//        }
//        if (requestCode == gallerypic2 && resultCode == RESULT_OK && data != null){
//            imageuri2 = data.getData();
//            addImage2.setImageURI(imageuri2);
//        }
//        if (requestCode == gallerypic3 && resultCode == RESULT_OK && data != null){
//            imageuri3 = data.getData();
//            addImage3.setImageURI(imageuri3);
//        }
        if (requestCode == gallerypic4 && resultCode == RESULT_OK && data != null){
            imageuri4 = data.getData();
            addImage4.setImageURI(imageuri4);
        }
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//
//            if (resultCode == RESULT_OK){
//
//                imageuri4 = result.getUri();
//                addImage4.setImageURI(imageuri4);
//
//            }
//        }



    }
}