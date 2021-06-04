package com.example.aryabhatt_thebookbazzar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import io.paperdb.Paper;

public class SellerMyOrdersActivity extends AppCompatActivity {

    private DatabaseReference productref,deleteref,refrence, ref;
    private RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog loadingbar;
    private Button confirmbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_my_orders);

        setTitle("My Orders");

        Paper.init(this);

        String phoneuser = Paper.book().read("phonenumber");

        confirmbtn = findViewById(R.id.confirm_orders_btn);
        confirmbtn.setVisibility(View.INVISIBLE);
        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerMyOrdersActivity.this, SellerConfirmOrdersActivity.class);
                startActivity(intent);
            }
        });

        loadingbar = new ProgressDialog(this);
        productref = FirebaseDatabase.getInstance().getReference().child("SellerOrders").child(phoneuser);
        deleteref = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = findViewById(R.id.recycler_menu1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(SellerMyOrdersActivity.this, SellerCategoryChooseActivity.class);
        startActivity(intent);

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

                Picasso.get().load(model.getImage4()).into(holder.showimage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Toast.makeText(SellerMyOrdersActivity.this, "Well done!", Toast.LENGTH_SHORT).show();



//                        AlertDialog.Builder dialog = new AlertDialog.Builder(SellerMyOrdersActivity.this);
//                        dialog.setTitle("Confirm this Product");
//                        dialog.setMessage(model.getQuantity() + " Piece/s");
//                        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                String phoneuserr = Paper.book().read("phonenumber");
//
//                                String namee = model.getProductname();
//                                String despe = model.getDescription();
//                                String pricee = model.getPrice();
//                                String datee = model.getDate();
//                                String timee = model.getTime();
//                                String phonenumber = model.getPhonenumber();
//                                String imagee1 = model.getImage1();
//                                String imagee2 = model.getImage2();
//                                String imagee3 = model.getImage3();
//                                String imagee4 = model.getImage4();
//                                String quantity = model.getQuantity();
//                                String myname = model.getName();
//                                String mystate = model.getState();
//                                String myaddress = model.getAddress();
//                                String mypincode = model.getPincode();
//                                String mycity = model.getCity();
//                                String phoneuser = model.getPhonenumberuser();
//
//                                refrence = FirebaseDatabase.getInstance().getReference().child("ComfirmOrders");
//                                ref = FirebaseDatabase.getInstance().getReference().child("ConfirmOrdersSeller");
//
//                                HashMap<String, Object> cartmap = new HashMap<>();
//                                cartmap.put("productname", namee);
//                                cartmap.put("description", despe);
//                                cartmap.put("price", pricee);
//                                cartmap.put("date", datee);
//                                cartmap.put("time", timee);
//                                cartmap.put("phonenumber", phonenumber);
//                                cartmap.put("image1", imagee1);
//                                cartmap.put("image2", imagee2);
//                                cartmap.put("image3", imagee3);
//                                cartmap.put("image4", imagee4);
//                                cartmap.put("quantity", quantity);
//                                cartmap.put("name", myname);
//                                cartmap.put("address", myaddress);
//                                cartmap.put("pincode", mypincode);
//                                cartmap.put("state", mystate);
//                                cartmap.put("city", mycity);
//                                cartmap.put("phonenumberuser", phoneuser);
//
//                                refrence.child(model.getPhonenumberuser()).child(model.getPhonenumber() + model.getProductname() + model.getPrice() + model.getDate() + model.getTime()).updateChildren(cartmap);
//                                ref.child(model.getPhonenumber()).child(model.getPhonenumberuser() + model.getProductname() + model.getPrice() + model.getDate() + model.getTime()).updateChildren(cartmap);
//                                deleteref.child("SellerOrders").child(model.getPhonenumber()).child(model.getPhonenumberuser() + model.getProductname() + model.getPrice() + model.getDate() + model.getTime()).removeValue();
//                                deleteref.child("Orders").child(model.getPhonenumberuser()).child(model.getPhonenumber() + model.getProductname() + model.getPrice() + model.getDate() + model.getTime()).removeValue();
//                                deleteref.child("Cart").child(model.getPhonenumberuser()).child(model.getPhonenumber() + model.getProductname() + model.getPrice() + model.getDate() + model.getTime()).removeValue();
//                                Toast.makeText(SellerMyOrdersActivity.this, "Done!", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//                        dialog.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                String phoneuserr = Paper.book().read("phonenumber");
//                                deleteref.child("SellerOrders").child(model.getPhonenumber()).child(model.getPhonenumberuser() + model.getProductname() + model.getPrice() + model.getDate() + model.getTime()).removeValue();
//                                deleteref.child("Orders").child(model.getPhonenumberuser()).child(model.getPhonenumber() + model.getProductname() + model.getPrice() + model.getDate() + model.getTime()).removeValue();
//                                //String message = model.getPhonenumber() + model.getProductname() + model.getPrice() + model.getDate() + model.getTime();
//                                //Toast.makeText(SellerMyOrdersActivity.this, message, Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        dialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.cancel();
//                            }
//                        });
//                        dialog.create();
//                        dialog.show();

                        AlertDialog.Builder dialog = new AlertDialog.Builder(SellerMyOrdersActivity.this);
                        dialog.setTitle("Details");
                        dialog.setMessage(model.getProductname() + "\n" + model.getPrice() + "\n" + model.getCategory() + "\n" + model.getPaymethod() + "\n" + model.getQuantity() + " Pieces " + "\n" + "Personal Details : " + "\n\t" + model.getName() + " "  + model.getPhonenumberuser() + "\nAddress: "  + model.getAddress() + " " + model.getCity() + " " + model.getState() + " " + model.getPincode());
                        dialog.create();
                        dialog.show();


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
}