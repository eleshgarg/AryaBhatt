
package com.example.aryabhatt_thebookbazzar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private DatabaseReference productref, registerref, notireff, notiref, nameref, imageref, donateref, uploadimageref;
    private RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private Button searchbtn;
    private EditText inputtext;
    private String searchinput;
    private static final int gallerypic = 1;
    private Uri imageuri;
    private StorageReference productimagesref;
    private String downloadimageurl;
    private CircleImageView profileimageview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        NavigationView navigationView = findViewById(R.id.nav_view);

        inputtext = findViewById(R.id.search_product_name);
        searchbtn = findViewById(R.id.search_btn);

        Paper.init(this);

        String UserPhoneKeyy = Paper.book().read("phonenumber");

        View headerview = navigationView.getHeaderView(0);
        final TextView usernametextview = headerview.findViewById(R.id.user_profile_name);
        profileimageview = headerview.findViewById(R.id.user_profile_image);
        nameref = FirebaseDatabase.getInstance().getReference().child("Users").child(UserPhoneKeyy);
        nameref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String namee = snapshot.child("Name").getValue().toString();

                usernametextview.setText("Hello, " + namee);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        productimagesref = FirebaseStorage.getInstance().getReference().child("Users Images");

        imageref = FirebaseDatabase.getInstance().getReference().child("Images").child(UserPhoneKeyy);
        profileimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });
        usernametextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageuri == null) {
                    Toast.makeText(HomeActivity.this, "Add Image First", Toast.LENGTH_SHORT).show();
                } else {
                    StoreProductInformation();
                }
            }
        });


        imageref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    String imagee = snapshot.child("image").getValue().toString();

                    Picasso.get().load(imagee).into(profileimageview);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchinput = inputtext.getText().toString();

                search();

                //Toast.makeText(HomeActivity.this, "you type " + searchinput, Toast.LENGTH_SHORT).show();
            }
        });


        notireff = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> notiphonemap = new HashMap<>();

        notiphonemap.put("titles", "Title: Welcome");
        notiphonemap.put("contents", "Message: Welcome to AryaBhatt - The Book Bazzar.");
        notireff.child("Notifications").child(UserPhoneKeyy).child("MY Notifications").updateChildren(notiphonemap);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_category, R.id.nav_cart, R.id.nav_orders, R.id.nav_donate, R.id.nav_sell, R.id.nav_notifications, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        String phoneuser = Paper.book().read("phonenumber");

        productref = FirebaseDatabase.getInstance().getReference().child("ProductsAll");

        if (phoneuser.isEmpty()) {
            phoneuser = "1";
        }
        registerref = FirebaseDatabase.getInstance().getReference().child("Seller Details").child(phoneuser).child("Personal Details");
        notiref = FirebaseDatabase.getInstance().getReference().child("Notifications").child(phoneuser);

        mRecyclerView = findViewById(R.id.recycler_menu);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    private void StoreProductInformation() {

        String UserPhoneKeyy = Paper.book().read("phonenumber");

        final StorageReference filepath = productimagesref.child(UserPhoneKeyy + imageuri.getLastPathSegment() + ".jpg");

        final UploadTask uploadTask = filepath.putFile(imageuri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(HomeActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(HomeActivity.this, "Image uploaded successfully....", Toast.LENGTH_SHORT).show();

                Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadimageurl = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();


                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {

                            downloadimageurl = task.getResult().toString();

                            //Toast.makeText(HomeActivity.this, "Image save to database successfully.", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void SaveProductInfoToDatabase() {

        uploadimageref = FirebaseDatabase.getInstance().getReference().child("Images");
        String UserPhoneKeyy = Paper.book().read("phonenumber");

        HashMap<String, Object> productmap = new HashMap<>();
        productmap.put("image", downloadimageurl);

        uploadimageref.child(UserPhoneKeyy).updateChildren(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {


                    Toast.makeText(HomeActivity.this, "Image upload successfully.", Toast.LENGTH_SHORT).show();
                } else {

                    String message = task.getException().toString();
                    Toast.makeText(HomeActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void search() {


        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(productref.orderByChild("productname").startAt(searchinput), Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, ProductsViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull final Products model) {

                holder.showname.setText(model.getProductname());
                holder.showdesp.setText(model.getDescription());
                holder.showprice.setText(model.getPrice() + "Rs");
                holder.showdate.setText(model.getDate());
                holder.showtime.setText(model.getTime());

                String phonenumber = model.getPhonenumber();

                Picasso.get().load(model.getImage4()).into(holder.showimage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(HomeActivity.this, "Well done!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("name", model.getProductname());
                        intent.putExtra("desp", model.getDescription());
                        intent.putExtra("price", model.getPrice());
                        intent.putExtra("date", model.getDate());
                        intent.putExtra("time", model.getTime());
                        intent.putExtra("image1", model.getImage1());
                        intent.putExtra("image2", model.getImage2());
                        intent.putExtra("image3", model.getImage3());
                        intent.putExtra("image4", model.getImage4());
                        intent.putExtra("phone", model.getPhonenumber());
                        startActivity(intent);


                    }
                });

            }

            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);

                ProductsViewHolder holder = new ProductsViewHolder(view);

                return holder;
            }
        };

        mRecyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(productref, Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, ProductsViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull final Products model) {

                holder.showname.setText(model.getProductname());
                holder.showdesp.setText(model.getDescription());
                holder.showprice.setText(model.getPrice() + "Rs");
                holder.showdate.setText(model.getDate());
                holder.showtime.setText(model.getTime());

                String phonenumber = model.getPhonenumber();

                Picasso.get().load(model.getImage4()).into(holder.showimage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(HomeActivity.this, "Well done!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("name", model.getProductname());
                        intent.putExtra("desp", model.getDescription());
                        intent.putExtra("price", model.getPrice());
                        intent.putExtra("date", model.getDate());
                        intent.putExtra("time", model.getTime());
//                        intent.putExtra("image1", model.getImage1());
//                        intent.putExtra("image2", model.getImage2());
//                        intent.putExtra("image3", model.getImage3());
                        intent.putExtra("image4", model.getImage4());
                        intent.putExtra("phone", model.getPhonenumber());
                        intent.putExtra("category", model.getCategory());
                        startActivity(intent);


                    }
                });

            }

            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);

                ProductsViewHolder holder = new ProductsViewHolder(view);

                return holder;
            }
        };

        mRecyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);

        menuInflater.inflate(R.menu.home, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.nav_logout) {
                    Paper.book().delete("phonenumber");
                    Intent logoutintent = new Intent(HomeActivity.this, MainActivity.class);
                    logoutintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(logoutintent);
                }
                if (destination.getId() == R.id.nav_orders) {
                    Intent intent = new Intent(HomeActivity.this, MyOrdersActivity.class);
                    startActivity(intent);
                }
                if (destination.getId() == R.id.nav_category) {
                    Intent intent = new Intent(HomeActivity.this, MainCategory.class);
                    startActivity(intent);
                }
                if (destination.getId() == R.id.nav_cart) {
                    Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);
                }
                if (destination.getId() == R.id.nav_donate) {

                    registerref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Intent intent = new Intent(HomeActivity.this, DonorActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(HomeActivity.this, "Please register as seller first.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
                if (destination.getId() == R.id.nav_sell) {

                    registerref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Intent intent = new Intent(HomeActivity.this, SellerCategoryChooseActivity.class);
                                startActivity(intent);
                                Toast.makeText(HomeActivity.this, "Already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(HomeActivity.this, SellerRegistrationActivity.class);
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
                if (destination.getId() == R.id.nav_notifications) {
                    notiref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Intent notiintent = new Intent(HomeActivity.this, NotificationActivity.class);
                                startActivity(notiintent);
                            } else {
                                Toast.makeText(HomeActivity.this, "No Notifications", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });


        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void OpenGallery() {

//        Intent galleryintent = new Intent();
//        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
//        galleryintent.setType("image/*");
//        startActivityForResult(galleryintent, gallerypic);

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == gallerypic && resultCode == RESULT_OK && data != null){
//            imageuri = data.getData();
//            profileimageview.setImageURI(imageuri);
//        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                Uri resulturi = result.getUri();
                profileimageview.setImageURI(resulturi);

            }
        }
    }
}